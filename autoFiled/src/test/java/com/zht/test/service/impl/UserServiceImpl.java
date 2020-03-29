package com.zht.test.service.impl;

import com.zht.test.annotation.SysLog;
import com.zht.entity.User;
import com.zht.test.service.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author zhanghaitao
 * @date 2020/3/20 0020
 */
@Service
public class UserServiceImpl implements UserService {

    @Override
    @SysLog("添加用户")
    public void addUser() {
        User zht = User.builder().name("zht").createTime(LocalDateTime.now()).build();
        System.out.println(zht.toString());
    }

}
