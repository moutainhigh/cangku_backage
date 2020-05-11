package cn.enn.wise.platform.mall.util.redlock;

import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 获取redisson的链接类
 *
 * @author baijie
 * @date 2019-09-12
 */
@Component
public class RedissonConnector {

    RedissonClient redisson;
    @Autowired
    RedisProperties properties;

    @PostConstruct
    public void init(){

        Config config = new Config();
        Long timeout =   properties.getTimeout().toMinutes()*60;

        SingleServerConfig serverConfig = config.useSingleServer()
                .setAddress(properties.getHost()+":"+properties.getPort())
                .setTimeout(timeout.intValue())
                .setConnectionPoolSize(properties.getLettuce().getPool().getMaxIdle())
                .setConnectionMinimumIdleSize(properties.getLettuce().getPool().getMinIdle());

        if(StringUtils.isNotBlank(properties.getPassword())) {
            serverConfig.setPassword(properties.getPassword());
        }

        redisson = Redisson.create(config);
    }

    public RedissonClient getClient(){
        return redisson;
    }


}
