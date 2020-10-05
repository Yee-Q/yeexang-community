package top.yeexang.community.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 * Swagger 配置类
 *
 * @author yeeq
 * @date 2020/10/1
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .pathMapping("/")
                .select()
                // 扫描接口的包
                .apis(RequestHandlerSelectors.basePackage("top.yeexang.community.controller"))
                .paths(PathSelectors.any())
                .build().apiInfo(new ApiInfoBuilder()
                        .title("yeexang")
                        .description("详细信息...")
                        .version("9.0")
                        .contact(new Contact("yeeq", "www.yeexang.top", "1051195623@qq.com"))
                        .license("License")
                        .licenseUrl("www.yeexang.top")
                        .build());
    }
}
