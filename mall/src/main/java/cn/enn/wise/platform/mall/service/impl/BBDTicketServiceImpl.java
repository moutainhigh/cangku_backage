package cn.enn.wise.platform.mall.service.impl;


import cn.enn.wise.platform.mall.bean.bo.OrderTickets;
import cn.enn.wise.platform.mall.bean.bo.Orders;
import cn.enn.wise.platform.mall.bean.param.BBDRefundOrderDTO;
import cn.enn.wise.platform.mall.bean.param.BBDRefundTicketDTO;
import cn.enn.wise.platform.mall.mapper.OrderDao;
import cn.enn.wise.platform.mall.mapper.WzdOrderAppletsMapper;
import cn.enn.wise.platform.mall.service.BBDTicketService;
import cn.enn.wise.platform.mall.util.MessageSender;
import cn.enn.wise.platform.mall.util.thirdparty.BaiBangDaHttpApiUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class BBDTicketServiceImpl implements BBDTicketService {


    @Value("${spring.profiles.active}")
    private String profile;
    @Autowired
    private OrderDao orderDao;

    @Resource
    private WzdOrderAppletsMapper wzdOrderAppletsMapper;

    @Autowired
    private MessageSender messageSender;

    @Override
    public void updateTicketFromBBD(Long ticketId) {
        JSONObject jsonObject = BaiBangDaHttpApiUtil.getBbdTicketDetail(ticketId);
        if(jsonObject.getInteger("code").intValue()==1){
            int status = jsonObject.getJSONObject("data").getInteger("status");
            orderDao.updateOrderTicketStateBBD(ticketId,status);
            log.info("===> 票状态已经更新");
        }else{
            log.error("===> 百邦达数据返回错误！！");
            log.error(jsonObject.toJSONString());
        }
    }

    @Override
    public void updateBBDTicketState(Long ticketId, int state) {
        orderDao.updateOrderTicketStateBBD(ticketId,state);
        // 更新订单状态

        // step1.根据当前票号查询订单号
        OrderTickets orderTicket = orderDao.findOrderTicketsById(ticketId);
        String orderCode = orderTicket.getTicketCode();
        // step2.根据订单号查询本地所有票的状态
        List<OrderTickets> tickets = orderDao.findOrderTicket(new String[]{orderCode});
        //百邦达票状态，0:待出票  -1出票失败/已取消  1出票成功  100 已检票  230退成功已结款
        int orderState=0;
        if(tickets.size()<=0){
            log.info("===> 不存在此订单下的票");
            return;
        }else if(tickets.size()==1){
            OrderTickets t = tickets.get(0);
            switch (t.getTicketStateBbd().intValue()){
                case 1:
                    orderState = 2;break;
                case 100:
                    orderState = 3;break;
                case 230:
                    orderState = 11;break;
            }
            log.info("===> 单张票，状态更新为：{}",orderState);
        }else{

            for(int i=0;i<tickets.size();i++){
                OrderTickets n = tickets.get(i);
                switch (n.getTicketStateBbd().intValue()){
                    case 1:
                        if(orderState<2){
                            orderState = 2;
                        }
                        break;
                    case 100:
                        if(orderState<3){
                            orderState = 3;
                        }
                        break;
                    case 230:
                        if(orderState<11){
                            orderState = 11;
                        }
                        break;
                }
            }

        }
        // step3.根据此订单下所有票的状态更新订单状态
        if(orderState!=0){
            log.info("===> 订单状态修改：{}",orderState);
            orderDao.updateOrderStateByOrderCode(orderState,orderCode);

            //订单状态更新为已使用时，分销订单的状态 => 已使用
            if(orderState == 3){
                //查询订单信息
                Orders orderInfo = wzdOrderAppletsMapper.getOrderInfoByOrderCode2(orderCode);
                //是分销订单
                if(orderInfo.getIsDistributeOrder() == 1){
                    //修改分销订单状态
                    Byte status = 2;
                    messageSender.sendSyncDistributorOrderStatus(orderInfo.getId(), status);
                }
            }
            //订单状态更新为已退单时，分销订单的状态 => 已退单
            if(orderState == 11){
                //查询订单信息
                Orders orderInfo = wzdOrderAppletsMapper.getOrderInfoByOrderCode2(orderCode);
                //是分销订单
                if(orderInfo.getIsDistributeOrder() == 1){
                    //修改分销订单状态
                    Byte status = 5;
                    messageSender.sendSyncDistributorOrderStatus(orderInfo.getId(), status);
                }
            }
        }

    }

    @Override
    public boolean refundNotifyBBD(String ticketNum) {

        OrderTickets orderTicket = orderDao.findOrderTicketByBBDTicketNum(ticketNum);
        if(orderTicket==null){
            return false;
        }
        NumberFormat moneyFormat = NumberFormat.getNumberInstance();
        moneyFormat.setMaximumFractionDigits(2);
        BBDRefundOrderDTO refundOrderDTO = new BBDRefundOrderDTO();
        refundOrderDTO.setOrderId(orderTicket.getOrderSerialBbd());
        List<BBDRefundTicketDTO> ticketDTOList = new ArrayList();
        BBDRefundTicketDTO ticketDTO = new BBDRefundTicketDTO();
        ticketDTO.setTicketId(orderTicket.getTicketIdBbd());
        ticketDTO.setTicketNo(orderTicket.getTicketSerialBbd());
        if(orderTicket.getCouponPrice()!=null && orderTicket.getCouponPrice().doubleValue()>0.00){
            ticketDTO.setTicketPrice(moneyFormat.format(orderTicket.getCouponPrice()));
        }else{
            ticketDTO.setTicketPrice(moneyFormat.format(orderTicket.getSinglePrice()));
        }
        ticketDTOList.add(ticketDTO);
        refundOrderDTO.setTicketRefundList(ticketDTOList);
        try{

            log.info("===> 通知百邦达执行退款");
            JSONObject jsonObject = BaiBangDaHttpApiUtil.notifyBbdTicketRefund(refundOrderDTO);
            log.info("<=== 退款完成");
            log.info("==> {}",jsonObject.toJSONString());
            if(jsonObject.getInteger("code").intValue()==1){
                return true;
            }
            log.error("===> 百邦达数据返回：{}",jsonObject.toJSONString());
            log.error("===> 未能更新百邦达退票状态");
        }catch (Exception e){
            log.error("===> 数据调用失败: {}",e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
}
