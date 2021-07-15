package com.auth.auth;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * 1、@EnableRedisHttpSession 使用redis作为session 存储
 *    之前导入了：RedisHttpSessionConfiguration 配置类
 *    封装bean:
 *      1、RedisOperationsSessionRepository 用于操作session的增删改查，然后间接操作redis
 *      2、SessionRepositoryFilter<S extends Session> extends OncePerRequestFilter 用于请求时的过滤 实现了 filter 接口，用于操作和发现session
 *          注入了： sessionRepository(第一步)
 *          核心控制方法：doFilterInternal（）。实现了普通filter，并对他操作
 *              包装了原有的request、response：包装为：SessionRepositoryRequestWrapper，SessionRepositoryResponseWrapper。
 *                  此后的获取session都用：RedisOperationsSessionRepository 进行操作，因为重写了标准里面的 
 *       3、装饰者模式
 *       4、session自动续期、也有过期时间
 */
@EnableRabbit
@EnableRedisHttpSession
@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
public class ComAuthServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ComAuthServerApplication.class, args);
    }

}
