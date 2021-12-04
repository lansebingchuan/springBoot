package com.zht.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    /**
     * 自定义人员详细服务
     *
     * @return
     */
    @Override
    protected UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }

    /**
     * 认证管理器，提供给 oauth2
     *
     * @return
     * @throws Exception
     */
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**").antMatchers("/webjars/**", "/swagger-resources/**", "/swagger**", "/v2/api-docs");
    }

    /**
     * spring-security安全拦截配置
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /**
         * antMatcher("/**").authorizeRequests(): 所有请求都需要登录
         * antMatchers("/login").permitAll()： 过滤掉的url
         * anyRequest().authenticated()： 任何请求都需要登录
         * and().formLogin()： 支持表单登录
         * and().httpBasic()： 支持 httpBasic 登录
         */
        http.antMatcher("/**").authorizeRequests()
                .antMatchers("/login").permitAll()
                .anyRequest().authenticated()
                .and().formLogin()
                .and().httpBasic();
    }

    /**
     * 配置用户登陆解析器与密码解析器
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
        super.configure(auth);
    }

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println(encoder.encode("123456"));
    }
}
