package cn.enn.wise.ssop.service.cms.config;

import cn.enn.wise.uncs.base.config.MyDataConvert;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Import({WebMvcAutoConfiguration.class})
public class WebMvcConfig implements WebMvcConfigurer {
    @Bean
    public MyDataConvert stringToDateConverter() {
        return new MyDataConvert();
    }
}