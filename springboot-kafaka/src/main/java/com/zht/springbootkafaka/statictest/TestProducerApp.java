package com.zht.springbootkafaka.statictest;

import com.zht.springbootkafaka.constant.KafkaConstants;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Arrays;

/**
 * <p>
 * 生产者主类
 * </p>
 *
 * @author ZHT
 * @since 2020/7/31
 */
public class TestProducerApp {

    //kafka发送的测试主题
    private static String TESTTOPIC = "test";

    public static void main(String[] args) {
        //构造实体类
        KafkaConstants kafkaConstants = new KafkaConstants();
        //获取生产者实体类
        Producer<String, String> producer = kafkaConstants.getProducer();
        //配置消息体
        ProducerRecord<String, String> record = new ProducerRecord<String, String>(TESTTOPIC, "我是张海涛");
        try {
            //发送消息（同步方式），得到发送信息结果
            RecordMetadata metadata = producer.send(record).get();
            System.out.println("Record sent to partition " + metadata.partition()
                    + " with offset " + metadata.offset());
        } catch (Exception e) {
            System.out.println("Error in sending record");
            e.printStackTrace();
        }
        //关闭流对象
        producer.close();
    }
}
