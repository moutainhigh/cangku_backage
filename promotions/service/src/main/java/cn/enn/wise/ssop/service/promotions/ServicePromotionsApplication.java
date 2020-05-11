package cn.enn.wise.ssop.service.promotions;

import cn.enn.wise.ssop.service.promotions.config.FeignConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 服务启动入口
 * @author shizhai
 * @date 209-12-04
 */
@EnableSwagger2
@EnableDiscoveryClient
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class}
,scanBasePackages = {"cn.enn.wise.ssop.service.promotions","cn.enn.wise.ssop.api.demo","cn.enn.wise.uncs.base","com.gitee.sunchenbin.mybatis.actable.manager.*"})
@EnableFeignClients(basePackages = "cn.enn.wise.ssop.api",defaultConfiguration = {FeignConfiguration.class})
@MapperScan(basePackages = {"cn.enn.wise.ssop.service.promotions.mapper","com.gitee.sunchenbin.mybatis.actable.dao.*"})
public class ServicePromotionsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServicePromotionsApplication.class, args);
    }

}
