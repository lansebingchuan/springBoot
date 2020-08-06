package com.zht.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * <p>
 * 首页控制器
 * </p>
 *
 * @author ZHT
 * @since 2020/6/14
 */
@Controller
public class IndexController {


    /**
     * 登陆页面
     *
     * @return
     */
    @RequestMapping("/login.html")
    public ModelAndView login() {
        return new ModelAndView("/login");
    }
}
