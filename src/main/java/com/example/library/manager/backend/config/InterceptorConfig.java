package com.example.library.manager.backend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * @author jluzhuwanyuan@163.com
 * @date 2023/7/28
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Resource private TokenInterceptor tokenInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenInterceptor)
                // 对图书相关的请求进行拦截
                .addPathPatterns("/book/**");
    }
}
