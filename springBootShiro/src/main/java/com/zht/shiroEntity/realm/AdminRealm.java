package com.zht.shiroEntity.realm;

import com.zht.entity.User;
import com.zht.service.UserService;
import com.zht.shiroEntity.context.UserContext;
import com.zht.shiroEntity.token.AdminToken;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author ZHT
 */
public class AdminRealm extends AuthorizingRealm {

    @Autowired
    UserService userService;

    /**
     * 进行授权
     * @param principalCollection
     * @return 权限信息
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        //查询管理员权限
        List<String> menuList = new ArrayList();
        Set<String> permissions = new HashSet<>();
        for (String menu : menuList) {
            permissions.add(menu);
        }
        authorizationInfo.setStringPermissions(permissions);
        return authorizationInfo;
    }

    /**
     * 进行认证
     * @param authenticationToken
     * @return 登录信息
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        AdminToken adminToken = (AdminToken) authenticationToken;
        String username = adminToken.getUsername();
        User adminUser = userService.getUser(username);
        //未知账号
        if (ObjectUtils.isEmpty(adminUser)) {
            throw new UnknownAccountException();
        }
        UserContext userContext = new UserContext();
        userContext.setName(adminUser.getName());
        userContext.setAge(adminUser.getAge());
        userContext.setJob("管理员");
        //凭证内容， 凭证密码，登录名
        return new SimpleAuthenticationInfo(userContext, adminUser.getPassword(), getName());
    }
}
