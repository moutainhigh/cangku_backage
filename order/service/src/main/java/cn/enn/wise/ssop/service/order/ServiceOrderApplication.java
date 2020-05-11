package cn.enn.wise.ssop.service.order;

import cn.enn.wise.ssop.service.order.config.FeignConfiguration;
import cn.enn.wise.ssop.service.order.handler.OrderWrapper;
import cn.enn.wise.ssop.service.order.utils.IdGenerator;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 订单应用启动类
 * @author mrwhite
 * @date 2019-12-11
 */
@EnableSwagger2
@EnableDiscoveryClient
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class}
        ,scanBasePackages = {"cn.enn.wise.ssop.service.order","cn.enn.wise.uncs.base","com.gitee.sunchenbin.mybatis.actable.manager.*"})
@EnableFeignClients(basePackages = "cn.enn.wise.ssop.api",defaultConfiguration = {FeignConfiguration.class})
@MapperScan(basePackages = {"cn.enn.wise.ssop.service.order.mapper","com.gitee.sunchenbin.mybatis.actable.dao.*"})
public class ServiceOrderApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(ServiceOrderApplication.class, args);
    }

}
