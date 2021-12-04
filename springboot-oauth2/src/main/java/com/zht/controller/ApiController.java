package com.zht.controller;

import com.zht.business.entity.User;
import com.zht.business.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class ApiController {

    @Autowired
    private UserService userService;

    @GetMapping("/getByName")
    public User getByName(String name) {
        return userService.getUser(name);
    }
}
