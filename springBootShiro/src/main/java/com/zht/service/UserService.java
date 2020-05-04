package com.zht.service;

import com.zht.entity.User;

/**
 * @author ZHT
 */
public interface UserService {

    /**
     * 根据用户名获取user
     * @param name 用户名
     * @return user对象
     */
    User getUser(String name);
}
