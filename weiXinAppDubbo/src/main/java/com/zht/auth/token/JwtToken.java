package com.zht.auth.token;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * 令牌认证token
 *
 * @author ZHT
 */
public class JwtToken implements AuthenticationToken {

    /**
     * 凭证
     */
    private String token;

    public JwtToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
