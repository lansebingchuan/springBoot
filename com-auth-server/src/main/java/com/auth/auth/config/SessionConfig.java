package com.auth.auth.config;

import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * @author jitendra on 3/3/16.
 */
@Configuration
public class SessionConfig {

	/**
	 * redis序列化
	 * 
	 * @return redis序列化
	 */
	@Bean
	public RedisSerializer<Object> springSessionDefaultRedisSerializer() {
		return new GenericFastJsonRedisSerializer();
	}

}