package com.zht.websocket1;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zht.SpringBootMybatis;
import com.zht.util.redis.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;


//标记为Servlet不是为了其被访问，而是为了便于伴随Tomcat一起启动
public class BitCoinDataCenter implements Runnable {

    @Autowired
    @Qualifier("webServerObj")
    Object webServerObj;//控制是否发送客服端的锁

    private static int COUNT = 5;//停顿秒数
    private static int SLEEPTIME = 1000 * COUNT;//停顿秒数

    private Map<String, Object> map = new HashMap<>();//定义map数据集合

    public void init() {
        startup();
    }

    public void startup() {
        new Thread(this).start();
    }

    @Override
    public void run() {
        String message = null;
        Object objmessage = null;
        while (true) {
            try {
                synchronized (webServerObj) {
                    webServerObj.wait();//执行等待，当访问某个浏览路径的时候，释放这个锁
                    objmessage = RedisUtils.getObject("webServerMessage");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            RedisUtils.delkeyObject("webServerMessage");
            System.out.println("发送数据");
            map.put("price", objmessage);
            map.put("total", ServerManager.getTotal());
            try {
                message = SpringBootMybatis.objectMapper().writeValueAsString(map);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            //广播出去
            ServerManager.broadCast(message);
        }
    }
}