
package cn.enn.wise.ssop.service.order.handler.schedule;

import cn.enn.wise.ssop.service.order.config.enums.FrontPayStatusEnum;
import cn.enn.wise.ssop.service.order.config.enums.OrderCategoryEnum;
import cn.enn.wise.ssop.service.order.config.status.OrderStatusEnum;
import cn.enn.wise.ssop.service.order.config.status.PayStatusEnum;
import cn.enn.wise.ssop.service.order.model.OrderGoods;
import cn.enn.wise.ssop.service.order.model.Orders;
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
public class HotelSchedule {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderGoodsService orderGoodsService;

    @Scheduled
    public void autoCancelExpiredOrder(Long orderId){
        OrderGoods orderGoods = new OrderGoods();
        orderGoods.setGoodsType(OrderCategoryEnum.HOTEL.getValue());
        orderGoods.setPayStatus(PayStatusEnum.UN_PAY.getValue());
        List<OrderGoods> orderGoodsList = orderGoodsService.getOrderGoodsList(orderGoods);
        for(OrderGoods orderGoodsInfo : orderGoodsList){
            boolean b = StatusCheck.checkOrderInfoStatus(orderGoodsInfo, null, PayStatusEnum.UN_PAY, null, null, null);
            if(b){
                if(new Date().getTime()>orderGoodsInfo.getCreateTime().getTime() + orderGoodsInfo.getPayTimeOut()*60){
                    orderService.updateOrderStatus(orderGoodsInfo.getOrderId(), OrderStatusEnum.HOTEL_TIMEOUT_CANCEL,PayStatusEnum.UN_PAY,
                            null,null,null,
                            null,null,null,null,
                            null,"订单长时间未支付自动取消",true);
                }
            }
        }
    }

}

