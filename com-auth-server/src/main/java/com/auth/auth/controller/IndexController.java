package com.auth.auth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * <p> </p>
 *
 * @author: ZHT
 * @create: 2021-07-09 11:43
 **/
@RestController
public class IndexController {
    
    @GetMapping("/index")
    public String index(HttpSession session) {
        session.setAttribute("user", "张海涛");
        return "你好nginx";
    }
    
}
