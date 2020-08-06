package com.zht.controller;

import com.zht.entity.User;
import com.zht.enumManager.TeacherEnum;
import com.zht.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author ZHT
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping("/getUser")
    public User getUser(){
       return userService.getUser();
    }

    /**
     * 通过枚举类型获取教师职称
     *
     * @param teacherEnum 枚举类型
     * @return 对应教师
     */
    @RequestMapping("/getUserByType")
    public ModelAndView getUser(ModelAndView mav, TeacherEnum teacherEnum){
        mav.setViewName("/success");
        mav.addObject("enumStr", teacherEnum.toString());
        return mav;
    }
}
