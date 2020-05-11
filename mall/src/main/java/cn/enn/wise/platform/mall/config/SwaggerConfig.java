package cn.enn.wise.platform.mall.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/******************************************
 * @author: haoguodong
 * @createDate: 2019/5/22 17:39
 * @since: JDK 1.8
 * @projectName:IntelliJ IDEA
 * @Description:接口文档配置
 ******************************************/
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("cn.enn.wise.platform.mall.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("涠洲岛小程序后台REST FUL APIS")
                .description("返回值说明:result 成功标识。1.成功2.失败; message 信息提示; value JSON数据")
                .termsOfServiceUrl("")
                .version("1.1")
                .build();
    }
}
