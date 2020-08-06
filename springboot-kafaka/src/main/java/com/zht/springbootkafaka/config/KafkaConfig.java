package com.zht.springbootkafaka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.converter.RecordMessageConverter;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;

/**
 * <p>
 * kafka配置类
 * </p>
 *
 * @author ZHT
 * @since 2020/8/1
 */
@Configuration
public class KafkaConfig {

    @Value("${kafka.topic.test}")
    private String testTopic;

    /**
     * json消息转换器
     *
     * @return
     */
    @Bean
    public RecordMessageConverter recordMessageConverter(){
        return new StringJsonMessageConverter();
    }

    /**
     * 会帮助你创建一个topic，如果存在则会忽略
     *
     * @return
     */
    @Bean
    public NewTopic testTopic(){
        return new NewTopic(testTopic, 3, (short) 1);
    }

}
