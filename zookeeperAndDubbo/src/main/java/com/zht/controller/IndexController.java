package com.zht.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 视图开始
 *
 * @author ZHT
 */
@Controller
public class IndexController {

    /**
     * 到枚举测试页面
     *
     * @return
     */
    @RequestMapping("/enumTest")
    public ModelAndView index(ModelAndView mav){
        mav.setViewName("/enumTest");
        return mav;
    }
}
