package com.zht.shiroEntity.token;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * 管理员认证凭证
 *
 * @author ZHT
 */
public class AdminToken extends UsernamePasswordToken {

    public AdminToken(String loginName, String password){
        super(loginName, password);
    }
}
