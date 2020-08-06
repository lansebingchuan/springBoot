package com.zht.springbootkafaka.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zht.springbootkafaka.entity.Book;
import com.zht.springbootkafaka.producer.BookProducerService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 书籍消费者实体
 * </p>
 *
 * @author ZHT
 * @since 2020/8/1
 */
@Service
public class BookConsumerService {

    /**
     * 消费的主题（topic）
     */
    @Value("${kafka.topic.test}")
    private String testTopic;

    /**
     * 日志
     */
    private final Logger logger = LoggerFactory.getLogger(BookConsumerService.class);

    /**
     * json操作对象
     */
    private final ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = {"${kafka.topic.test}"}, groupId = "consumerGroup1")
    public void listenerBookMessage(ConsumerRecord<String, String> bookConsumerRecord){
        try {
            Book book = objectMapper.readValue(bookConsumerRecord.value(), Book.class);
            logger.info("消费者收到-> topic: {} , partition: {}, 书籍消息： {}", bookConsumerRecord.topic(), bookConsumerRecord.partition(), book.toString());
        } catch (JsonProcessingException e) {
            logger.error("json序列化出现异常！原因： {}", e.getMessage());
            e.printStackTrace();
        }
    }
}
