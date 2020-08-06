package com.zht.service;

import com.zht.entity.User;

/**
 * <p>
 *  用户接口
 * </p>
 *
 * @author ZHT
 * @since 2020/6/14
 */
public interface UserService {

    /**
     * 根据用户名获取用户
     *
     * @param userName 用户名称
     * @return 用户
     */
    User getUserByUserName(String userName);
}
