package cn.enn.wise.platform.mall.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * rabbitMq
 *
 * @author caiyt
 */
@Configuration
public class OrderMqConfig {

    @Value("${queueconfig.prefix}")
    public String prefix;

    /**
     * 交换机
     */
    public final static String ORDER_EXCHANGE = "order.direct";

    @Bean
    public DirectExchange orderExchange() {


        return new DirectExchange(prefix+ORDER_EXCHANGE);
    }

    /**
     * 订单类队列
     */
    public class OrderQueue {
        /**
         * 订单状态改变
         */
        public final static String ORDER_STATUS_UPDATE = "orderStatusUpdate";
        /**
         * 分销订单状态改变
         */
        public final static String SYNC_DISTRIBUTOR_ORDER_STATUS = "syncDistributorOrderStatus";
        /**
         * 生成分销订单
         */
        public final static String GENERATE_DISTRIBUTOR_ORDERS = "generateDistributorOrders";

        /**
         * 处理订单
         */
        public final static String  PROCESS_ORDER= "processOrder";

        /**
         * 同步最低价格
         */
        public final static String  UPDATE_MIN_GOODS_PRICE= "goodsPriceQueue";


        /**
         * 同步最低价格
         */
        public final static String  CHECK_DISTRIBUTE_ORDER_CAN_PROFIT= "checkDistributeOrderCanProfit";


        /**
         * 同步项目信息
         */
        public final static String  GOODS_PEOJECT_UPDATE_QUEUE= "goodsProjectUpdateQueue";


        /**
         * 同步项目体验人数
         */
        public final static String  PROJECT_YESTERDAY_NUM_QUEUE= "projectYesterdayNumsQueue";


        /**
         * 同步核销策略
         */
        public final static String  CHECK_ORDER_QUEUE= "checkOrderQueue";

        /**
         * 发送退款商品数量
         */
        public final static String REFUND_ORDER_AMOUNT_QUEUE= "refundOrderAmount";



    }

    /**
     * 同步最低价格
     * @return
     */
    @Bean
    public Queue goodsPriceQueue() {
        return new Queue(prefix+OrderQueue.UPDATE_MIN_GOODS_PRICE);
    }

    /**
     * 发送退款商品数量
     * @return
     */
    @Bean
    public Queue refundOrderAmountQueue() {
        return new Queue(prefix+OrderQueue.REFUND_ORDER_AMOUNT_QUEUE);
    }

    @Bean
    public Queue goodsProjectUpdateQueue(){
        return new Queue(prefix+OrderQueue.GOODS_PEOJECT_UPDATE_QUEUE);
    }

    @Bean
    public Queue projectYesterdayNumsQueue(){
        return new Queue(prefix+OrderQueue.PROJECT_YESTERDAY_NUM_QUEUE);
    }
    /**
     * 同步分润状态
     * @return
     */
    @Bean
    public Queue checkDistributeOrderCanProfitQueue() {
        return new Queue(prefix+OrderQueue.CHECK_DISTRIBUTE_ORDER_CAN_PROFIT);
    }

    /**
     * 订单状态改变
     *
     * @return
     */
    @Bean
    public Queue orderStatusUpdateQueue() {
        return new Queue(prefix+OrderQueue.ORDER_STATUS_UPDATE);
    }

    /**
     * 订单状态
     * @return
     */
    @Bean
    public Queue processOrderQueue(){
        return new Queue(prefix+OrderQueue.PROCESS_ORDER);
    }

    /**
     * 分销订单状态改变
     *
     * @return
     */
    @Bean
    public Queue syncDistributorOrderStatusQueue() {
        return new Queue(prefix+OrderQueue.SYNC_DISTRIBUTOR_ORDER_STATUS);
    }

    /**
     * 生成分销订单
     *
     * @return
     */
    @Bean
    public Queue generateDistributorOrdersQueue() {
        return new Queue(prefix+OrderQueue.GENERATE_DISTRIBUTOR_ORDERS);
    }

    /**
     * 核销订单队列：确定核销策略
     *
     * @return
     */
    @Bean
    public Queue checkOrderQueue() {
        return new Queue(prefix+OrderQueue.CHECK_ORDER_QUEUE);
    }




    /**
     * 发送退款商品数量
     *
     * @param refundOrderAmountQueue 退款商品数量
     * @param orderExchange
     * @return
     */
    @Bean
    Binding bindingrefundOrderAmount(Queue refundOrderAmountQueue, DirectExchange orderExchange) {
        return BindingBuilder.bind(refundOrderAmountQueue).to(orderExchange).with(prefix+OrderQueue.REFUND_ORDER_AMOUNT_QUEUE);
    }

    /**
     * 更新最低价格
     *
     * @param checkDistributeOrderCanProfitQueue
     * @param orderExchange
     * @return
     */
    @Bean
    Binding bindingcheckDistributeOrderCanProfitQueue(Queue checkDistributeOrderCanProfitQueue, DirectExchange orderExchange) {
        return BindingBuilder.bind(checkDistributeOrderCanProfitQueue).to(orderExchange).with(prefix+OrderQueue.CHECK_DISTRIBUTE_ORDER_CAN_PROFIT);
    }

    /**
     * 同步项目信息
     * @param goodsProjectUpdateQueue
     * @param orderExchange
     * @return
     */
    @Bean
    Binding bindinggoodsProjectUpdateQueue(Queue goodsProjectUpdateQueue, DirectExchange orderExchange) {

        return BindingBuilder.bind(goodsProjectUpdateQueue).to(orderExchange).with(prefix+OrderQueue.GOODS_PEOJECT_UPDATE_QUEUE);
    }

    /**
     * 同步项目体验人数
     * @param projectYesterdayNumsQueue
     * @param orderExchange
     * @return
     */
    @Bean
    Binding bindingprojectYesterdayNumsQueue(Queue projectYesterdayNumsQueue, DirectExchange orderExchange) {

        return BindingBuilder.bind(projectYesterdayNumsQueue).to(orderExchange).with(prefix+OrderQueue.PROJECT_YESTERDAY_NUM_QUEUE);
    }


    /**
     * 更新最低价格
     *
     * @param goodsPriceQueue
     * @param orderExchange
     * @return
     */
    @Bean
    Binding bindingGoodsPriceQueue(Queue goodsPriceQueue, DirectExchange orderExchange) {
        return BindingBuilder.bind(goodsPriceQueue).to(orderExchange).with(prefix+OrderQueue.UPDATE_MIN_GOODS_PRICE);
    }


    /**
     * 订单状态改变
     *
     * @param orderStatusUpdateQueue
     * @param orderExchange
     * @return
     */
    @Bean
    Binding bindingOrderStatusUpdate(Queue orderStatusUpdateQueue, DirectExchange orderExchange) {
        return BindingBuilder.bind(orderStatusUpdateQueue).to(orderExchange).with(prefix+OrderQueue.ORDER_STATUS_UPDATE);
    }

    /**
     * 分销订单状态改变
     *
     * @param syncDistributorOrderStatusQueue
     * @param orderExchange
     * @return
     */
    @Bean
    Binding bindingSyncDistributorOrderStatus(Queue syncDistributorOrderStatusQueue, DirectExchange orderExchange) {
        return BindingBuilder.bind(syncDistributorOrderStatusQueue).to(orderExchange).with(prefix+OrderQueue.SYNC_DISTRIBUTOR_ORDER_STATUS);
    }

    /**
     * 生成分销订单
     *
     * @param generateDistributorOrdersQueue
     * @param orderExchange
     * @return
     */
    @Bean
    Binding bindingGenerateDistributorOrders(Queue generateDistributorOrdersQueue, DirectExchange orderExchange) {
        return BindingBuilder.bind(generateDistributorOrdersQueue).to(orderExchange).with(prefix+OrderQueue.GENERATE_DISTRIBUTOR_ORDERS);
    }

    /**
     * 生成分销订单
     *
     * @param processOrderQueue
     * @param orderExchange
     * @return
     */
    @Bean
    Binding bindingProcessOrder(Queue processOrderQueue, DirectExchange orderExchange) {
        return BindingBuilder.bind(processOrderQueue).to(orderExchange).with(prefix+OrderQueue.PROCESS_ORDER);
    }

    /**
     * 生成分销订单
     *
     * @param checkOrderQueue
     * @param orderExchange
     * @return
     */
    @Bean
    Binding bindingCheckOrder(Queue checkOrderQueue, DirectExchange orderExchange) {
        return BindingBuilder.bind(checkOrderQueue).to(orderExchange).with(prefix+OrderQueue.CHECK_ORDER_QUEUE);
    }



}
