package com.zht.serviceImpl;

import com.zht.annotation.SysLog;
import com.zht.entity.User;
import com.zht.service.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author zhanghaitao
 * @date 2020/3/20 0020
 */
@Service
public class UserServiceImpl implements UserService {

    @Override
    public void addUser() {
        User zht = User.builder().name("zht").createTime(LocalDateTime.now()).build();
        System.out.println(zht.toString());
    }

    @Override
    public User getUser(String name) {
        return User.builder().name("张海涛1").createTime(LocalDateTime.now()).build();
    }

}
