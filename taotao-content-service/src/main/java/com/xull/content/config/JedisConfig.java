package com.xull.content.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.xull.content.jedis.JedisClient;
import com.xull.content.jedis.JedisClientPool;

import redis.clients.jedis.JedisPool;

@Configuration
@PropertySource(value = {"classpath:conf/content_redis.properties"})
public class JedisConfig {
	
	@Bean
	public JedisPool jedisPool(){
		return new JedisPool("192.168.66.107",6379);
	}
	
	@Bean
	public JedisClient jedisClient(){
		return new JedisClientPool();
	}
}
