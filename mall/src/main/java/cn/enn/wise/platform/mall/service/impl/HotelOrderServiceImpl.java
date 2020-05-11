package cn.enn.wise.platform.mall.service.impl;

import cn.enn.wise.platform.mall.bean.bo.HotelGoods;
import cn.enn.wise.platform.mall.bean.bo.HotelOrder;
import cn.enn.wise.platform.mall.bean.param.HotelOrderParams;
import cn.enn.wise.platform.mall.bean.param.ReqPageInfoQry;
import cn.enn.wise.platform.mall.bean.vo.HotelOrderAdminVo;
import cn.enn.wise.platform.mall.bean.vo.HotelOrderVo;
import cn.enn.wise.platform.mall.bean.vo.ResPageInfoVO;
import cn.enn.wise.platform.mall.config.pay.WxPayService;
import cn.enn.wise.platform.mall.dao.HotelOrderRepository;
import cn.enn.wise.platform.mall.service.HotelGoodsService;
import cn.enn.wise.platform.mall.service.HotelOrderService;
import cn.enn.wise.platform.mall.util.*;
import cn.enn.wise.platform.mall.util.WX.WxPayUtils;
import cn.enn.wise.platform.mall.util.exception.BusinessException;
import com.alibaba.fastjson.JSONObject;
import com.mongodb.client.result.UpdateResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 酒店订单接口实现
 *
 * @author baijie
 * @date 2019-09-20
 */
@Service
@Slf4j
public class HotelOrderServiceImpl implements HotelOrderService {

    @Autowired
    private HotelGoodsService hotelGoodsService;

    @Autowired
    private HotelOrderRepository hotelOrderRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private WxPayService wxPayService;

    @Autowired
    private MessageSender messageSender;


    private static Integer TO_BE_USED =2;

    @Override
    public Object saveOrder(HotelOrderVo hotelOrderVo) throws Exception {

        //查询商品信息
        HotelGoods hotelGoods = hotelGoodsService.getById(hotelOrderVo.getGoodsId());

        Integer hotelGoodsStatus = hotelGoods.getStatus();

        if( hotelGoodsStatus == null || hotelGoodsStatus !=1 ){
            throw new BusinessException(GeneConstant.BUSINESS_ERROR,"该商品已下架");
        }
        //景点id
        Long scenicId = hotelOrderVo.getScenicId();
        //房间名称
        String houseName = hotelGoods.getHouseName();
        //房间数
        Integer amount = hotelOrderVo.getAmount();
        //入住天数
        Integer dayStayed = hotelOrderVo.getDayStayed();
        //计算价格
        BigDecimal totalPrice = hotelGoods.getPrice().multiply(new BigDecimal(amount)).multiply(new BigDecimal(dayStayed));

        String wxOrderCode = GeneUtil.getJDCode();
        //创建订单
        HotelOrder.HotelOrderBuilder hotelOrderBuilder = HotelOrder
                .builder()
                .goodsName(houseName)
                .goodsPrice(totalPrice)
                .state(1)
                .amount(amount)
                .userId(hotelOrderVo.getUserId())
                .scenicId(scenicId)
                .goodsId(hotelOrderVo.getGoodsId())
                .createTime(Timestamp.valueOf(LocalDateTime.now()))
                .expiredTime(new Timestamp(DateUtil.getNextTime(20).getTime()))
                .payType("weixin")
                .batCode(wxOrderCode)
                .orderSource(1)
                .actualPay(new BigDecimal(0))
                .shouldPay(totalPrice)
                .userWechatName(GeneUtil.filterEmoji(hotelOrderVo.getUserWechatName()))
                .name(hotelOrderVo.getName())
                .siglePrice(hotelGoods.getPrice())
                .incomeDate(hotelOrderVo.getIncomeDate())
                .departureDate(hotelOrderVo.getDepartureDate())
                .dayStayed(hotelOrderVo.getDayStayed())
                .phone(hotelOrderVo.getPhone())
                .payStatus(1);
        HotelOrder hotelOrder = saveOrderToDB(hotelOrderBuilder);

        if(hotelOrder == null){

            throw new BusinessException(GeneConstant.BUSINESS_ERROR,"保存订单失败");
        }

        //调用微信支付时需要把商品的价格单位变成分
        String  totalFee = hotelOrder.getGoodsPrice().multiply(new BigDecimal("100")).setScale(0).toString();

        Object pay = wxPayService.pay(scenicId,
                houseName,
                hotelOrderVo.getOpenId(),
                wxOrderCode,
                totalFee, hotelOrderVo.getIp(),
                hotelOrder.getOrderCode());


        return pay;
    }

    @Override
    public ResponseEntity<ResPageInfoVO<List<HotelOrderAdminVo>>> listHotelOrder(ReqPageInfoQry<HotelOrderParams> orderParams) {

        ResPageInfoVO<List<HotelOrderAdminVo>> page =new ResPageInfoVO<>();

        Query query = new Query()
                .skip((orderParams.getPageNum()-1)*orderParams.getPageSize())
                .limit(orderParams.getPageSize().intValue())
                .with(new Sort(Sort.Direction.DESC ,"orderCode"));
        if(StringUtils.isNotEmpty(orderParams.getReqObj().getOrderCode())){
            query.addCriteria(Criteria.where("orderCode").is(orderParams.getReqObj().getOrderCode()));

        }

        List<HotelOrderAdminVo> list= mongoTemplate.find(query, HotelOrderAdminVo.class, "HotelOrder");
        long totalSize = mongoTemplate.count(query, HotelOrder.class, "HotelOrder");

        page.setPageNum(orderParams.getPageNum());
        page.setRecords(list);
        page.setTotal(totalSize);
        page.setPageSize(orderParams.getPageSize());

        return new ResponseEntity(GeneConstant.SUCCESS_CODE,"酒店订单列表",page);
    }

    @Override
    public  ResponseEntity refundOrderById(String orderCode) {
        try {
            Query query = new Query(Criteria.where("orderCode").is(orderCode));
            HotelOrder order  =mongoTemplate.findOne(query,HotelOrder.class,"HotelOrder");
            if(order.getState().equals(TO_BE_USED)){
                order.setState(11);
                hotelOrderRepository.save(order);
                try {
                    handleHotelOrder(order);
                }catch (Exception e){
                    e.printStackTrace();
                }
                return new ResponseEntity(GeneConstant.SUCCESS_CODE,"更新成功",order);
            }
            return new ResponseEntity(GeneConstant.BUSINESS_ERROR,"更新失败");
        } catch (Exception e) {
            return new ResponseEntity(GeneConstant.BUSINESS_ERROR,"更新失败",e.getMessage());
        }
    }

    @Override
    public ResponseEntity checkIn(String orderCode) {
        try {
            Query query = new Query(Criteria.where("orderCode").is(orderCode));
            HotelOrder order = mongoTemplate.findOne(query, HotelOrder.class, "HotelOrder");
            if (order.getState().equals(TO_BE_USED)) {
                order.setState(3);
                hotelOrderRepository.save(order);
                return new ResponseEntity(GeneConstant.SUCCESS_CODE, "入住成功", order);
            }
            return new ResponseEntity(GeneConstant.BUSINESS_ERROR,"入住失败");
        } catch (Exception e) {
            return new ResponseEntity(GeneConstant.BUSINESS_ERROR, "入住失败", e.getMessage());
        }
    }

    /**
     * 保存订单到Mongo数据库
     * @param hotelOrderBuilder
     *          构建订单实体
     * @return
     *          保存到数据库的订单
     */
    public synchronized HotelOrder   saveOrderToDB( HotelOrder.HotelOrderBuilder hotelOrderBuilder){
        //获取当月的第一天 和最后一天
        String beginDayOfMonth = DateUtil.getFormat(DateUtil.getBeginDayOfMonth(),"yyyy-MM-dd") + " 00:00:00";

        String endDayOfMonth = DateUtil.getFormat(DateUtil.getEndDayOfMonth(),"yyyy-MM-dd") +" 23:59:59";

        Query query = new Query();
        Criteria criteria = Criteria.where("createTime").lt(DateUtil.dateToISODate(endDayOfMonth))
                .gte(DateUtil.dateToISODate(beginDayOfMonth));
        query.addCriteria(criteria);
        //查询当月有多少订单
        long hotelOrder = mongoTemplate.count(query, "HotelOrder");

        String month = new SimpleDateFormat("MM").format(new Date());

        Integer nowYear = DateUtil.getNowYear();

        String year  = String.valueOf(nowYear).substring(2);

        StringBuffer stringBuffer = new StringBuffer();
        String orderCode = stringBuffer
                .append("JD")
                .append(year)
                .append(month)
                .append(String.format("%04d", hotelOrder + 1))
                .toString();
        HotelOrder build = hotelOrderBuilder.orderCode(orderCode).build();
        //订单入库
        HotelOrder result = hotelOrderRepository.save(build);

        return result;
    }


    @Override
    public List<HotelOrder> getUserOrderList(Long userId) {

        return hotelOrderRepository.findByUserIdOrderByCreateTimeDesc(userId);
    }

    @Override
    public HotelOrder getOrderInfo(String orderId, Long userId) {

        HotelOrder order = hotelOrderRepository.findHotelOrderByOrderCodeAndUserId(orderId, userId);
        //待支付订单
        if(order.getPayStatus() == 1 && order.getState() == 1){

            Date expiredTime = order.getExpiredTime();
            Timestamp currentTime = Timestamp.valueOf(LocalDateTime.now());

            //计算剩余时间
            long levelTime = DateUtil.differentSecondIsNotAbs(currentTime, expiredTime);
            if(levelTime <= 0){

                order.setState(5);
                Query query = new Query(Criteria.where("id").is(order.getId()).and("state").is(1).and("payStatus").is(1));
                Update update = new Update().set("state", 5).set("updateTime", Timestamp.valueOf(LocalDateTime.now()));
                mongoTemplate.updateFirst(query,update,HotelOrder.class);

            }else {
                order.setLeaveTime(levelTime);

            }
        }

        return order;
    }

    @Override
    public void userCancelOrder(String orderId, Long userId) throws Exception {

        //查询订单详情
        HotelOrder hotelOrderByIdAndUserId = hotelOrderRepository.findHotelOrderByIdAndUserId(orderId, userId);

        if(hotelOrderByIdAndUserId == null){
            throw new BusinessException(GeneConstant.BUSINESS_ERROR,"该订单不存在!");
        }

        if(hotelOrderByIdAndUserId.getState() != 2 && hotelOrderByIdAndUserId.getPayStatus() == 2 ){

            throw new BusinessException(GeneConstant.BUSINESS_ERROR,"订单不是待使用状态,无法取消");

        }

        Query query = new Query(Criteria.where("userId").is(userId).and("id").is(orderId));
        Update update = new Update().set("state",5).set("updateTime",Timestamp.valueOf(LocalDateTime.now()));

        //更新订单状态
        mongoTemplate.updateFirst(query,update,HotelOrder.class);

    }

    @Override
    public Object payOldOrder(HotelOrderVo hotelOrderVo) throws Exception {

        String orderCode = hotelOrderVo.getOrderCode();
        //查询订单详情
        HotelOrder orderInfo = hotelOrderRepository.findHotelOrderByOrderCodeAndUserIdAndPayStatusAndState(orderCode, hotelOrderVo.getUserId(),1,1);

        if(orderInfo == null){
            throw new BusinessException(GeneConstant.BUSINESS_ERROR,"该订单无法发起重新支付");
        }

        String  totalFee = orderInfo.getGoodsPrice().multiply(new BigDecimal("100")).setScale(0).toString();

        Object pay = wxPayService.pay(hotelOrderVo.getScenicId(),
                orderInfo.getGoodsName(),
                hotelOrderVo.getOpenId(),
                orderInfo.getBatCode(),
                totalFee,
                hotelOrderVo.getIp(),
                orderInfo.getOrderCode());


        return pay;
    }

    @Override
    public void updateOrderStatusToSuccess(String batCode) {

        //查询订单状态
        HotelOrder byBatCode = hotelOrderRepository.findByBatCode(batCode);
        if(batCode == null ){

            throw new BusinessException(GeneConstant.BUSINESS_ERROR,"该订单不存在");

        }
        Integer payStatus = byBatCode.getPayStatus();
        if( payStatus != 1){

            throw new BusinessException(GeneConstant.BUSINESS_ERROR,"该订单支付状态不正确"+payStatus);
        }

        Integer state = byBatCode.getState();
        if(state != 1){
            throw new BusinessException(GeneConstant.BUSINESS_ERROR,"订单状态不正确"+state);
        }

        Query query = new Query(Criteria.where("batCode").is(batCode).and("payStatus").is(1).and("state").is(1));
        Update update = new Update()
                .set("payStatus", 2)
                .set("state", 2)
                .set("actualPay",byBatCode.getGoodsPrice())
                .set("updateTime",Timestamp.valueOf(LocalDateTime.now()));

        //修改订单状态
        mongoTemplate.findAndModify(query,update,HotelOrder.class);
        messageSender.sendSms(byBatCode.getPhone(), "SMS_174812438", new HashMap<String, String>() {{
            put("name", byBatCode.getName());
            put("roomType", byBatCode.getGoodsName());
            put("amount", String.valueOf(byBatCode.getAmount()));
            put("startTime", byBatCode.getIncomeDate());
            put("endTime", byBatCode.getDepartureDate());
            put("days", String.valueOf(byBatCode.getDayStayed()));
        }});
        log.info("发送支付成功的短信!");
    }

    @Override
    public void cancelExpireOrder() {

        //转换成mongoDb的时间
        Date date = DateUtil.dateToISODate(DateUtil.getNowDate());

        //查询过期订单
        Query query = new Query(Criteria.where("payStatus").is(1).and("state").is(1).and("expiredTime").lte(date));

        List<HotelOrder> hotelOrders = mongoTemplate.find(query, HotelOrder.class);

        if(CollectionUtils.isEmpty(hotelOrders )){
            log.info("当前无过期订单");
            return;
        }
        log.info("查询过期订单:{},过期订单数量:{}", JSONObject.toJSON(hotelOrders),hotelOrders.size());

        for (HotelOrder hotelOrder : hotelOrders) {

            log.info("orderCode:{},createTime={},expireTime={},因为未在规定时间内支付,订单已取消",hotelOrder.getOrderCode(),DateUtil.getFormat(hotelOrder.getCreateTime()),DateUtil.getFormat(hotelOrder.getExpiredTime()));
        }


        Update update = new Update().set("state", 5).set("updateTime",Timestamp.valueOf(LocalDateTime.now()));
        //修改订单状态
        UpdateResult updateResult = mongoTemplate.updateMulti(query, update, HotelOrder.class);


        log.info("修改订单状态结果:{}受影响",updateResult.getModifiedCount());

    }




    public void handleHotelOrder(HotelOrder order){
        try {
            BigDecimal couponPrice = new BigDecimal(100);
            Integer toPayPrice=order.getGoodsPrice().multiply(couponPrice).intValue();
            getRefundPay(order.getBatCode(),order.getBatCode()+"TK",String.valueOf(toPayPrice),String.valueOf(toPayPrice),order);
        }catch (Exception e){
            e.printStackTrace();
            log.error("getRefundPay error"+e);
        }
    }

    private void getRefundPay(String orderNum,String refundNum,String totalPrice,String refundPrice,HotelOrder order) throws Exception{
        log.info("---------------------> refund pay is start ");
        Map<String, String> map= WxPayUtils.refund(orderNum,refundNum,totalPrice,refundPrice);

        log.info("--------------------->"+map);

        String returnCode = map.get("return_code");

        if (returnCode.equals(GeneConstant.SUCCESS_UPPERCASE)) {
            log.info("---------------------> refund pay is success");
            order.setPayStatus(3);
            hotelOrderRepository.save(order);
        }
        log.info("---------------------> refund pay is end");
    }


}
