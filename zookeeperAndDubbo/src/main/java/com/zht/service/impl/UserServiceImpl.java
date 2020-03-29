package com.zht.service.impl;

import com.zht.entity.User;
import com.zht.service.UserService;
import org.springframework.boot.autoconfigure.klock.annotation.Klock;
import org.springframework.stereotype.Service;

/**
 * @author ZHT
 */
@Service
public class UserServiceImpl implements UserService {

    @Override
    @Klock(keys = "zht")
    public User getUser() {
        return User.builder().name("张海涛").age(22).build();
    }

    @Override
    @Klock(waitTime = Long.MAX_VALUE)
    public User getUserByName(String name) throws InterruptedException {
        if ("zht".equals(name)){
            return User.builder().name("张海涛").age(22).build();
        }else {
            //线程等待 50 秒
            Thread.sleep(1000 * 5);
            return User.builder().name("张海涛11").age(22).build();
        }
    }
}
