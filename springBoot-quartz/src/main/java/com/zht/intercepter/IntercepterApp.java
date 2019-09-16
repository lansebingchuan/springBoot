package com.zht.intercepter;

import com.zht.listener.SessionListener;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author zht
 * @create 2019-09-05 15:38
 */
@Configuration
public class IntercepterApp implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
                                ///增加拦截器                设置拦截目录
        registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/*");
    }
}
