package com.zht.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component ///这个是 rabbitmq 的配置类，可以进行多个类进行配置，那么就有多个不同的实例，发送到不同的交换机
public class RabbitConfig implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback {

    private Logger log = LoggerFactory.getLogger(RabbitConfig.class);

    private RabbitTemplate rabbitTemplate;

    @Autowired
    public RabbitConfig(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        this.rabbitTemplate.setConfirmCallback(this);//配置发送状态监听器
        this.rabbitTemplate.setReturnCallback(this);//配置消息失败返回监听器
        this.rabbitTemplate.setExchange("directExchange");//设置发向交换机
    }

    //这是发送的方法，封装在这里面了，外面我们需要发送信息给Exchange直接调用这个方法
    public void send(String routingKey , String message) {
        this.rabbitTemplate.convertAndSend(routingKey, message);
    }

    /**
     * 发送后的回调函数
     * @param correlationData - 发送ID
     * @param ack - boolean
     * @param cause - 原因
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (ack) {
            if (correlationData != null)
                log.info("消息发送exchange成功,id:{}", correlationData.getId());
        } else {
            if (correlationData != null)
                log.info("消息发送exchange失败,id:{},原因:{}", correlationData.getId(), cause);
            log.info("消息发送exchange失败,原因:{}", cause);
        }
    }

    /**
     * 消息发送失败的回调函数
     * @param message - 消息
     * @param replyCode - 应答码
     * @param replyText - 原因
     * @param exchange  - 交换机
     * @param routingKey - 路由键
     */
    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        String correlationId = message.getMessageProperties().getCorrelationId();
        log.info("消息:{}发送失败，应答码:{} 原因:{} 交换机:{} 路由键:{}", correlationId, replyCode, replyText, exchange, routingKey);
    }
}