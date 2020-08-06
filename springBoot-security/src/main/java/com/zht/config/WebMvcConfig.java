package com.zht.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * <p>
 *  web配置
 * </p>
 *
 * @author ZHT
 * @since 2020/6/14
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    public static String[] STATIC_PATH_PATTERNS = new String[]{
            "/css/**",  "/img/**", "/js/**", "/plugins/**",
            "/layuiadmin/**"
    };

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // 配置首页为的登录也
        registry.addRedirectViewController("/", "/login.html");
        registry.addViewController("login.html").setViewName("login");
        registry.addViewController("index.html").setViewName("index");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }
}
