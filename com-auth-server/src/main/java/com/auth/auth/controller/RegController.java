package com.auth.auth.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.auth.auth.serve.UserRegServe;
import com.auth.auth.vo.UserLoginVo;
import com.auth.auth.vo.UserRegVo;
import com.common.common.dto.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p> 注册控制器 </p>
 *
 * @author: ZHT
 * @create: 2021-07-12 10:29
 **/
@Slf4j
@Controller
public class RegController {
    
    @Autowired
    private UserRegServe userRegServe;

    /**
     * RedirectAttributes attributes 重定向的时候设置的参数，模仿session设置参数，但是在一次取出后会被删除
     * 
     * @param userRegVo
     * @param result
     * @param attributes
     * @return
     */
    @PostMapping("/reg")
    public String reg(@Validated UserRegVo userRegVo, BindingResult result, RedirectAttributes attributes){
        JSONObject user = new JSONObject();
        user.put("username", userRegVo.getUsername());
        user.put("password", userRegVo.getPassword());
        user.put("phone", userRegVo.getPhone());
        R r = userRegServe.userReg(user);
        if (result.hasErrors()) {
            Map<String, String> errorMap = result.getFieldErrors().stream().collect(Collectors.toMap((a) -> {
                return a.getField();
            }, (a) -> {
                return a.getDefaultMessage();
            }));
            attributes.addFlashAttribute("errorMap", errorMap);
            // 回到注册也
            return "redirect:http://auth.com/login.html";
        }
        // 回到首页
        return "redirect:http://auth.com/index.html";
    }

    @PostMapping("/login")
    public String login(@Validated UserLoginVo userLoginVo){
        JSONObject user = new JSONObject();
        user.put("username", userLoginVo.getUsername());
        user.put("password", userLoginVo.getPassword());
        R r = userRegServe.login(user);
        log.info("登录详情：{}", JSON.toJSONString(r));
        // 回到首页
        return "redirect:http://auth.com/index.html";
    }
}
