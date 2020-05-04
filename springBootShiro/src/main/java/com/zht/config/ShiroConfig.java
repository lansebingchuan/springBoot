package com.zht.config;

import cn.hutool.crypto.digest.BCrypt;
import com.zht.shiroEntity.realm.AdminRealm;
import com.zht.shiroEntity.realm.UserRealm;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 安全配置
 * </p>
 *
 * @author zht
 * @since 2020-04-1
 */
@Configuration
public class ShiroConfig {

    /**
     * 密码匹配器
     */
    @Bean
    public CredentialsMatcher credentialsMatcher() {
        return (authenticationToken, authenticationInfo) -> {
            UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
            //明文，用户输入密码
            String plaintext = new String(token.getPassword());
            //密文,数据库里面的密码
            String hashed = authenticationInfo.getCredentials().toString();
            return BCrypt.checkpw(plaintext, hashed);
        };
    }

    /**
     * 安全过滤器
     */
    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilterFactoryBean() {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager());
        shiroFilterFactoryBean.setUnauthorizedUrl("/unauthorized.html");
        shiroFilterFactoryBean.setLoginUrl("/login.html");
        //配置拦截规则
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        //不需要认证即可访问的url
        filterChainDefinitionMap.put("/", "anon");
        filterChainDefinitionMap.put("/login.html", "anon");
        filterChainDefinitionMap.put("/alogin.html", "anon");
        filterChainDefinitionMap.put("/unauthorized.html", "anon");
        filterChainDefinitionMap.put("/login", "anon");
        filterChainDefinitionMap.put("/alogin", "anon");
        filterChainDefinitionMap.put("/logout", "anon");
        filterChainDefinitionMap.put("/alogout", "anon");
        //静态资源文件
        filterChainDefinitionMap.put("/css/**", "anon");
        filterChainDefinitionMap.put("/font-awesome/**", "anon");
        filterChainDefinitionMap.put("/images/**", "anon");
        filterChainDefinitionMap.put("/js/**", "anon");
        filterChainDefinitionMap.put("/layuiadmin/**", "anon");
        filterChainDefinitionMap.put("/pdf-viewer/**", "anon");
        filterChainDefinitionMap.put("/vendors/**", "anon");

        filterChainDefinitionMap.put("/**", "authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        return shiroFilterFactoryBean;
    }

    /**
     * 安全管理
     */
    @Bean
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //多种方式认证
        securityManager.setAuthenticator(modularRealmAuthenticator());
        return securityManager;
    }

    /**
     * 多方式认证
     */
    @Bean
    public ModularRealmAuthenticator modularRealmAuthenticator() {
        ModularRealmAuthenticator modularRealmAuthenticator = new ModularRealmAuthenticator();
        modularRealmAuthenticator.setAuthenticationStrategy(new AtLeastOneSuccessfulStrategy());
        List<Realm> realms = new ArrayList<>();
        realms.add(adminRealm());
        realms.add(userRealm());
        modularRealmAuthenticator.setRealms(realms);
        return modularRealmAuthenticator;
    }

    /**
     * 超级管理员认证
     */
    @Bean
    public AdminRealm adminRealm() {
        AdminRealm adminRealm = new AdminRealm();
        adminRealm.setCredentialsMatcher(credentialsMatcher());
        return adminRealm;
    }

    /**
     * 租户用户认证
     */
    @Bean
    public UserRealm userRealm() {
        UserRealm userRealm = new UserRealm();
        userRealm.setCredentialsMatcher(credentialsMatcher());
        return userRealm;
    }

    /**
     * 开启注解
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager());
        return authorizationAttributeSourceAdvisor;
    }

    /**
     * setUsePrefix(true)用于解决一个奇怪的bug。在引入spring aop的情况下。
     * 在@Controller注解的类的方法中加入@RequiresRole等shiro注解，会导致该方法无法映射请求，导致返回404。
     * 加入这项配置能解决这个bug
     */
    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public static DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
        creator.setUsePrefix(true);
        // 强制使用cglib，防止重复代理和可能引起代理出错的问题
        creator.setProxyTargetClass(true);
        return creator;
    }

    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }
}
