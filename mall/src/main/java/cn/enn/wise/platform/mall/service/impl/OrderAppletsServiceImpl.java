package cn.enn.wise.platform.mall.service.impl;

import cn.enn.wise.platform.mall.bean.bo.Orders;
import cn.enn.wise.platform.mall.bean.param.GoodsReqParam;
import cn.enn.wise.platform.mall.bean.param.PayParam;
import cn.enn.wise.platform.mall.bean.vo.*;
import cn.enn.wise.platform.mall.config.pay.WxPayService;
import cn.enn.wise.platform.mall.mapper.GoodsExtendMapper;
import cn.enn.wise.platform.mall.mapper.GoodsMapper;
import cn.enn.wise.platform.mall.mapper.OrderAppletsMapper;
import cn.enn.wise.platform.mall.service.OrderAppletsService;
import cn.enn.wise.platform.mall.util.*;
import cn.enn.wise.platform.mall.util.exception.BusinessException;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * @author bj
 * @Description 小程序订单相关逻辑
 * @Date19-5-24 下午7:14
 * @Version V1.0
 **/

@Service
public class OrderAppletsServiceImpl implements OrderAppletsService {

    private static final Logger logger = LoggerFactory.getLogger(OrderAppletsServiceImpl.class);

    @Value("${spring.profiles.active}")
    private String profiles;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    final String HQ_REDIS_INCR_KEY = "HQ_REDIS_INCR_KEY";

    @Autowired
    private GoodsMapper goodsMapper;


    @Autowired
    private OrderAppletsMapper orderAppletsMapper;


    @Autowired
    private MessageSender messageSender;

    @Autowired
    private WxPayService wxPayService;

    @Autowired
    private RemoteServiceUtil remoteServiceUtil;


    @Autowired
    private GoodsExtendMapper goodsExtendMapper;
    /**
     * 支付下单
     *
     * @return
     */
    @Override
    public Object saveOrder(PayParam payParam) throws Exception {
        logger.info("===开始下单===");
        Orders orders = completionOrderInfo(payParam);
        if ("prod".equals(profiles)) {
           orders.setProfiles("");

        } else {
            orders.setProfiles(profiles);
        }
        int i = 0;
        try {
            orders.setUserWechatName(GeneUtil.filterEmoji(orders.getUserWechatName()));
            logger.info("纪念一下===过滤后的微信名字===" + orders.getUserWechatName());

            i = orderAppletsMapper.insertOrder(orders);
            Orders orderById = orderAppletsMapper.getOrderById(orders.getId());
            orders.setOrderCode(orderById.getOrderCode());
        } catch (Exception e) {
            logger.info("===微信昵称中可能含有非法字符==="+e);
            orders.setUserWechatName("Unknown");

            i = orderAppletsMapper.insertOrder(orders);
            Orders orderById = orderAppletsMapper.getOrderById(orders.getId());
            orders.setOrderCode(orderById.getOrderCode());
        }
        if (i < 1) {
            throw new BusinessException(GeneConstant.BUSINESS_ERROR, "===插入订单失败===");
        } else {
            logger.info("===插入订单成功!===");
            String s = JSONObject.toJSONString(orders);
            logger.info("sendmessage : " + s);
            logger.info("===将订单放入消息队列处理===start");

            messageSender.sendProcessOrder(s);
            logger.info("===将订单放入消息队列处理===end");
            String  totalFee = orders.getGoodsPrice().multiply(new BigDecimal("100")).setScale(0).toString();

//            Object pay = wxPayService.pay(payParam.getScenicId(), orders.getGoodsName(), payParam.getOpenId(), orders.getOrderCode(), totalFee, payParam.getIp());
            return null;
        }
    }


//    private Object pay(PayParam payParam, Orders orders) throws Exception {
//
//        PayResult payResult = remoteServiceUtil.getPayResult(payParam, orders);
//
//        if (payResult != null) {
//
//            String prepayId = payResult.getValue().getPrepayId();
//
//            Orders updateOrder = new Orders();
//            updateOrder.setPrepayId(prepayId);
//            updateOrder.setId(orders.getId());
//            orderAppletsMapper.updateOrder(updateOrder);
//            return payResult.getValue().getObj();
//        }
//        throw new BusinessException(GeneConstant.BUSINESS_ERROR, "获取支付结果失败!");
//    }

    /**
     * 补全订单信息
     *
     * @param payParam
     * @return
     */
    private Orders completionOrderInfo(PayParam payParam) {
        logger.info("===补全订单信息===");
        Orders orders = new Orders();
        //查询票信息
        GoodsApiResVO goodsById = goodsMapper.getGoodsById(payParam.getGoodsReqParam());
        if (goodsById == null) {
            throw new BusinessException(GeneConstant.BUSINESS_ERROR, "===未查询到票信息===");
        }
        GoodsApiExtendResVo goodsApiExtendResVo = goodsById.getGoodsApiExtendResVoList().get(0);
        if (goodsApiExtendResVo == null) {
            throw new BusinessException(GeneConstant.BUSINESS_ERROR, "===未查询到商品的SKU信息===");
        }
        //单价
        BigDecimal salePrice = goodsApiExtendResVo.getSalePrice().setScale(2, BigDecimal.ROUND_HALF_DOWN);
        //总价
        BigDecimal totalPrice = null;
        //判断是否是
        if (goodsById.getMaxNum() != 1) {
            //多人票
            totalPrice = salePrice.setScale(2, BigDecimal.ROUND_HALF_DOWN);
            orders.setAmount(1L);
            orders.setMaxNumberOfUsers(goodsById.getMaxNum());

        } else {
            orders.setMaxNumberOfUsers(1);
            //商品数量
            Long amount = Long.valueOf(payParam.getAmount());
            orders.setAmount(amount);

            if ("179".equals(GeneUtil.formatBigDecimal(salePrice).toString()) && amount >= 8) {
                //价格为179的购票数量大于等于8 按照每张票169算
                salePrice = new BigDecimal("169");
            }
            if ("199".equals(GeneUtil.formatBigDecimal(salePrice).toString())) {
                if (amount >= 4 && amount < 8) {
                    salePrice = new BigDecimal("179");
                }
                if (amount >= 8) {
                    salePrice = new BigDecimal("169");
                }
            }

            totalPrice = salePrice.multiply(new BigDecimal(payParam.getAmount())).setScale(2, BigDecimal.ROUND_HALF_DOWN);

        }

        orders.setUserId(payParam.getUserId());
        orders.setSiglePrice(salePrice);
        orders.setGoodsPrice(totalPrice);
        orders.setScenicId(payParam.getScenicId());
        orders.setGoodsName(goodsById.getGoodsName());
        orders.setType(Long.valueOf(GeneConstant.INT_6));
        orders.setGoodsId(goodsApiExtendResVo.getId());
        orders.setState(GeneConstant.INT_1);
        // 订单过期时间 20分钟,未支付订单自动过期
        orders.setExpiredTime(new Timestamp(DateUtil.getNextTime(20).getTime()));
        orders.setPayType(payParam.getPayType());
        orders.setEnterTime(goodsApiExtendResVo.getOperationDate());
        orders.setIdNumber(payParam.getIdNumber());
        orders.setName(payParam.getName());
        orders.setUnchekedNum(orders.getAmount().intValue());
        orders.setCreateTime(Timestamp.valueOf(LocalDateTime.now()));
        Integer isDistributeOrder = 0;
        Integer orderSource = 1;
        Integer profitStatus = 2;
        if (StringUtils.isNotEmpty(payParam.getDriverMobile())) {
            logger.info("===当前分销商手机号为===" + payParam.getDriverMobile());
            ResponseEntity checkUserResult = remoteServiceUtil.getCheckUserResult(payParam.getDriverMobile(),orders.getScenicId());
            if (checkUserResult != null && checkUserResult.getResult() == 1) {
                //TODO 节点2 扫码下单此处记录日志
                String id = ((JSONObject) (checkUserResult.getValue())).get("id").toString();
                //获取用户角色信息
                String userRole = ((JSONObject) (checkUserResult.getValue())).getString("userRole");
                orders.setRoleId(userRole);
                orders.setSnapshot(((JSONObject) (checkUserResult.getValue())).toJSONString());
                logger.info("===分销商当前有效id===" + id + ",角色信息userRole=" + userRole);
                orders.setDistributorId(Long.valueOf(id));
                //用户具有分销身份
                isDistributeOrder = 1;
                orderSource = 2;
                profitStatus = 1;

            }
        }

        orders.setProfitStatus(profitStatus);
        orders.setIsDistributeOrder(isDistributeOrder);
        orders.setOrderSource(orderSource);
        orders.setShouldPay(totalPrice);
        orders.setPayStatus(1);
        orders.setActualPay(new BigDecimal("0.00"));
        orders.setUserWechatName(payParam.getUserWechatName());
        orders.setPhone(payParam.getPhone());
        return orders;
    }


    /**
     * 立即预定信息
     *
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public TicketResVo predestinate(GoodsReqParam goodsReqParam) throws Exception {
        logger.info("===开始预定门票 bookingTicket ===");
        GoodsApiResVO goodsById = goodsMapper.getGoodsById(goodsReqParam);
        logger.info("===获取到票信息==" + JSONObject.toJSONString(goodsById));
        if (goodsById == null) {
            logger.info("===要预定的商品不存在===");
            throw new BusinessException(GeneConstant.BUSINESS_ERROR, "要预定的商品不存在");
        }

        GoodsApiExtendResVo goodsApiExtendResVo = goodsById.getGoodsApiExtendResVoList().get(0);
        if (goodsApiExtendResVo == null) {
            logger.info("===该票没有运营时段信息===");
            throw new BusinessException(GeneConstant.BUSINESS_ERROR, "该时段没有票信息");
        }

        logger.info("===预定门票信息===");
        TicketResVo ticketResVo = new TicketResVo();
        //二销产品
        //门票类型
        ticketResVo.setGoodsId(goodsById.getId());
        //判断是单人票还是多人票
        if (goodsById.getIsPackage() == 1) {
            //多人票
            ticketResVo.setAmount(goodsById.getMaxNum());
            ticketResVo.setRealTips(GeneUtil.formatBigDecimal(goodsReqParam.getRealTips()).toString());
        } else {
            //        默认数量为1
            ticketResVo.setAmount(GeneConstant.INT_1);
        }

        ticketResVo.setGoodsName(goodsById.getGoodsName());

        //入园时间
//        ticketResVo.setEnterTime(new SimpleDateFormat("yyyy-MM-dd").parse(goodsReqParam.getOperationDate()));
        ticketResVo.setExperienceTime(goodsReqParam.getOperationDate() + " " + goodsApiExtendResVo.getTimespan());

        //sku对应的价格
        ticketResVo.setSiglePrice(GeneUtil.formatBigDecimal(goodsApiExtendResVo.getSalePrice()).toString());
        ticketResVo.setPeriodId(goodsReqParam.getPeriodId());
        ticketResVo.setTimeFrame(goodsReqParam.getTimeFrame());
        logger.info("===预定门票结束===");
        return ticketResVo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object payOriginalOrder(PayParam payParam) throws Exception {

        Orders orders = new Orders();
        orders.setState(1);
        orders.setOrderCode(payParam.getOrderCode());

        orders = orderAppletsMapper.getOrderInfoByOrderCode(orders);

        if (orders.getId() == null) {
            throw new BusinessException(GeneConstant.BUSINESS_ERROR, "===该订单不存在，或者已经超过支付时间===");
        }

        logger.info("===插入订单子表成功===");
        String  totalFee = orders.getGoodsPrice().multiply(new BigDecimal("100")).setScale(0).toString();

//        Object pay = wxPayService.pay(payParam.getScenicId(), orders.getGoodsName(), payParam.getOpenId(), orders.getOrderCode(), totalFee, payParam.getIp());

        return null;
    }


    /**
     * 获取用户订单
     *
     * @param orders
     * @return
     */
    @Override
    public List<Orders> getUserOrder(Orders orders) {
        return orderAppletsMapper.getUserOrder(orders);
    }

    /**
     * 获取订单详情
     *
     * @param orders
     * @return
     */
    @Override
    public Orders getOrderByIdAndUserId(Orders orders) {
        Orders orderByIdAndUserId = orderAppletsMapper.getOrderByIdAndUserId(orders);

        Integer state = orderByIdAndUserId.getState();
        if (state == 1) {
            Timestamp expiredTime = orderByIdAndUserId.getExpiredTime();
            Timestamp currentTime = Timestamp.valueOf(LocalDateTime.now());
            long leaveTime = DateUtil.differentMunitessIsNotAbs(currentTime, expiredTime);
            if (leaveTime <= 0) {
                orderByIdAndUserId.setState(5);
                orders.setState(5);
                orders.setActualPay(new BigDecimal(0));
                orders.setId(orderByIdAndUserId.getId());
                //手动修改订单状态
                orderAppletsMapper.refundOrder(orders);
            } else {
                orderByIdAndUserId.setLeaveTime(leaveTime);
            }

        }
        return orderByIdAndUserId;
    }


    /**
     * 取消订单
     *
     * @param orders
     * @return
     */
    @Override
    public int refundOrder(Orders orders) throws Exception {
        Orders orderInfoByOrderCode = orderAppletsMapper.getOrderInfoByOrderCode(orders);
        if (orderInfoByOrderCode == null) {
            return 0;
        }
        if (orderInfoByOrderCode.getState() == 2) {
            logger.info("userWeChatName" + orders.getUserWechatName() + "userId" + orders.getUserId() + "===取消订单===" + orders.getOrderCode());
            orders.setState(5);
            orders.setActualPay(new BigDecimal(0));
            orders.setId(orderInfoByOrderCode.getId());
            int i = orderAppletsMapper.refundOrder(orders);
            //订单取消后同步分销订单状态为6，订单流转控制值为4
            messageSender.sendSyncDistributorOrderStatus(orderInfoByOrderCode.getId(), Byte.valueOf("4"));
            return i;
        } else {
            logger.info("userWeChatName" + orders.getUserWechatName() + "userId" + orders.getUserId() + ",订单状态不是待支付状态或者待使用,无法取消订单");
            return 0;

        }

    }

    /**
     * 订单完成支付后调用,更改支付状态和订单状态
     */
    @Override
    public int complateOrder(Orders orders) throws Exception {
        //根据订单号查询订单信息
        Orders orderInfoByOrderCode = orderAppletsMapper.getOrderInfoByOrderCode(orders);

        //#region 下单给运营App发送消息
        try{
            String msg="订单时间:%s,订单包含游客%s人,体验时间为%s,详情请查看运营App订单内容。";
            String orderTime = orderInfoByOrderCode.getCreateTime().toString().substring(0,16);
            String orderAmount = orderInfoByOrderCode.getAmount().toString();
            String orderCheckInTime =orderInfoByOrderCode.getEnterTime().toString();
            String period = goodsExtendMapper.selectById(orderInfoByOrderCode.getGoodsId()).getTimespan();
            String orderUseTime = String.format(msg,orderTime,orderAmount,orderCheckInTime+" "+period);
            messageSender.sendOrderMessage(orderUseTime);
        }catch(Exception ex){
            logger.info("下单给运营App发送消息:"+ex);
        }
        //#endregion

        //支付状态为2的订单代表已经处理过的订单
        if (orderInfoByOrderCode.getPayStatus() != null && orderInfoByOrderCode.getPayStatus() == 2) {
            logger.info("===该订单已经支付成功!===");
            return 1;
        }

        if (orderInfoByOrderCode.getPayStatus() == 3) {
            logger.info("===该笔订单已退款===" + orders.getOrderCode() + ",orderStatus:" + orderInfoByOrderCode.getState() + ",payStatus=" + orderInfoByOrderCode.getPayStatus());
        }
        //只有待支付的订单才说明,该笔订单未处理过
        if (orderInfoByOrderCode.getState() != 1) {
            logger.info("====该订单已经进行过支付处理===" + orders.getOrderCode() + ",orderStatus:" + orderInfoByOrderCode.getState());
            return 1;
        }


        logger.info("===该笔订单未更改回掉状态===" + orders.getOrderCode() + ",orderStatus:" + orderInfoByOrderCode.getState());
        orders.setPayStatus(2);
        orders.setState(2);
        orders.setActualPay(orderInfoByOrderCode.getGoodsPrice());
        orders.setId(orderInfoByOrderCode.getId());
        int i = orderAppletsMapper.updateOrderByOrderCode(orders);
        logger.info("===更新订单支付状态===");
        messageSender.sendSms(orderInfoByOrderCode.getPhone(), "SMS_167973143", new HashMap<String, String>() {{
//            GeneUtil.getSendMessageContent(orderInfoByOrderCode.getName(), orderInfoByOrderCode.getGoodsName(), orderInfoByOrderCode.getOrderCode())
            put("name", orderInfoByOrderCode.getName());
            put("goodName", orderInfoByOrderCode.getGoodsName());
            put("orderCode", orderInfoByOrderCode.getOrderCode());
        }});
        logger.info("===发送支付成功的短信===");
        if (orderInfoByOrderCode.getOrderSource() == 2 && orderInfoByOrderCode.getIsDistributeOrder() == 1) {
            Orders paramOrders = new Orders();
            paramOrders.setId(orderInfoByOrderCode.getId());
            //此状态与分销订单要改变的状态一致
            paramOrders.setState(1);
            logger.info("send message " + JSONObject.toJSONString(paramOrders));
            messageSender.sendSyncDistributorOrderStatus(paramOrders.getId(), paramOrders.getState().byteValue());
        }
        return i;
    }

    /**
     * 取消过期的未支付的订单
     */
    @Override
    public void cancelExpireOrder() {

        List<Orders> orders = orderAppletsMapper.selectExpireOrder();

        if (CollectionUtils.isNotEmpty(orders)) {

            orders.stream().forEach(order -> {
                order.setState(5);
                order.setActualPay(new BigDecimal(0));
                orderAppletsMapper.refundOrder(order);
                logger.info("orderCode=" + order.getOrderCode() + ",createTime=" + order.getCreateTime() + ",expireTime=" + order.getExpiredTime() + ",因为未在规定时间内支付,订单已取消");
            });
        }


    }

    /**
     * 生成订单号
     * 统一订单号规则：
     * 业务编号+年份后两位+月份两位+4位流水
     * 业务编号：热气球 HQ 门票PT 车ZC 游船YC 餐CY
     *
     * @return
     */
    public String generateOrderCode(String businessCode) {

         Map<String, String> map = orderAppletsMapper.selectMaxOrderCode();

        return map.get("orderCode");
    }


}
