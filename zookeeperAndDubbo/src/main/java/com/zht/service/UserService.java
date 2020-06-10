package com.zht.service;

import com.zht.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

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

    /**
     * 根据登录名获取用户信息
     *
     * @param loginName
     * @return
     */
    User getUserAuthByLoginName(String loginName);

    /**
     * 通过jwt令牌获取用户
     *
     * @param jwt 令牌
     * @return 用户
     */
    User getUserAuthByJwt(String jwt);


    /**
     * 根据id获取用户
     *
     * @param id 用于id
     * @return  用户
     */
    User getUserById(Integer id);

    /**
     * 获取所有的用户
     *
     * @param user 用户
     * @return 用户列表
     */
    List<User> listUser(User user);
}
