package cn.enn.wise.platform.mall;


import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication(exclude = DruidDataSourceAutoConfigure.class)
@ComponentScan( basePackages= {"cn.enn.wise.platform.mall","com.gitee.sunchenbin.mybatis.actable.manager.*"})
@MapperScan(basePackages = {"cn.enn.wise.platform.mall.mapper","com.gitee.sunchenbin.mybatis.actable.dao.*"})
@ServletComponentScan
//@EnableScheduling
public class WisePlatformMallApplication {


    @Value("${queueconfig.prefix}")
    private String prefix;

    public static void main(String[] args) {

        //#region 初始化队列名称
        SpringApplication.run(WisePlatformMallApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplateBuilder().build();
    }


}
