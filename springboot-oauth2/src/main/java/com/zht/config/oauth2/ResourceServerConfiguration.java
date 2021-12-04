package com.zht.config.oauth2;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * 自定义资源服务器配置
 * <p>
 * Created by xw on 2017/3/19.
 * 2017-03-19 8:09
 */
@EnableResourceServer
@Configuration
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    /**
     * 控制 oauth2.0 拦截配置的信息
     *
     * @param http
     * @throws Exception
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        /**
         * antMatcher("/api/**") ： oauth2.0 只对匹配这个路径下面的 路径 进行认证
         * antMatcher("/**") ： oauth2.0 对所有 路径 进行认证
         * antMatchers("/api/v1/**").permitAll() ： oauth2.0 对路径 /api/v1/** 下面的 所有进行放行
         */
        http.antMatcher("/api/**").authorizeRequests() //请求都需要 oauth2.0认证检测
                .antMatchers("/api/v1/**").permitAll() // 不需要认证的路径
                .anyRequest().authenticated();  // 所有请求都需要 oauth2.0认证检测
    }
}