package com.zht.controller;

import com.zht.dubbo.rpcService.UserRpcService;
import com.zht.entity.User;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ZHT
 */
@RestController
public class UserController {

    @Reference
    UserRpcService userRpcService;

    @RequestMapping("/getUser")
    public User getUser(){
        User user = userRpcService.getUser();
        return user;
    }

    @RequestMapping("/getUserTmp")
    public User getUserTmp(){
        return User.builder().name("张海涛").age(21).build();
    }

    @RequestMapping("/getUserByName")
    public User getUserByName(String name) throws InterruptedException{
        return userRpcService.getUserByName(name);
    }
}
