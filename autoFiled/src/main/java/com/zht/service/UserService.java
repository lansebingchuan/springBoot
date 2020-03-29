package com.zht.service;

import com.zht.entity.User;

/**
 * @author zhanghaitao
 * @date 2020/3/20 0020
 */
public interface UserService {

    /**
     * 添加用户
     */
    void addUser();

    /**
     * 获取用户
     * @param name
     * @return
     */
    User getUser(String name);
}
