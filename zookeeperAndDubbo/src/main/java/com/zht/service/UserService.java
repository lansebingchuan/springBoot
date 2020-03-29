package com.zht.service;

import com.zht.entity.User;
import org.springframework.stereotype.Service;

/**
 * @author ZHT
 */
@Service
public interface UserService {

    /**
     * 获取用户
     * @return
     */
    User getUser();

    /**
     * 根据姓名获取user
     * @param name
     * @return
     */
    User getUserByName(String name) throws InterruptedException;
}
