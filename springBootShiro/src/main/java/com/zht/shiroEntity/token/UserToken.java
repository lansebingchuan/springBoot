package com.zht.shiroEntity.token;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * 普通用户凭证
 *
 * @author ZHT
 */
public class UserToken extends UsernamePasswordToken {

    public UserToken(String loginName, String password){
        super(loginName, password);
    }
}
