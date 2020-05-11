package cn.enn.wise.platform.mall.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * rabbitMq
 *
 * @author caiyt
 */
@Configuration
public class OperationMqConfig {

    @Value("${queueconfig.prefix}")
    public String prefix;

    /**
     * 交换机
     */
    public final static String OPERATION_EXCHANGE = "amq.fanout";

    @Bean
    public FanoutExchange OperationExchange() {
        return new FanoutExchange(prefix+OPERATION_EXCHANGE);
    }
}