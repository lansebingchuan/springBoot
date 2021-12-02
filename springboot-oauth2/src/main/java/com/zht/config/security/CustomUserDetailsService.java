package com.zht.config.security;

import cn.hutool.core.util.ObjectUtil;
import com.zht.business.entity.User;
import com.zht.business.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * spring-security。用户查找自定义类
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getUser(username);
        if (ObjectUtil.isEmpty(user)) {
            throw new UsernameNotFoundException("用户未注册！");
        }
        return user;
    }
}
