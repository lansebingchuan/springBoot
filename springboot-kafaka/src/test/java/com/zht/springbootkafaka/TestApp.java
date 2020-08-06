package com.zht.springbootkafaka;

import com.zht.springbootkafaka.constant.KafkaConstants;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;

import java.util.Arrays;

/**
 * <p>
 * 消费者
 * </p>
 *
 * @author ZHT
 * @since 2020/7/31
 */
public class TestApp {

    public static void main(String[] args) {
        KafkaConstants kafkaConstants = new KafkaConstants();
        Consumer<String, String> consumer = kafkaConstants.getConsumer();
        consumer.subscribe(Arrays.asList("test"));
        while (true) {
            System.out.println("poll start...");
            ConsumerRecords<String, String> records = consumer.poll(2000);
            int count = records.count();
            System.out.println("the numbers of topic:" + count);
            for (ConsumerRecord<String, String> record : records) {
                System.out.println(String.format("offset = %s, key = %s, value = %s", record.offset(), record.key(), record.value()));
            }
        }
    }
}
