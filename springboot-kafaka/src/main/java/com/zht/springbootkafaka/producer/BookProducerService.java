package com.zht.springbootkafaka.producer;

import com.zht.springbootkafaka.entity.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.concurrent.ExecutionException;

/**
 * <p>
 * 书的生产类
 * </p>
 *
 * @author ZHT
 * @since 2020/8/1
 */
@Service
public class BookProducerService {

    /**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(BookProducerService.class);

    /**
     * kafka消息模板
     */
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public BookProducerService(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }


    public void sendMsg(String topic, Object value) {
        ListenableFuture<SendResult<String, Object>> listenableFuture = kafkaTemplate.send(topic, value);
        try {
            //同步获取发送消息的结果
            SendResult<String, Object> sendResult = listenableFuture.get();
            if (sendResult.getRecordMetadata() != null) {
                logger.info("生产者成功发送消息到, topic：" + sendResult.getProducerRecord().topic() + "-> " + sendResult.getProducerRecord().value().toString());
            }
            //异步获取消息发送的结果
            listenableFuture.addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {
                @Override
                public void onFailure(Throwable throwable) {
                    logger.error("消息发送失败！ book："+ value.toString());
                }

                @Override
                public void onSuccess(SendResult<String, Object> sendResult) {
                    if (sendResult.getRecordMetadata() != null) {
                        logger.info("生产者成功发送消息到，topic：" + sendResult.getProducerRecord().topic() + "-> " + sendResult.getProducerRecord().value().toString());
                    }
                }
            });
            //优化之后的异步消息结果
            listenableFuture.addCallback(result -> logger.info("生产者成功发送消息！ topic: {}, partition: {}, value: {}",
                    result.getProducerRecord().topic(),
                    result.getProducerRecord().partition(),
                    result.getProducerRecord().value().toString()),
                    ex -> logger.error("消息发送失败！ 原因：{}", ex.getMessage()));
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

    }

}
