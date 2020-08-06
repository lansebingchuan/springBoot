package com.zht.config;

import com.zht.auth.UserAuthService;
import com.zht.auth.UserAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * <p>
 *  Security安全框架配置
 * </p>
 *
 * @author ZHT
 * @since 2020/6/14
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // 不需要身份验证即可访问的URL
                .antMatchers("/","/login.html").permitAll()
                // 尚未匹配的任何URL都要求进行身份验证
                .anyRequest().authenticated()
                .and()
                // 通过表单登录
                .formLogin()
                //登录页面
                .loginPage("/login.html")
                //自定义登录表单
                .loginProcessingUrl("/login")
                .failureUrl("/login.html?error=1")
                .usernameParameter("username")
                .passwordParameter("password")
                .defaultSuccessUrl("/index.html", true)
                .permitAll();
        //下面是默认产生一个都会产生一个hiden标签 里面有安全相关的验证 防止请求伪造 这边我们暂时不需要 可禁用掉
                /*.and()
                .cors()
                .and()
                .headers()
                .frameOptions().sameOrigin();*/
        http.csrf().disable();
        // 添加过滤器,在认证之前执行的--
        //注意：添加了过滤器之后，前面设置的【“defaultSuccessUrl”、“failureUrl”】地址将会失效，需要在过滤器当中设置成功或者失败地址
        //http.addFilterBefore(userAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // 过滤静态资源文件
        web.ignoring().antMatchers(WebMvcConfig.STATIC_PATH_PATTERNS);
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 设置登录逻辑处理service以及password编码
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());

    }

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        return new UserAuthService();
    }

    @Bean
    public UserAuthenticationFilter userAuthenticationFilter() {
        UserAuthenticationFilter userAuthenticationFilter = new UserAuthenticationFilter();
        userAuthenticationFilter.setAuthenticationManager(this.authenticationManager);
        return userAuthenticationFilter;
    }

    public static void main(String[] args) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encode = bCryptPasswordEncoder.encode("1");
        System.out.println(encode);
    }
}
