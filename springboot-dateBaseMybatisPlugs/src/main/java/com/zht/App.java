package com.zht;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author ZHT
 */
@SpringBootApplication(exclude = DruidDataSourceAutoConfigure.class)
@ComponentScan("com.zht")
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
