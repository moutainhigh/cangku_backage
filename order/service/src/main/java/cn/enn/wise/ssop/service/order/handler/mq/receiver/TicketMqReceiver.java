package cn.enn.wise.ssop.service.order.handler.mq.receiver;

import cn.enn.wise.ssop.service.order.config.status.OrderStatusEnum;
import cn.enn.wise.ssop.service.order.model.Orders;
import cn.enn.wise.ssop.service.order.service.OrderService;
import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;


public class TicketMqReceiver {


    @Autowired
    OrderService orderService;

    @RabbitListener(queues = {"${queueconfig.prefix}chupiaosuccess"})
    public void updateChuPiaoSuccess(Message message , Channel channel) throws IOException {
        String chupiaoStr = new String(message.getBody());
        Orders orders = JSONObject.parseObject(chupiaoStr, Orders.class);
        //orderService.changeOrderStatus(orders, OrderStatusEnum.TUIPIAO_CHECK_SUCCESS);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
    }

    /**
     * 审核通过-财务拨款成功
     */
    @RabbitListener(queues = {"${queueconfig.prefix}tuipiaoCheckPassAndWithdrawSuccess"})
    public void tuipiaoCheckPassAndWithdrawSuccess(Long orderId){
        orderService.updateOrderStatus(orderId, OrderStatusEnum.TICKET_CANCEL,null,
                null,null,null,null,null,
                null,null,null,"审核通过-财务拨款成功",false);
    }

    /**
     * 审核通过-财务拨款失败
     */
    @RabbitListener(queues = {"${queueconfig.prefix}tuipiaoCheckPassAndWithdrawFailure"})
    public void tuipiaoCheckPassAndWithdrawFailure(Long orderId){
        orderService.updateOrderStatus(orderId, OrderStatusEnum.TICKET_CANCEL,null,
                null,null,null,null,null,
                null,null,null,"审核通过-财务拨款失败",false);
    }
}
