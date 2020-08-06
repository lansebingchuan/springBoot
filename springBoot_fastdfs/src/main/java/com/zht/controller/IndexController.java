package com.zht.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {

    @RequestMapping("/indexPage")
    public ModelAndView index(ModelAndView mav){
        mav.setViewName("index");
        return mav;
    }

    @RequestMapping("/indexPage2")
    public ModelAndView index2(ModelAndView mav){
        mav.setViewName("index_upfile2");
        return mav;
    }
}
