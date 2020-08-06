package com.zht.auth;

import cn.hutool.core.util.ObjectUtil;
import com.zht.entity.User;
import com.zht.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

/**
 * <p>
 *
 * </p>
 *
 * @author ZHT
 * @since 2020/6/14
 */
@Component
public class UserAuthService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Autowired
    private UserAuthenticationFilter userAuthenticationFilter;

    /**
     * 认证
     * @param userName 登录名
     * @return 登录认证对象
     * @throws UsernameNotFoundException 用户不存在异常
     */
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        if (userName == null){
            throw new NullPointerException();
        }
        User user = userService.getUserByUserName(userName);

        //动态设置失败的登录地址
        userAuthenticationFilter.setAuthenticationFailureHandler(new SimpleUrlAuthenticationFailureHandler("/login.html?error=1"));
        if (user == null){
            //设置位置用户地址
            userAuthenticationFilter.setAuthenticationFailureHandler(new SimpleUrlAuthenticationFailureHandler("/login.html?error=2"));
            throw new UsernameNotFoundException("未知用户！");
        }

        UserAuth userAuth = UserAuth.builder().userId(user.getUserId()).loginName(user.getLoginName()).password(user.getPassword()).build();

        SavedRequestAwareAuthenticationSuccessHandler savedRequestAwareAuthenticationSuccessHandler = new SavedRequestAwareAuthenticationSuccessHandler();
        savedRequestAwareAuthenticationSuccessHandler.setAlwaysUseDefaultTargetUrl(true);
        savedRequestAwareAuthenticationSuccessHandler.setDefaultTargetUrl("/index.html");
        userAuthenticationFilter.setAuthenticationSuccessHandler(savedRequestAwareAuthenticationSuccessHandler);
        return userAuth;
    }
}
