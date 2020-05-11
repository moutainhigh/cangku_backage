package cn.enn.wise.ssop.service.order.handler.schedule;

import cn.enn.wise.ssop.service.order.config.enums.OrderCategoryEnum;
import cn.enn.wise.ssop.service.order.config.status.OrderStatusEnum;
import cn.enn.wise.ssop.service.order.config.status.PayStatusEnum;
import cn.enn.wise.ssop.service.order.model.OrderGoods;
import cn.enn.wise.ssop.service.order.service.OrderGoodsService;
import cn.enn.wise.ssop.service.order.service.OrderService;
import cn.enn.wise.ssop.service.order.utils.StatusCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class TicketSchedule {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderGoodsService orderGoodsService;

    @Scheduled
    public void autoCancelExpiredOrder(){
        //产品的超时时间进行取消
        OrderGoods orderGoods = new OrderGoods();
        orderGoods.setGoodsType(OrderCategoryEnum.TICKET.getValue());
        orderGoods.setPayStatus(PayStatusEnum.UN_PAY.getValue());
        List<OrderGoods> orderGoodsList = orderGoodsService.getOrderGoodsList(orderGoods);
        for(OrderGoods orderGoodsInfo : orderGoodsList){
            boolean b = StatusCheck.checkOrderInfoStatus(orderGoodsInfo, null, PayStatusEnum.UN_PAY, null, null, null);
            if(b){
                if(new Date().getTime()>orderGoodsInfo.getCreateTime().getTime() + orderGoodsInfo.getPayTimeOut()*60){
                    orderService.updateOrderStatus(orderGoodsInfo.getOrderId(), OrderStatusEnum.TICKET_TIMEOUT_CANCEL,null,
                            null,null,null,
                            null,null,null,null,
                            null,"订单长时间未支付自动取消",true);
                }
            }
        }
    }

    /**
     * 票过期（超过失效日期）-部分
     * @param orderId
     */
    @Scheduled
    public void ticketPartExpired(Long orderId){
        //有票号，（已出票，已取票）票没使用，体验日期不一样的过期
        //有票号，产品里边的票的体验时间过了，过期
        OrderGoods orderGoodsInfo = orderGoodsService.getOrderGoodsInfo(orderId);
        boolean b = StatusCheck.checkOrderInfoStatus(orderGoodsInfo, OrderStatusEnum.TICKET_WAIT_USE, PayStatusEnum.PAYED, null, null, null);
        boolean b1 = StatusCheck.checkOrderInfoStatus(orderGoodsInfo, OrderStatusEnum.TICKET_USED, PayStatusEnum.PAYED, null, null, null);
        if(b || b1){
            orderService.updateOrderStatus(orderId, OrderStatusEnum.TICKET_CANCEL,null,
                    null,null,null,
                    null,null,null,null,
                    null,"票过期（超过失效日期）-部分",false);
        }

    }

    /**
     * 票过期（超过失效日期）-全部
     * @param orderId
     */
    @Scheduled
    public void ticketAllExpired(Long orderId){
        //有票号，（已出票，已取票）票没使用，所有票都过期了
        OrderGoods orderGoodsInfo = orderGoodsService.getOrderGoodsInfo(orderId);
        boolean b = StatusCheck.checkOrderInfoStatus(orderGoodsInfo, OrderStatusEnum.TICKET_WAIT_USE, PayStatusEnum.PAYED, null, null, null);
        boolean b1 = StatusCheck.checkOrderInfoStatus(orderGoodsInfo, OrderStatusEnum.TICKET_USED, PayStatusEnum.PAYED, null, null, null);
        if(b || b1){
            orderService.updateOrderStatus(orderId, OrderStatusEnum.TICKET_CANCEL,null,
                    null,null,null,
                    null,null,null,
                    null,null,
                    "票过期（超过失效日期）-全部",false);
        }

    }

    /**
     * 全部当日体验完毕（体验日期3天后【失效日期】）
     * @param orderId
     */
    @Scheduled
    public void experienceFinishAfterThreeDays(Long orderId){
        //有票号，已核销，产品日期3天后已完成
        OrderGoods orderGoodsInfo = orderGoodsService.getOrderGoodsInfo(orderId);
        boolean b = StatusCheck.checkOrderInfoStatus(orderGoodsInfo, OrderStatusEnum.TICKET_WAIT_USE, PayStatusEnum.PAYED, null, null, null);
        boolean b1 = StatusCheck.checkOrderInfoStatus(orderGoodsInfo, OrderStatusEnum.TICKET_USED, PayStatusEnum.PAYED, null, null, null);
        if(b || b1){
            orderService.updateOrderStatus(orderId, OrderStatusEnum.TICKET_CANCEL,null,
                    null,null,null,
                    null,null,null,
                    null,null,
                    "全部当日体验完毕（体验日期3天后【失效日期】）",false);
        }

    }
}
