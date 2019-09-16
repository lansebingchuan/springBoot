package com.zht;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * @author zht
 * @create 2019-09-11 9:47
 */
@SpringBootApplication
public class RabbitMpApp {

    public static void main(String[] args) {
        SpringApplication.run(RabbitMpApp.class,args);
    }
}
