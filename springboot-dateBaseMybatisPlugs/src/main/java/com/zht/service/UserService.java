package com.zht.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zht.entity.User;

/**
 * @author ZHT
 */
public interface UserService extends IService<User> {

    /**
     * 通过id‘获取user
     * @param user
     * @return
     */
    User getUser(User user);
}
