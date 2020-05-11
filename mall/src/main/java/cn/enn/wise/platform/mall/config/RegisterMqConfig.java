package cn.enn.wise.platform.mall.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * rabbitMq
 *
 * @author caiyt
 */
@Configuration
public class RegisterMqConfig {

    @Value("${queueconfig.prefix}")
    private String prefix;
    /**
     * 交换机
     */
    public final static String REGISTER_EXCHANGE = "register.direct";

    /**
     * 注册类队列
     */
    public class RegisterQueue {
        /**
         * 注册成为分销商队列
         */
        public final static String REGISTER_DISTRIBUTOR = "registerDistributor";
        /**
         * 处理用户重复提交审核信息队列
         */
        public final static String VERIFY_DISTRIBUTOR_APPLY_INFO = "verifyDistributorApplyInfo";
        /**
         * 更新分销商信息队列
         */
        public final static String UPDATE_DISTRIBUTOR_INFO = "updateDistributorInfo";
        /**
         * 同步分销商状态
         */
        public final static String SYNC_DISTRIBUTOR_STATUS = "syncDistributorStatus";
    }

    @Bean
    public DirectExchange registerExchange() {
        return new DirectExchange(prefix+REGISTER_EXCHANGE);
    }

    /**
     * 注册成为分销商队列
     *
     * @return
     */
    @Bean
    public Queue registerDistributorQueue() {
        return new Queue(prefix+RegisterQueue.REGISTER_DISTRIBUTOR);
    }

    /**
     * 处理用户重复提交审核信息队列
     *
     * @return
     */
    @Bean
    public Queue verifyDistributorApplyInfoQueue() {
        return new Queue(prefix+RegisterQueue.VERIFY_DISTRIBUTOR_APPLY_INFO);
    }

    /**
     * 更新分销商信息队列
     *
     * @return
     */
    @Bean
    public Queue updateDistributorInfoQueue() {
        return new Queue(prefix+RegisterQueue.UPDATE_DISTRIBUTOR_INFO);
    }

    /**
     * 同步分销商状态
     *
     * @return
     */
    @Bean
    public Queue syncDistributorStatusQueue() {
        return new Queue(prefix+RegisterQueue.SYNC_DISTRIBUTOR_STATUS);
    }

    /**
     * 注册成为分销商
     *
     * @param registerDistributorQueue
     * @param registerExchange
     * @return
     */
    @Bean
    Binding bindingRegisterDistributor(Queue registerDistributorQueue, DirectExchange registerExchange) {
        return BindingBuilder.bind(registerDistributorQueue).to(registerExchange).with(prefix+RegisterQueue.REGISTER_DISTRIBUTOR);
    }

    /**
     * 处理用户重复提交审核信息
     *
     * @param verifyDistributorApplyInfoQueue
     * @param registerExchange
     * @return
     */
    @Bean
    Binding bindingVerifyDistributorApplyInfo(Queue verifyDistributorApplyInfoQueue, DirectExchange registerExchange) {
        return BindingBuilder.bind(verifyDistributorApplyInfoQueue).to(registerExchange).with(prefix+RegisterQueue.VERIFY_DISTRIBUTOR_APPLY_INFO);
    }

    /**
     * 更新分销商信息
     *
     * @param updateDistributorInfoQueue
     * @param registerExchange
     * @return
     */
    @Bean
    Binding bindingUpdateDistributorInfo(Queue updateDistributorInfoQueue, DirectExchange registerExchange) {
        return BindingBuilder.bind(updateDistributorInfoQueue).to(registerExchange).with(prefix+RegisterQueue.UPDATE_DISTRIBUTOR_INFO);
    }

    /**
     * 同步分销商状态
     * @param syncDistributorStatusQueue
     * @param registerExchange
     * @return
     */
    @Bean
    Binding bindingSyncDistributorStatus(Queue syncDistributorStatusQueue, DirectExchange registerExchange) {
        return BindingBuilder.bind(syncDistributorStatusQueue).to(registerExchange).with(prefix+RegisterQueue.SYNC_DISTRIBUTOR_STATUS);
    }

    /**
     *
     * @param connectionFactory
     * @return
     */
    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory){
        return new RabbitAdmin(connectionFactory);
    }


    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate=new RabbitTemplate(connectionFactory);
        //数据转换为json存入消息队列
//        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        return rabbitTemplate;
    }

}