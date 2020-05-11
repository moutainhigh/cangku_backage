/*
package cn.enn.wise.ssop.service.order.handler.mq.receiver;

import cn.enn.wise.ssop.service.order.model.MessageBody;
import cn.enn.wise.ssop.service.order.thirdparty.IThirdPartyOrder;
import cn.enn.wise.ssop.service.order.thirdparty.ThirdPartyOrder;
import cn.enn.wise.ssop.service.order.thirdparty.ThirdPartyOrderFactory;
import cn.enn.wise.uncs.base.pojo.response.R;
import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

*/
/**
 * 接收消息
 *
 * @author anhui257@163.com
 *//*

@Component
public class MessageReceiver {

    */
/**
     * 消费下单成功消息
     *
     * @param message
     * @param channel
     *//*

    @RabbitListener(queues = "order_success")
    public void processOrderSuccess(Message message, Channel channel) {
        // 下单成功,调用微信下单

    }


    */
/**
     * 消费支付成功消息
     *
     * @param message
     * @param channel
     *//*

    @RabbitListener(queues = "pay_success")
    public void paySuccess(Message message, Channel channel) throws Exception{
        String body = new String(message.getBody());
        MessageBody messageBody = JSON.parseObject(body, MessageBody.class);
        ThirdPartyOrder order = JSON.parseObject(messageBody.getData(), ThirdPartyOrder.class);
        IThirdPartyOrder thirdPartyOrder = ThirdPartyOrderFactory.getFactory(5L);

        R result = thirdPartyOrder.order(order);
        // 判断参数 手工确认 消息消费成功
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
    }

    */
/**
     * 消费核销消息
     * @param message
     * @param channel
     *//*

    @RabbitListener(queues = "check_in_success")
    public void checkInSuccess(Message message, Channel channel) {
        //
    }

    */
/**
     * 消费退款消息
     * @param message
     * @param channel
     *//*

    @RabbitListener(queues = "refund_success")
    public void refund(Message message, Channel channel) {
        //
    }
}
*/
