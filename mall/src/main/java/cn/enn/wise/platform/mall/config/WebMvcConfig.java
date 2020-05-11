package cn.enn.wise.platform.mall.config;


import cn.enn.wise.platform.mall.config.logging.LoggingFilter;
import cn.enn.wise.platform.mall.util.AuthHandlerInterceptor;
import cn.enn.wise.platform.mall.util.OpenIdAuthHandlerInterceptor;
import cn.enn.wise.platform.mall.util.SignHandlerInterceptor;
import cn.enn.wise.platform.mall.util.StaffAuthHandlerInterceptor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;


@Configuration
//@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {

    @Bean
    public AuthHandlerInterceptor authHandlerInterceptor() {
        return new AuthHandlerInterceptor();
    }

    @Bean
    public StaffAuthHandlerInterceptor staffAuthHandlerInterceptor() {
        return new StaffAuthHandlerInterceptor();
    }

    @Bean
    public OpenIdAuthHandlerInterceptor openIdAuthHandlerInterceptor() {
        return new OpenIdAuthHandlerInterceptor();
    }

    @Bean
    public SignHandlerInterceptor signAuthHandlerInterceptor() {
        return new SignHandlerInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authHandlerInterceptor());
        registry.addInterceptor(staffAuthHandlerInterceptor());
        registry.addInterceptor(openIdAuthHandlerInterceptor());
        registry.addInterceptor(signAuthHandlerInterceptor());
    }

    @Bean
    public Filter loggingFilter() {
        return new LoggingFilter();
    }

    @Bean
    public FilterRegistrationBean someFilterRegistration() {

        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(loggingFilter());
        registration.addUrlPatterns("/order");
        registration.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/");
        registration.setName("loggingFilter");
        return registration;
    }


}
