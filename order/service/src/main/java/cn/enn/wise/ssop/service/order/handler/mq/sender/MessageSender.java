package cn.enn.wise.ssop.service.order.handler.mq.sender;

import cn.enn.wise.ssop.service.order.config.OrderMqConfig;
import cn.enn.wise.ssop.service.order.model.MessageBody;
import com.alibaba.fastjson.JSON;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 订单
 * @author 安辉
 */
@Component
public class MessageSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 下单消息
     */
    public void orderSuccess(MessageBody params){
        this.rabbitTemplate.convertAndSend(OrderMqConfig.ORDER_EXCHANGE, "", JSON.toJSONString(params));
    }

    /**
     * 支付消息
     */
    public void paySuccess(MessageBody params){
        this.rabbitTemplate.convertAndSend(OrderMqConfig.ORDER_EXCHANGE, "",JSON.toJSONString(params));
    }

    /**
     * 核销消息
     */
    public void checkInSuccess(MessageBody params){
        this.rabbitTemplate.convertAndSend(OrderMqConfig.ORDER_EXCHANGE, "",JSON.toJSONString(params));
    }

    /**
     * 退款消息
     */
    public void refundSuccess(MessageBody params){
        this.rabbitTemplate.convertAndSend(OrderMqConfig.ORDER_EXCHANGE, "",JSON.toJSONString(params));
    }
}

