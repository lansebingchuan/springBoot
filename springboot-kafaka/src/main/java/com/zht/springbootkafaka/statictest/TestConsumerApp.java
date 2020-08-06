package com.zht.springbootkafaka.statictest;

import com.zht.springbootkafaka.constant.KafkaConstants;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;

import java.time.Duration;
import java.util.Arrays;

/**
 * <p>
 * 消费者主类
 * </p>
 *
 * @author ZHT
 * @since 2020/7/31
 */
public class TestConsumerApp {

    public static void main(String[] args) {
        //配置常量区
        KafkaConstants kafkaConstants = new KafkaConstants();
        //获取消费者
        Consumer<String, String> consumer = kafkaConstants.getConsumer();
        //设置消费的主题（topic）
        consumer.subscribe(Arrays.asList("test"));
        while (true) {
            System.out.println("poll start...");
            //主动获取的时间，多久一次
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(10000));
            //消息的数量
            int count = records.count();
            System.out.println("the numbers of topic:" + count);
            //遍历所有接收的消息体
            for (ConsumerRecord<String, String> record : records) {
                System.out.println(String.format("offset = %s, key = %s, value = %s", record.offset(), record.key(), record.value()));
            }
        }
    }
}
