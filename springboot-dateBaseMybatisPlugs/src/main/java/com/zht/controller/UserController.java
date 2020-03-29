package com.zht.controller;

import com.zht.entity.BanJi;
import com.zht.entity.User;
import com.zht.service.BanJiService;
import com.zht.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ZHT
 */
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    BanJiService banJiService;

    @RequestMapping("/getUser")
    public User getUser(User user){
        return userService.getUser(user);
    }

    @RequestMapping("/getBanJi")
    public BanJi getBanJi(BanJi banJi){
        return banJiService.getById(banJi.getId());
    }
}
