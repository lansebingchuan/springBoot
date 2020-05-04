package com.zht.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.crypto.digest.BCrypt;
import com.zht.entity.User;
import com.zht.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author ZHT
 */
@Service
public class UserServiceImpl implements UserService {

    @Override
    public User getUser(String name) {
        //加密之后的密码
        String password = BCrypt.hashpw("1");
        return ObjectUtil.equal("zht", name) ? User.builder().name("张海涛").age(21).password(password).build() : User.builder().name("无名").age(2).build();
    }

}
