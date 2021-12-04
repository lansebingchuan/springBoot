package com.zht.controller;

import com.zht.business.entity.User;
import com.zht.business.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "用户api")
@RestController
@RequestMapping("/api/user")
public class ApiController {

    @Autowired
    private UserService userService;

    @ApiOperation("根据用户名查找用户")
    @GetMapping("/getByName")
    public User getByName(String name) {
        return userService.getUser(name);
    }
}
