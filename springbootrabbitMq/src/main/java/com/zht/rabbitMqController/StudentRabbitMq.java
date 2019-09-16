package com.zht.rabbitMqController;

import com.zht.util.RabbitConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zht
 * @create 2019-09-11 9:44
 */
@RestController
public class StudentRabbitMq {

    @Autowired
    RabbitConfig rabbitConfig;

    @RequestMapping("/sendMessageStu")
    public String sendMessageStu(){
        String message =  "张海涛";
        rabbitConfig.send("stu",message);
        return "发送："+message;
    }

}
