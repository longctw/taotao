package com.xull.redis;

import org.junit.Test;

import redis.clients.jedis.Jedis;

public class RedisTest {
	
	@Test
	public void test1(){
		Jedis jedis = new Jedis("192.168.66.107", 6379);
		
		jedis.set("hello", "hello jedis");
		String str = jedis.get("hello");
		jedis.close();
		System.out.println(str);
	}
	
}
