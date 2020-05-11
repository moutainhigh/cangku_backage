package cn.enn.wise.platform.mall.util;


import cn.enn.wise.platform.mall.bean.bo.GoodsProject;
import cn.enn.wise.platform.mall.bean.bo.Order;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * producer demo
 *
 * @author caiyt
 */
@Component
public class MessageSender {


    private Logger logger = LoggerFactory.getLogger(MessageSender.class);

    @Value("${queueconfig.prefix}")
    public String prefix;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private static final  String messageTitle = "运营状态变更通知";

    private static final  String orderMessageTitle = "下单通知";

    private static final  String orderExpireMessageTitle = "订单取消通知";

    private static final String orderWriteOffMessageTitle = "订单核销通知";

    /**
     * 运营App广播消息
     * @param msg
     */
    public void send(String msg) {
        sendMessage(msg,"1",messageTitle);
    }

    /**
     * 运营App广播消息
     * @param msg
     */
    public void send(String msg,Long projectId) {
        sendMessage(msg,"1",messageTitle,projectId);
    }

    /**
     * 订单广播消息
     * @param msg
     */
    public void sendOrderMessage(String msg) {
        sendMessage(msg,"2",orderMessageTitle);
    }

    /**
     * 订单广播消息
     * @param msg
     */
    public void sendOrderMessage(String msg,Long projectId,Integer isDistributeOrder,Long distributeId,String phone) {
        sendMessage(msg,"2",orderMessageTitle,projectId,isDistributeOrder,distributeId,phone);
    }

    /**
     * 发送订单取消的消息
     * @param msg
     *          消息内容
     * @param projectId
     *          项目Id
     * @param isDistributeOrder
     *          是否是分销订单
     * @param distributeId
     *          分销商Id
     * @param phone
     *          分销商手机号
     */
    public void sendOrderRefundMessage(String msg,Long projectId,Integer isDistributeOrder,Long distributeId,String phone){

        sendMessage(msg,"3",orderExpireMessageTitle,projectId,isDistributeOrder,distributeId,phone);

    }

    /**
     * 发送订单核销的消息
     * @param msg
     *          消息内容
     * @param projectId
     *          项目Id
     * @param isDistributeOrder
     *          是否是分销订单
     * @param distributeId
     *          分销商Id
     * @param phone
     *        分销商手机号
     */
    public void sendWriteOffOrder(String msg,Long projectId,Integer isDistributeOrder,Long distributeId,String phone){

        sendMessage(msg,"4",orderWriteOffMessageTitle,projectId,isDistributeOrder,distributeId,phone);
    }

    /**
     * 广播消息
     * @param msg
     */
    public void sendMessage(String msg,String messageType,String messageTitle) {
        MessageProperties messageProperties = new MessageProperties();
        Integer expiration = 1000*60*30;
        messageProperties.setExpiration(expiration.toString());

        Map<String ,Object> result = new HashMap<>();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String timeStr = sdf.format(new Date());
        result.put("title",messageTitle);
        result.put("type",messageType);
        result.put("msg",msg);
        result.put("time", timeStr);
        msg = JSON.toJSONString(result);
        Message message = new Message(msg.getBytes(),messageProperties);
        rabbitTemplate.convertAndSend(prefix+"amq.fanout",prefix+"amp.fanout", message);
    }

    /**
     * 广播消息
     * @param msg
     */
    public void sendMessage(String msg,String messageType,String messageTitle,Long projectId) {
        MessageProperties messageProperties = new MessageProperties();
        Integer expiration = 1000*60*30;
        messageProperties.setExpiration(expiration.toString());

        Map<String ,Object> result = new HashMap<>();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String timeStr = sdf.format(new Date());
        result.put("title",messageTitle);
        result.put("type",messageType);
        result.put("msg",msg);
        result.put("time", timeStr);
        result.put("projectId",projectId);
        msg = JSON.toJSONString(result);
        Message message = new Message(msg.getBytes(),messageProperties);

        rabbitTemplate.convertAndSend(prefix+"amq.fanout",prefix+"amq.fanout", message);
    }

    /**
     * 广播消息
     * @param msg
     */
    public void sendMessage(String msg,
                            String messageType,
                            String messageTitle,
                            Long projectId,
                            Integer isDistributeOrder,
                            Long distributeId,
                            String phone) {
        MessageProperties messageProperties = new MessageProperties();
        Integer expiration = 1000*60*30;
        messageProperties.setExpiration(expiration.toString());

        Map<String ,Object> result = new HashMap<>();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String timeStr = sdf.format(new Date());
        result.put("title",messageTitle);
        result.put("type",messageType);
        result.put("msg",msg);
        result.put("time", timeStr);
        result.put("projectId",projectId);
        result.put("isDistributeOrder",isDistributeOrder);
        result.put("distributeId",distributeId);
        result.put("phone",phone);
        msg = JSON.toJSONString(result);
        Message message = new Message(msg.getBytes(),messageProperties);

        rabbitTemplate.convertAndSend(prefix+"amq.fanout",prefix+"amq.fanout", message);
    }



    /**
     * 更新最低价格消息
     * @param projectId 项目id,BigDecimal 对应的商品组低价
     */
    public void sendGoodsPrice(Long projectId, BigDecimal price,BigDecimal distributionPrice) {
        Map<String ,Object> result = new HashMap<>();
        result.put("projectId",projectId);
        result.put("price",price);
        result.put("distributionPrice",distributionPrice);
        logger.info("sendGoodsPrice : "+"projectId:"+projectId);
        rabbitTemplate.convertAndSend(prefix+"order.direct",prefix+"goodsPriceQueue",JSON.toJSONString(result));
    }

    /**
     * 发送项目更新信息
     * @param goodsProjectList 本景区所有项目
     */
    public void sendGoodsProjectUpdateQueue(List<GoodsProject> goodsProjectList) {
        rabbitTemplate.convertAndSend(prefix+"order.direct",prefix+"goodsProjectUpdateQueue",JSON.toJSONString(goodsProjectList));
    }

    /**
     * 发送项目昨天体验人数
     * @param projectYesterdayNums 本景区所有项目的昨天体验人数  projectId, number
     */
    public void sendProjectYesterdayNumsQueue(List<Map<String,Object>> projectYesterdayNums) {
        rabbitTemplate.convertAndSend(prefix+"order.direct",prefix+"projectYesterdayNumsQueue",JSON.toJSONString(projectYesterdayNums));
    }





    /**
     * 发送短信
     *
     */
    public void sendSms(String phone, String signName, HashMap<String,String> templateMap) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("phone",phone);
        jsonObject.put("signName",signName);
        jsonObject.put("templateMap",templateMap);
        this.rabbitTemplate.convertAndSend("sms.direct", "sendSms",jsonObject.toJSONString());
    }

    /**
     * 统一发送短信队列
     * 短信模板在member项目中,这里只根据具体的模板需要的参数作为key,值作为value
     * 公共参数为companyId
     * //1 发送短信验证码
     * //2 热气球退单
     * //3 分销商审核不通过
     * //4 分销商审核通过
     * //5 订单预定成功
     * //6 核销成功
     * @param map
     */
    public void sendSmsV2(Map<String,String> map){
        String msg = JSONObject.toJSONString(map);
        logger.info("发送短信:sms.direct.v2 :"+msg);
        this.rabbitTemplate.convertAndSend("sms.direct.v2","sendSmsv2",msg);

    }

    /**
     * 发送短信
     * @param map
     *
     * key companyId 11 涠洲岛短信通道
     * key phone 手机号
     * key content 短信内容 短信签名需要自己拼，例如：【新绎游船】
     */
    public void sendSmsV3(Map<String,String> map){
        String msg = JSONObject.toJSONString(map);
        logger.info("发送短信:sms.direct.v3 :"+msg);
        this.rabbitTemplate.convertAndSend("sms.direct.v3","sendSmsv3",msg);

    }

    /**
     * 发送创建分销订单的消息
     * @param msg
     */
    public void sendCreateDistributeOrder(String msg){

        //msg="{distributorId=10, orderId=21, goodsId=90, goodsName=热气球单人体验票, goodsCount=1, goodsPrice=199.00, orderPrice=199.00, contacts=15303786335, snapshot={\"amount\":1,\"distributorId\":10,\"expiredTime\":1560324360000,\"goodsId\":90,\"goodsName\":\"热气球单人体验票\",\"goodsPrice\":199.00,\"id\":21,\"isDistributeOrder\":1,\"name\":\"白杰\",\"orderCode\":\"20190612150559780535463r\",\"orderSource\":2,\"payType\":\"weixin\",\"phone\":\"15303786335\",\"scenicId\":5,\"siglePrice\":199.00,\"state\":1,\"type\":6,\"userId\":1}}";
        this.rabbitTemplate.convertAndSend(prefix+"order.direct",prefix+"generateDistributorOrders",msg);
    }

    /**
     * 发送处理订单的消息
     * @param msg
     */
    public void sendProcessOrder(String msg ){
        this.rabbitTemplate.convertAndSend(prefix+"order.direct",prefix+"processOrder",msg);
    }

    /**
     * 订单支付成功同步对应分销商状态
     * @param
     */
    public void sendSyncDistributorOrderStatus(Long orderId,Byte status){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id",orderId);
        jsonObject.put("status",status);
        logger.info("send message to syncDistributorOrderStatus id ="+orderId+" status="+status);
        this.rabbitTemplate.convertAndSend(prefix+"order.direct",prefix+"syncDistributorOrderStatus",jsonObject.toJSONString());
    }

    /**
     * 体验完成确定生成分销订单的数量
     * @param
     */
    public void sendSyncDistributorOrderStatusByAmount(Long orderId,Byte status,Integer amount){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id",orderId);
        jsonObject.put("status",status);
        jsonObject.put("amount",amount);
        logger.info("send message to syncDistributorOrderStatus id =>{} ,status=>{},amount=>{}",orderId,status,amount);
        this.rabbitTemplate.convertAndSend(prefix+"order.direct",prefix+"syncDistributorOrderStatus",jsonObject.toJSONString());
    }


    /**
     * 核销订单选择核销策略
     * @param
     */
    public void sendCheckOrder(Long orderId,Long strategyItemId){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("orderId",orderId);
        jsonObject.put("strategyItemId",strategyItemId);
        logger.info("send message to checkOrderQueue orderId ="+orderId+" strategyItemId="+strategyItemId);
        this.rabbitTemplate.convertAndSend(prefix+"order.direct",prefix+"checkOrderQueue",jsonObject.toJSONString());
    }


    /**
     * 发送退款商品数量
     * @param orders 订单对象
     * @param amount 退款数量
     */
    public void sendRefundOrderAmount(Order orders, Integer amount){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("orders",orders);
        jsonObject.put("amount",amount);
        logger.info("send message to refundOrderAmount  body:{}",jsonObject.toJSONString());
        this.rabbitTemplate.convertAndSend(prefix+"order.direct",prefix+"refundOrderAmount",jsonObject.toJSONString());
    }



}

