package com.auth.auth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * <p> </p>
 *
 * @author: ZHT
 * @create: 2021-07-09 18:03
 **/
@Configuration
public class AuthWebConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index/index");
        registry.addViewController("/login.html").setViewName("login/index");
        registry.addViewController("/reg.html").setViewName("reg/index");
        registry.addViewController("/index.html").setViewName("index/index");
    }
}
