package cn.enn.wise.platform.mall.util;

import cn.enn.wise.platform.mall.bean.bo.GoodsExtend;
import cn.enn.wise.platform.mall.bean.bo.OrderTicket;
import cn.enn.wise.platform.mall.bean.bo.Orders;
import cn.enn.wise.platform.mall.bean.bo.autotable.GoodsPackageItem;
import cn.enn.wise.platform.mall.bean.vo.DistributeOrderVo;
import cn.enn.wise.platform.mall.mapper.GoodsExtendMapper;
import cn.enn.wise.platform.mall.mapper.OrderAppletsMapper;
import cn.enn.wise.platform.mall.mapper.WzdGoodsAppletsMapper;
import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * consumer-demo
 *
 * @author caiyt
 */
@Component
public class MessageReceiver {

    private static final Logger logger = LoggerFactory.getLogger(MessageReceiver.class);

    @Resource
    private OrderAppletsMapper orderAppletsMapper;

    @Resource
    private GoodsExtendMapper goodsExtendMapper;

    @Autowired
    private MessageSender messageSender;

    @Resource
    private WzdGoodsAppletsMapper wzdGoodsAppletsMapper;


    @RabbitListener(queues = "${queueconfig.prefix}checkDistributeOrderCanProfit")
    public void checkDistributeOrderCanProfit(Message message, Channel channel) throws Exception {
        String checkDistributeOrderCanProfitStr = new String(message.getBody());
        logger.info("reciver message..." + checkDistributeOrderCanProfitStr);
        try {
            JSONObject jsonObject = JSONObject.parseObject(checkDistributeOrderCanProfitStr);
            Integer profitStatus = jsonObject.getInteger("profitStatus");
            Long orderId = jsonObject.getLong("orderId");
            logger.info("update "+orderId +" set profitStatus = " +profitStatus);
            if(profitStatus == 2){

                orderAppletsMapper.updateOrdersProfitStatus(profitStatus,orderId,0);

            }
            if(profitStatus == 1){

                orderAppletsMapper.updateOrdersProfitStatus(profitStatus,orderId,1);
            }
            //手动确认Ack
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
        }catch (Exception e){
            logger.info("===checkDistributeOrderCanProfit==="+e);
        }
    }





     @RabbitListener(queues = "${queueconfig.prefix}processOrder")
    public void processOrder(Message message, Channel channel) throws Exception {

         try {
             String processOrderStr = new String(message.getBody());
             logger.info("process" + processOrderStr);


             Orders orders = JSONObject.parseObject(processOrderStr, Orders.class);

             processDistributeOrder(orders);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
        }catch (Exception e){
            logger.info("===处理订单失败==="+e);
        }
    }
    /**
     *处理订单
     * @param orderInfoByOrderCode
     */
    @Transactional(rollbackFor = Exception.class)
    public void processDistributeOrder( Orders orderInfoByOrderCode) {
        Long amount = orderInfoByOrderCode.getAmount();
        logger.info("===处理订单===");
        List<OrderTicket> orderTickets = new ArrayList<>();
        if(orderInfoByOrderCode.getOrderType() == 4){
            // 查询套装详情
            List<GoodsPackageItem> byPackageId = wzdGoodsAppletsMapper.getPackageGoodsByPackageId(orderInfoByOrderCode.getGoodsId());
            if(CollectionUtils.isNotEmpty(byPackageId)){
                //每张票包含的每个商品生成一条记录

               for (int i = 0 ;i <amount;i++){
                   byPackageId.parallelStream().forEach( packageItem -> {
                       OrderTicket orderTicket = OrderTicket
                               .builder()
                               .ticketCode(orderInfoByOrderCode.getOrderCode())
                               .createTime(orderInfoByOrderCode.getCreateTime())
                               .orderId(orderInfoByOrderCode.getId())
                               .goodsId(packageItem.getGoodsId())
                               .goodsName(packageItem.getGoodsName())
                               .goodsExtendId(packageItem.getGoodsExtendId())
                               .projectId(packageItem.getProjectId())
                               .projectName(packageItem.getProjectName())
                               .singlePrice(packageItem.getSalePrice())
                               .couponPrice(packageItem.getSalePrice())
                               .projectPlaceId(packageItem.getServicePlaceId())
                               .build();
                       orderTickets.add(orderTicket);

                   });


               }

            }else {
                logger.error("套装商品详情为空!");
            }

        }else {
            Map<String, Object> info = wzdGoodsAppletsMapper.getGoodsAndProjectByExtendId(orderInfoByOrderCode.getGoodsId());
            if(MapUtils.isEmpty(info)){
                logger.error("获取商品信息出错：",info);
            }
            boolean flag = false;
            //获取优惠金额
            BigDecimal couponPrice = orderInfoByOrderCode.getCouponPrice();
            if(couponPrice != null && new BigDecimal(0).compareTo(couponPrice) == -1){
                flag = true;
            }
            //累计金额
            BigDecimal countPrice = new BigDecimal(0);
            for (int j = 0; j < amount; j++) {
               OrderTicket ticket = OrderTicket.builder()
                       .ticketCode(orderInfoByOrderCode.getOrderCode())
                       .createTime(orderInfoByOrderCode.getCreateTime())
                       .orderId(orderInfoByOrderCode.getId())
                       .goodsId(Long.parseLong(info.get("goodsId").toString()))
                       .goodsName(orderInfoByOrderCode.getGoodsName())
                       .goodsExtendId(orderInfoByOrderCode.getGoodsId())
                       .singlePrice(orderInfoByOrderCode.getSiglePrice())
                       .couponPrice(orderInfoByOrderCode.getSiglePrice())
                       .projectId(Long.parseLong(info.get("projectId").toString()))
                       .projectName(info.get("projectName").toString())
                       .build();
               //计算优惠后的分摊价格
               if(flag){

                   //每个商品分摊的价格四舍五入
                   BigDecimal divide = couponPrice.divide(new BigDecimal(amount),2,BigDecimal.ROUND_HALF_DOWN);
                   logger.info("每个商品分摊价格为:"+divide);
                   //商品单价 - 优惠价格
                   BigDecimal subtract = ticket.getSinglePrice().subtract(divide).setScale(2,BigDecimal.ROUND_HALF_DOWN);
                   if(amount-j == 1){
                       //最后一次循环，也就是最后一个商品
                       //最后一个商品单价 = 订单金额 - 优惠金额 - 每个商品分摊价*已经分摊的个数
                       ticket.setCouponPrice(orderInfoByOrderCode.getGoodsPrice().subtract(couponPrice).subtract(countPrice));
                       logger.info("最后一个商品的退款金额为:"+ticket.getSinglePrice());
                   }else {
                       //加入总金额中
                       countPrice = countPrice.add(subtract);
                       ticket.setCouponPrice(subtract);
                       logger.info("每个商品的退款金额为:"+subtract);
                   }

               }


               orderTickets.add(ticket);
           }

       }
        orderAppletsMapper.insertOrdertTicket(orderTickets);
        logger.info("===插入订单子表成功===");

        if (orderInfoByOrderCode != null  && orderInfoByOrderCode.getIsDistributeOrder() == 1) {
            createDistributeOrder(orderInfoByOrderCode);
        }

        logger.info("===订单处理完成===");
    }

    /**
     * 创建分销订单
     * @param orderInfoByOrderCode
     */
    public void createDistributeOrder(Orders orderInfoByOrderCode) {
        logger.info("===开始生成分销账单===");
        DistributeOrderVo distributeOrderVo = new DistributeOrderVo();
        distributeOrderVo.setDistributorId(String.valueOf(orderInfoByOrderCode.getDistributorId()));
        distributeOrderVo.setOrderId(String.valueOf(orderInfoByOrderCode.getId()));
        //该商品Id是主表id,订单表中的商品id为商品扩展表的id
        //
        GoodsExtend goodsExtend = goodsExtendMapper.selectById(orderInfoByOrderCode.getGoodsId());
        if (goodsExtend != null) {
            distributeOrderVo.setGoodsId(String.valueOf(goodsExtend.getGoodsId()));
        }
        distributeOrderVo.setGoodsName(String.valueOf(orderInfoByOrderCode.getGoodsName()));
        distributeOrderVo.setGoodsCount(String.valueOf(orderInfoByOrderCode.getAmount()));
        distributeOrderVo.setGoodsPrice(String.valueOf(orderInfoByOrderCode.getSiglePrice()));
        distributeOrderVo.setOrderPrice(String.valueOf(orderInfoByOrderCode.getGoodsPrice()));
        distributeOrderVo.setContacts(String.valueOf(orderInfoByOrderCode.getPhone()));
        distributeOrderVo.setSnapshot(JSONObject.toJSONString(orderInfoByOrderCode));
        distributeOrderVo.setRoleId(orderInfoByOrderCode.getRoleId());
        String messageStr = JSONObject.toJSONString(distributeOrderVo);
        logger.info("===发送分销订单消息start==="+messageStr);
        messageSender.sendCreateDistributeOrder(messageStr);
        logger.info("===发送分销订单 end===");
    }
}
