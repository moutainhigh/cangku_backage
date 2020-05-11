package cn.enn.wise.ssop.service.promotions.config;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author shiz
 *
 */
@Configuration
@EnableSwaggerBootstrapUI
public class SwaggerConfig extends WebMvcConfigurerAdapter {

//    @Value("${project.swagger.host}")
//    String swaggerHost;

    @Bean
    public Docket createRestApi() {
        List<Parameter> pars = new ArrayList<>();
        ParameterBuilder companyId = new ParameterBuilder();
        companyId.name("company_id").description("租户id(前端不需要关注，只用于页面测试)").modelRef(new ModelRef("string")).parameterType("header").required(false).defaultValue("1").build();
        pars.add(companyId.build());



        Docket build = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("cn.enn.wise.ssop.service.promotions.controller"))
                .paths(PathSelectors.ant("/**"))
                .build().directModelSubstitute(Timestamp.class, Date.class).globalOperationParameters(pars)
                .groupName("api");

//        if(swaggerHost!=null||!swaggerHost.trim().isEmpty()) build.host(swaggerHost);
        return build;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("内容服务")
                .description("内容服务接口")
                .termsOfServiceUrl("www.enn.cn")
                .contact("enn")
                .version("1.0")
                .build();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("doc.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        super.addResourceHandlers(registry);
    }

}
