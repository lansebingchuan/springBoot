package com.zht.controller;

import com.zht.annotation.FormatDateTime;
import com.zht.annotation.SysLog;
import com.zht.entity.User;
import com.zht.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhanghaitao
 * @date 2020/3/20 0020
 */
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping("/addUser")
    @SysLog("'添加用户：'+ #name +''")
    @FormatDateTime
    public void addUser(){
        userService.addUser();
    }

    @RequestMapping("/getUser")
    @SysLog("'获取用户：'+ #name +''")
    @FormatDateTime
    public User getUser(String name){
        return userService.getUser(name);
    }
}
