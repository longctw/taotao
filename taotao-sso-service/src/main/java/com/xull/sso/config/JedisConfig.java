package com.xull.sso.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.xull.sso.jedis.JedisClient;
import com.xull.sso.jedis.JedisClientPool;

import redis.clients.jedis.JedisPool;

@Configuration
@PropertySource(value={"classpath:resource/redis.properties"})
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
