package com.zht.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.BasicAuth;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("API")
                .apiInfo(apiInfo())
                .securitySchemes(securitySchemes())
                .select()
                // swagger扫描的包
                .apis(RequestHandlerSelectors.basePackage("com.zht"))
                // 只有标记了@ApiOperation的方法才会暴露出给swagger
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * API基本信息
     *
     * @return
     */
    public ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("接口")
                .description("接口说明")
                .version("1.0")
                .build();
    }

    /**
     * api认证方式
     *
     * @return
     */
    private List<? extends SecurityScheme> securitySchemes() {
        BasicAuth authorization = new BasicAuth("BasicAuth");
        ApiKey apiKey = new ApiKey("Bearer", "Authorization", "header");
        return Arrays.asList(authorization, apiKey);
    }

}
