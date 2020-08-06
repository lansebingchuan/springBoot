package com.zht.springbootkafaka.controller;

import com.zht.springbootkafaka.constant.KafkaConstants;
import com.zht.springbootkafaka.entity.Book;
import com.zht.springbootkafaka.producer.BookProducerService;
import com.zht.springbootkafaka.service.KafkaService;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.Duration;
import java.util.Collections;
import java.util.concurrent.ExecutionException;


/**
 * <p>
 * kafka前端控制器
 * </p>
 *
 * @author ZHT
 * @since 2020/7/30
 */
@Controller
public class KafkaController {

    //kafka发送的测试主题
    private static String TESTTOPIC = "test";

    @Autowired
    private KafkaService kafkaService;

    @Autowired
    private BookProducerService bookProducerService;
    /**
     * 生产者发送消息
     */
    @ResponseBody
    @RequestMapping("/sendMsg")
    public void sendMsg(String msg){
        //（生产）发送消息
        bookProducerService.sendMsg(TESTTOPIC, Book.builder().name("hw").price(1f).build());
    }


}
