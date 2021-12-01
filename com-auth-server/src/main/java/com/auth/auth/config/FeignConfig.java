package com.auth.auth.config;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * <p> Feign远程调用配置类 </p>
 *
 * @author: ZHT
 * @create: 2021-07-16 16:59
 **/
@Configuration
public class FeignConfig {

    /**
     * 配置feign远程请求的拦截器
     *  
     * @return 请求的拦截器
     */
    @Bean
    public RequestInterceptor requestInterceptor(){
        return (requestTemplate) -> {
            // 获取当前请求参数
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            //获取到请求体
            HttpServletRequest request = requestAttributes.getRequest();
            // 获取到浏览器提交的cookie
            String cookie = request.getHeader("Cookie");
            // feign远程调用设置 cookie
            requestTemplate.header("Cookie", cookie);
        };
    }
}
