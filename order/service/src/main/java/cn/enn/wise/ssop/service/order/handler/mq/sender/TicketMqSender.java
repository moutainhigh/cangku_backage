package cn.enn.wise.ssop.service.order.handler.mq.sender;

import cn.enn.wise.ssop.service.order.config.OrderMqConfig;
import cn.enn.wise.ssop.service.order.model.MessageBody;
import cn.enn.wise.ssop.service.order.model.OrderRefundRecord;
import com.alibaba.fastjson.JSON;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

@Component
public class TicketMqSender {

    @Value("${queueconfig.prefix}")
    public String prefix;

    private static final String TAG = "order";

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 发送退款消息
     * @param orderRefundRecord
     */

    public void sendRefundMessage(OrderRefundRecord orderRefundRecord){
        MessageBody messageBody = new MessageBody();
        messageBody.setTag(TAG);
        messageBody.setCompanyId("111");
        messageBody.setData(orderRefundRecord.toString());
        this.rabbitTemplate.convertAndSend(OrderMqConfig.ORDER_EXCHANGE, "","MessageBody");
    }


    /**
     * 发订单退票失败消息
     * @param orderRefundRecord
     */

    public void sendRefundFailureMessage(OrderRefundRecord orderRefundRecord){
        rabbitTemplate.convertAndSend(
                "ssop.service.orders",prefix+"refundQueue", JSON.toJSON(orderRefundRecord));
    }

    /**
     * 发订单退票成功消息
     * @param orderRefundRecord
     */

    public void sendRefundSuccessMessage(OrderRefundRecord orderRefundRecord){
        rabbitTemplate.convertAndSend(prefix+"refund.direct",prefix+"refundQueue", JSON.toJSON(orderRefundRecord));
    }

}
