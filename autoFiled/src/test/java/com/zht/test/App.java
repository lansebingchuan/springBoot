package com.zht.test;

import com.zht.test.service.UserService;
import com.zht.test.service.impl.UserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author zhanghaitao
 * @date 2020/3/20 0020
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {App.class})
@SpringBootApplication
public class App {

    @Autowired
    UserServiceImpl userService;

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Test
    public void myTest1(){
        userService.addUser();
    }
}
