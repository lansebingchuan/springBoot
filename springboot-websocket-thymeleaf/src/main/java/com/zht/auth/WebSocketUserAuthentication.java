package com.zht.auth;

import java.security.Principal;

/**
 * <p>
 * websocket用户认证
 * </p>
 *
 * @author ZHT
 * @since 2020/8/3
 */
public class WebSocketUserAuthentication implements Principal {

    /**
     * 用户身份标识符
     */
    private String token;

    public WebSocketUserAuthentication(String token) {
        this.token = token;
    }

    public WebSocketUserAuthentication() {
    }

    /**
     * 获取用户登录令牌
     * @return
     */
    @Override
    public String getName() {
        return token;
    }
}
