package cn.enn.wise.platform.mall.job;

import cn.enn.wise.platform.mall.bean.bo.GoodsExtend;
import cn.enn.wise.platform.mall.bean.bo.Order;
import cn.enn.wise.platform.mall.service.GoodsExtendService;
import cn.enn.wise.platform.mall.service.OrderService;
import cn.enn.wise.platform.mall.util.thirdparty.LalyoubaShipHttpApiUtil;
import cn.enn.wise.platform.mall.util.thirdparty.laiyoubovo.OinfoVo;
import cn.enn.wise.platform.mall.util.thirdparty.laiyoubovo.OrderDetailVo;
import cn.enn.wise.platform.mall.util.thirdparty.laiyoubovo.ShipBaseVo;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 票务订单状态更新
 * <p>根据来游吧票务系统接口状态，更新本地订单状态</p>
 * <p>票务 --> 订单</p>
 * @author gaoguanglin
 * @since version.feature-wzd-ticket
 */
@Component
@Slf4j
public class OrderTicketStatusJob {

    @Value("${spring.profiles.active}")
    private String profile;

    @Autowired
    private OrderService orderService;

    @Autowired
    private GoodsExtendService goodsExtendService;

    /** 远程（Key）船票状态码 ： 本地（Value）订单状态码 **/
    private Map<Integer,Integer> remoteLocalOrderStateMap = new HashMap(){{
        put(0,7);  // 订单异常关闭，出票失败
        put(1,1);  // 待支付
        put(2,2);  // 已支付，待使用
        put(3,5);  // 已取消
        put(4,5);  // 成功退票
        put(5,9);  // 已出票，已使用
    }};




    /** 每3分钟更新，船票使用情况 **/
    public void refreshOrderStatus(){
        log.info("==%% 定时任务执行（每3分钟），同步船票订单出单状态 ...");
        List<Order> orderList = orderService.queryTicketUnusedOrder();
        for(Order order : orderList){
            GoodsExtend goodsExtend = goodsExtendService.getById(order.getGoodsId());
            try{
                LocalDateTime startTime = parseDateTimeFromGoodsExtends(goodsExtend);
                // 发船已经超过12小时，设置为已过期
                if(startTime != null){
                    if(LocalDateTime.now().minusHours(12L).isAfter(startTime)){
                        orderService.updateOrderStateById(4,order.getId());
                        log.info("==> 已经更新订单状态：已过期，OrderID：{}",order.getId());
                        continue;
                    }
                }
                // 查询订单
                ShipBaseVo<OrderDetailVo> baseVo = LalyoubaShipHttpApiUtil.orderDetail(profile, order.getTicketOrderCode(), null);
                OrderDetailVo orderDetail = baseVo.getData();
                log.info("====> 来游吧数据返回:{}",JSONObject.toJSONString(orderDetail));
                String strStatus = orderDetail.getStatus();
                if(StringUtils.isEmpty(strStatus)){
                    // 数据返回不完整
                    continue;
                }
                int status = Integer.parseInt(strStatus);
                Integer localOrderStatus = remoteLocalOrderStateMap.get(status);
                if(localOrderStatus==null){
                    // 不可识别的状态码
                    continue;
                }
                if(status==5){ // 已使用
                    orderService.updateOrderStateById(localOrderStatus,order.getId());
                    List<OinfoVo> details = orderDetail.getOinfo();
                    for(OinfoVo oinfoVo : details){
                        if(oinfoVo.getStatusName().contains("出票")){
                            orderService.setOrderTicketStatus(oinfoVo.getTkdtID(),3);
                        }
                    }
                    log.info("==> 已经更新订单状态：已使用，OrderID：{}",order.getId());
                }

            }catch (Exception e){
                log.error("====> OrderID:{},{}",order.getId(),e.getMessage());
                continue;
            }

        }
    }


    /** 每3分钟更新，退票情况 **/
    @Scheduled(cron = "0 */3 * * * ?")
    public void refreshOrderStatus_2(){
        log.info("==%% 定时任务执行（每3分钟），同步船票订单退票状态 ...");
        List<Order> orderList = orderService.queryTicketPayedOrder();
        for(Order order : orderList){
            GoodsExtend goodsExtend = goodsExtendService.getById(order.getGoodsId());
            try{
                LocalDateTime startTime = parseDateTimeFromGoodsExtends(goodsExtend);
                // 已晚于发船时间12小时，将状态设置为已过期已使用；
                if(startTime != null){
                    if(LocalDateTime.now().minusHours(12L).isAfter(startTime)) {
                        if (order.getState().intValue() == 2) {
                            orderService.updateOrderStateById(4, order.getId());
                            log.info("==> 已经更新订单状态：已过期，OrderID：{}", order.getId());
                        } else {
                            orderService.updateOrderStateById(3, order.getId());
                            log.info("==> 已经更新订单状态：已使用，OrderID：{}", order.getId());
                        }
                        continue;
                    }
                }
                // 查询订单
                ShipBaseVo<OrderDetailVo> baseVo = LalyoubaShipHttpApiUtil.orderDetail(profile, order.getTicketOrderCode(), null);
                OrderDetailVo orderDetail = baseVo.getData();
                log.info("====> 来游吧数据返回:{}",JSONObject.toJSONString(orderDetail));
                String strStatus = orderDetail.getStatus();
                if(StringUtils.isEmpty(strStatus)){
                    // 数据返回不完整
                    continue;
                }
                int status = Integer.parseInt(strStatus);
                if(status==4){
                    // 退单，退票
                    int refundCount = 0 ;
                    orderService.updateOrderStateById(remoteLocalOrderStateMap.get(4),order.getId());
                    List<OinfoVo> details = orderDetail.getOinfo();
                    for(OinfoVo oinfoVo : details){
                        if(oinfoVo.getStatusName().contains("取消") || oinfoVo.getStatusName().contains("退票")){
                            orderService.setOrderTicketRefund(oinfoVo.getTkdtID(),oinfoVo.getBackMoney().doubleValue(),oinfoVo.getRatio());
                            refundCount++;
                        }
                    }
                    log.info("==> 已经更新订单状态：已退票，OrderID：{},退票张数：{}",order.getId(),refundCount);
                    continue;
                }

                if(status==6 || status==20){
                    // 部分退款 & 窗口退票
                    int refundCount = 0;
                    List<OinfoVo> details = orderDetail.getOinfo();
                    for(OinfoVo oinfoVo : details){
                        if(oinfoVo.getStatusName().contains("取消") || oinfoVo.getStatusName().contains("退票")){
                            orderService.setOrderTicketRefund(oinfoVo.getTkdtID(),oinfoVo.getBackMoney().doubleValue(),oinfoVo.getRatio());
                            refundCount++;
                        }
                    }
                    log.info("==> 已经更新订单状态：已退票，OrderID：{}，退票张数：{}",order.getId(),refundCount);
                }
            }catch (Exception e){
                log.error("====> OrderID:{},{}",order.getId(),e.getMessage());
                continue;
            }

        }
    }






    private LocalDateTime parseDateTimeFromGoodsExtends(GoodsExtend goodsExtend){
        try {
            if(goodsExtend == null || StringUtils.isEmpty(goodsExtend.getLineDate()) || StringUtils.isEmpty(goodsExtend.getShipLineInfo())){
                throw new Exception("无船舶航班信息");
            }
            String jsonStr = goodsExtend.getShipLineInfo();
            JSONObject jsonObj = JSONObject.parseObject(jsonStr);
            String startTime = jsonObj.getString("startTime");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            return LocalDateTime.parse(goodsExtend.getLineDate().trim()+" "+startTime.trim()+":00",formatter);
        }catch (Exception e){
            log.warn("====> {}",e.getMessage());
            return null;
        }

    }





}
