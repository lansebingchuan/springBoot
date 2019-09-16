package com.zht.rabbitMqConsumersController;

import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

/**
 * @author zht
 * @create 2019-09-11 9:44
 */
@Component
public class StudentConsumers {

    private Logger log = LoggerFactory.getLogger(StudentConsumers.class);

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "student", durable = "true"),//申明绑定的队列
            exchange = @Exchange(name = "directExchange", durable = "true", type = "direct"),//绑定的交换机
            key = "stu"))//指定路由 ，使用绑定的话，如果没有指定的 路由 key 那么会在交换机上自动创建
    @RabbitHandler
    public void topicProcess2(Message message,
                              @Headers Map<String, Object> headers,
                              Channel channel) throws Exception {
        //出现错误拒绝消息
        if (headers.get("error") != null) {
            channel.basicReject((Long) headers.get(AmqpHeaders.DELIVERY_TAG), false);
            return;
        }
        //确认消息
        Long deli = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        channel.basicAck(deli, false);
        log.info("接收消息："+new String(message.getBody()));
    }

    //@RabbitListener(queues = "student" )//指定简单的队列，没有路由KEY ，那么也只能收取 普通队列发送的 消息，不能接受交换机的信息
    public void topicProcess3(Message message,
                              @Headers Map<String, Object> headers,
                              Channel channel) throws Exception {
        //出现错误拒绝消息
        if (headers.get("error") != null) {
            channel.basicReject((Long) headers.get(AmqpHeaders.DELIVERY_TAG), false);
            return;
        }
        //确认消息
        Long deli = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        channel.basicAck(deli, false);
        log.info("接收消息1："+new String(message.getBody()));
//        log.info("接收消息1："+new String(message));
    }
}