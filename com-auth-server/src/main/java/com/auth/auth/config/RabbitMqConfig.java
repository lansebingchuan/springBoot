package com.auth.auth.config;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * <p> RabbitMqConfig </p>
 *
 * @author: ZHT
 * @create: 2021-07-15 14:43
 **/
@Configuration
public class RabbitMqConfig {
    
    @Autowired
    RabbitTemplate rabbitTemplate;
    
    @PostConstruct
    private void initRabbitConfirmCallback(){
        
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            
            /**
             * rabbitmq消费 确认回调机制
             * 
             * @param correlationData 发送的消息数据信息
             * @param ack 是否成功消费
             * @param cause 消费失败原因
             */
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
            }
        });
        
        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {

            /**
             * 抵达消息队列时回调
             * 
             * @param message 哪个消息失败了
             * @param replyCode 回复的状态码
             * @param replyText 回复的文本内容
             * @param exchange 消息是发给哪个交换机
             * @param routingKey 消息发送给哪个路由
             */
            @Override
            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
            }
        });
    }
}
