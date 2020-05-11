package cn.enn.wise.ssop.service.order.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * rabbitMq
 *
 * @author 安辉
 */
@Configuration
public class OrderMqConfig {

    /**
     * 交换机
     */
    public final static String ORDER_EXCHANGE = "ssop.service.orders";
    @Bean
    public FanoutExchange orderExchange() {
        return new FanoutExchange(ORDER_EXCHANGE);
    }

    /**
     * 订单类队列
     */
    public class OrderQueue {
        // 下单成功
        public final static String ORDER_SUCCESS_QUEUE = "order_success";

        // 支付成功
        public final static String PAY_SUCCESS_QUEUE = "pay_success";

        // 核销成功
        public final static String CHECK_IN_SUCCESS_QUEUE = "check_in_success";

        // 退款成功
        public final static String REFUND_QUEUE = "refund_success";
    }

    /**
     * 初始化下单成功队列
     *
     * @return
     */
    @Bean
    public Queue orderSuccessQueue() {
        return new Queue(OrderQueue.ORDER_SUCCESS_QUEUE);
    }

    /**
     * 支付成功队列
     *
     * @return
     */
    @Bean
    public Queue paySuccessQueue() {
        return new Queue(OrderQueue.PAY_SUCCESS_QUEUE);
    }

    /**
     * 核销队列
     *
     * @return
     */
    @Bean
    public Queue checkInQueue() {
        return new Queue(OrderQueue.CHECK_IN_SUCCESS_QUEUE);
    }

    /**
     * 核销队列
     * @return
     */
    @Bean
    public Queue refundQueue() {
        return new Queue(OrderQueue.REFUND_QUEUE);
    }


    /**
     * 绑定下单成功队列
     * @param orderSuccessQueue
     * @param orderExchange
     * @return
     */
    @Bean
    Binding bindingOrder(Queue orderSuccessQueue, FanoutExchange orderExchange) {
        return BindingBuilder.bind(orderSuccessQueue).to(orderExchange);
    }

    /**
     * 绑定支付成功队列
     * @param paySuccessQueue
     * @param orderExchange
     * @return
     */
    @Bean
    Binding bindingPaySuccess(Queue paySuccessQueue, FanoutExchange orderExchange) {
        return BindingBuilder.bind(paySuccessQueue).to(orderExchange);
    }

    /**
     * 绑定支付成功队列
     * @param checkInQueue
     * @param orderExchange
     * @return
     */
    @Bean
    Binding checkInQueue(Queue checkInQueue, FanoutExchange orderExchange) {
        return BindingBuilder.bind(checkInQueue).to(orderExchange);
    }

    /**
     * 退款成功
     * @param refundQueue
     * @param orderExchange
     * @return
     */
    @Bean
    Binding refundQueue(Queue refundQueue, FanoutExchange orderExchange) {
        return BindingBuilder.bind(refundQueue).to(orderExchange);
    }
}
