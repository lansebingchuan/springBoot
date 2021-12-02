package com.zht.business.service;

import com.zht.business.entity.User;

/**
 * 用户服务
 */
public interface UserService {

    /**
     * 根据人员名称获取人员
     *
     * @param userName
     * @return
     */
    User getUser(String userName);
}
