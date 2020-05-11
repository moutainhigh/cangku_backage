package cn.enn.wise.platform.mall.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * rabbitMq
 *
 * @author caiyt
 */
@Configuration
public class SmsMqConfig {
    /**
     * 交换机
     */
    public final static String SMS_EXCHANGE = "sms.direct";

    /**
     * 交换机V2
     */
    public final static String SMS_EXCHANGE_V2 = "sms.direct.v2";
    /**
     * 交换机V3
     */
    public final static String SMS_EXCHANGE_V3 = "sms.direct.v3";

    @Bean
    public DirectExchange smsExchange() {
        return new DirectExchange(SMS_EXCHANGE);
    }

    @Bean
    public DirectExchange smsExchangeV2() {
        return new DirectExchange(SMS_EXCHANGE_V2);
    }
    @Bean
    public DirectExchange smsExchangeV3() {
        return new DirectExchange(SMS_EXCHANGE_V3);
    }

    /**
     * 短信队列
     */
    public final static String SMS_QUEUE = "sendSms";

    /**
     * 短信队列V2
     */
    public final static String SMS_QUEUE_V2 = "sendSmsv2";

    /**
     * 短信队列V3
     */
    public final static String SMS_QUEUE_V3 = "sendSmsv3";

    /**
     * 发送短信V2
     *
     * @return
     */
    @Bean
    public Queue sendSmsQueue() {
        return new Queue(SMS_QUEUE);
    }

    /**
     * 发送短信V2
     *
     * @return
     */
    @Bean
    public Queue sendSmsQueueV2() {
        return new Queue(SMS_QUEUE_V2);
    }

    /**
     * 发送短信V3
     *
     * @return
     */
    @Bean
    public Queue sendSmsQueueV3() {
        return new Queue(SMS_QUEUE_V3);
    }

    /**
     * 发送短信
     *
     * @param sendSmsQueue
     * @param smsExchange
     * @return
     */
    @Bean
    Binding bindingSendSms(Queue sendSmsQueue, DirectExchange smsExchange) {
        return BindingBuilder.bind(sendSmsQueue).to(smsExchange).with(SMS_QUEUE);
    }

    /**
     * 发送短信
     *
     * @param sendSmsQueueV2
     * @param smsExchangeV2
     * @return
     */
    @Bean
    Binding bindingSendSmsV2(Queue sendSmsQueueV2, DirectExchange smsExchangeV2) {
        return BindingBuilder.bind(sendSmsQueueV2).to(smsExchangeV2).with(SMS_QUEUE_V2);
    }

    /**
     * 发送短信
     *
     * @param sendSmsQueueV3
     * @param smsExchangeV3
     * @return
     */
    @Bean
    Binding bindingSendSmsV3(Queue sendSmsQueueV3, DirectExchange smsExchangeV3) {
        return BindingBuilder.bind(sendSmsQueueV3).to(smsExchangeV3).with(SMS_QUEUE_V3);
    }
}
