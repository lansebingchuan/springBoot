package com.zht.controller;

import com.zht.baseEntity.ApiResult;
import com.zht.entity.User;
import com.zht.exception.ErrorCode;
import com.zht.service.UserService;
import com.zht.shiroEntity.token.UserToken;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sun.plugin.liveconnect.SecurityContextHelper;

/**
 * @author ZHT
 */
@Controller
public class LoginController {

    @Autowired
    UserService userService;

    /**
     * 用户登录
     *
     * @param loginName 用户名
     * @param password 密码
     * @return 通用返回结果
     */
    @RequestMapping("login")
    public ModelAndView login(String loginName, String password, ModelAndView mav){
        if (StringUtils.isAllEmpty(loginName, password)){
            mav.setViewName("redirect:/login.html?error");
        }
        //shiro登录对象token
        UserToken token = new UserToken(loginName, password);
        //获取登录
        Subject subject = SecurityUtils.getSubject();
        try {
            //进行登录
            subject.login(token);
            //登录成
            mav.setViewName("redirect:/index/home");
        }catch (AuthenticationException e){
            mav.setViewName("redirect:/login.html?error");
        }
        return mav;
    }
}
