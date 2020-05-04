package com.zht.shiroEntity.realm;

import cn.hutool.core.util.ObjectUtil;
import com.zht.entity.User;
import com.zht.service.UserService;
import com.zht.shiroEntity.context.UserContext;
import com.zht.shiroEntity.token.UserToken;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 普通用户权限登录判断类
 *
 * @author ZHT
 */
public class UserRealm extends AuthorizingRealm {

    @Autowired
    UserService userService;

    /**
     * 获取权限方法-授权
     * @param principalCollection
     * @return
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
     * 获取登录信息-认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UserToken userToken = (UserToken) authenticationToken;
        User user = userService.getUser(userToken.getUsername());
        if (ObjectUtil.isNull(user)){
            throw new UnknownAccountException();
        }
        UserContext userContext = new UserContext();
        userContext.setName(user.getName());
        userContext.setAge(user.getAge());
        userContext.setJob("普通用户");
        //认证信息，认证密码，登录名
        return new SimpleAuthenticationInfo(userContext, user.getPassword(), getName());
    }
}
