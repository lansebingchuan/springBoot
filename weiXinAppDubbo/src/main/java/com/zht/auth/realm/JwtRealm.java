package com.zht.auth.realm;

import com.zht.JwtUtils;
import com.zht.auth.token.JwtToken;
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

/**
 * 令牌登录认证
 *
 * @author ZHT
 */
public class JwtRealm extends AuthorizingRealm {

    @Reference
    private UserRpcService userRpcService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    /**
     * 授权
     *
     * @param principalCollection 实现
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return new SimpleAuthorizationInfo();
    }

    /**
     * 用户认证
     *
     * @param authenticationToken 认证token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        JwtToken token = (JwtToken) authenticationToken;
        String jwt = (String) token.getCredentials();
        User user = userRpcService.getUserAuthByJwt(jwt);
        String generate = JwtUtils.generate(1, "20161047036");
        if (user == null || !JwtUtils.validate(jwt, user.getId(), user.getPassword())) {
            throw new AuthenticationException("无效的jwt.");
        }
        User userOn = userRpcService.getUserById(user.getId());
        //用户不存在
        if (userOn == null) {
            throw new UnknownAccountException();
        }
        //用户被禁用
        if (StringUtils.isEquals(user.getEnableFlag(), BooleanString.TRUE)) {
            throw new LockedAccountException();
        }
        return new SimpleAuthenticationInfo(userOn, token, getName());
    }
}
