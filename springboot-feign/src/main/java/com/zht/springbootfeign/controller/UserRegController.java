package com.zht.springbootfeign.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.common.common.dto.R;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * <p> 用户注册控制器 </p>
 *
 * @author: ZHT
 * @create: 2021-07-12 13:50
 **/
@RestController
@RequestMapping("/user")
public class UserRegController {
    
    private static Map<String, JSONObject> userMap = new HashMap<>();
    
    @PostMapping("/userReg")
    public R userReg(@RequestBody JSONObject user){
        String username = user.getString("username");
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = passwordEncoder.encode(user.getString("password"));
        user.put("password", password);
        userMap.put(username, user);
        return R.ok();
    }

    @PostMapping("/user-reg/userReg")
    public R login(@RequestBody JSONObject user){
        String username = user.getString("username");
        JSONObject object = userMap.get(username);
        return R.ok(object);
    }
    
}
