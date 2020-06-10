package com.zht.controller;

import com.zht.context.CurrentUserHolder;
import com.zht.dubbo.rpcService.UserRpcService;
import com.zht.entity.User;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * jwt控制器测试类
 *
 * @author ZHT
 */
@RestController
@RequestMapping("/api")
public class JwtController {

    @Reference
    private UserRpcService userRpcService;

    /**
     * 获取当前登录用户
     *
     * @return
     */
    @RequestMapping("/getJwtUser")
    public User getJwtUser(){
        return CurrentUserHolder.getUser();
    }

    /**
     * 获取当前登录用户
     *
     * @return
     */
    @RequestMapping("/listUser")
    public List<User> listUser(User user){
        return userRpcService.listUser(user);
    }
}
