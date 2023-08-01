package com.example.library.manager.backend.config;

import com.example.library.manager.backend.common.constant.RequestEnumConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jluzhuwanyuan@163.com
 * @date 2023/7/28
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() {
        List<Parameter> parameters = getParameters();
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(
                        new ApiInfoBuilder()
                                .title("图书管理系统")
                                .description("接口文档")
                                .version("1.0")
                                .build())
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(parameters)
                .enable(true);
    }

    /**
     * swagger加请求头参数
     *
     * @return
     */
    private List<Parameter> getParameters() {
        List<Parameter> parameters = new ArrayList<>();
        Parameter token =
                new ParameterBuilder()
                        .name(RequestEnumConstants.RequestHeader.OLD_USER_TOKEN.getName())
                        .description("登陆令牌")
                        .modelRef(new ModelRef("string"))
                        .parameterType("header")
                        .build();
        parameters.add(token);
        return parameters;
    }
}
