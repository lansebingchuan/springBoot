package com.zht.auth.realm;

import com.zht.cons.BooleanString;
import com.zht.dubbo.rpcService.UserRpcService;
import com.zht.entity.User;
import org.apache.dubbo.common.utils.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.util.ObjectUtils;

/**
 * 普通用户shiro认证
 *
 * @author ZHT
 */
public class UsernamePasswordRealm extends AuthorizingRealm {

    @Reference
    private UserRpcService userRpcService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof UsernamePasswordToken;
    }

    /**
     * 授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return new SimpleAuthorizationInfo();
    }

    /**
     * 认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String loginName = token.getUsername();
        User user = userRpcService.getUserAuthByLoginName(loginName);
        //未知账号
        if (ObjectUtils.isEmpty(user)) {
            throw new UnknownAccountException();
        }
        //用户被禁用
        if (StringUtils.isEquals(user.getEnableFlag(), BooleanString.FALSE)) {
            throw new LockedAccountException();
        }
        return new SimpleAuthenticationInfo(loginName, user.getPassword(), getName());
    }
}
