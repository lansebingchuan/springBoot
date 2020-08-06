package com.zht.controller;

import com.zht.entity.UserEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 首页控制器
 * </p>
 *
 * @author ZHT
 * @since 2020/8/2
 */
@Controller
public class IndexController {


    @RequestMapping("/index")
    public ModelAndView index(){
        ModelAndView mav = new ModelAndView("index");
        //单个属性
        mav.addObject("zht", "张海涛");
        //列表
        List<UserEntity> userEntityList = Arrays.asList(new UserEntity[]{
                UserEntity.builder().name("小明").age(21).build(),
                UserEntity.builder().name("孙尚香").age(7).build(),
        });
        //列表属性
        mav.addObject("userEntityList", userEntityList);
        mav.addObject("userEntityList1", Arrays.asList());
        return mav;
    }
}
