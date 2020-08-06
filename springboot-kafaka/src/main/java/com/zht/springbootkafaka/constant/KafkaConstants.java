package com.zht.springbootkafaka.constant;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * <p>
 * kafka配置常量
 * </p>
 *
 * @author ZHT
 * @since 2020/7/30
 */
@Component
public class KafkaConstants {

    //kafka服务器ip及端口
    public static final String BROKER_LIST = "192.168.1.6:9092";
    //组的配置
    public static String GROUP_ID_CONFIG="consumerGroup1";

    /**
     * 创建kafka生产者
     *
     * @return 生产者
     */
    public Producer<String, String> getProducer(){
        //创建配置文件
        Properties properties = new Properties();
        //设置服务地址
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BROKER_LIST);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        return new KafkaProducer<String, String>(properties);
    }

    public Consumer<String, String> getConsumer(){
        //创建配置文件
        Properties properties = new Properties();
        //设置服务地址
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BROKER_LIST);
        //连接客户端id
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID_CONFIG);
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        return new KafkaConsumer<String, String>(properties);
    }

}
