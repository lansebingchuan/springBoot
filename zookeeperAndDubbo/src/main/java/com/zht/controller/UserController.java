package com.zht.controller;

import com.zht.entity.User;
import com.zht.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ZHT
 */
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @ResponseBody
    @RequestMapping("/getUser")
    public User getUser(){
       return userService.getUser();
    }
}
