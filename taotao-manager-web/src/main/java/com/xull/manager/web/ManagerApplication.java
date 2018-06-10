package com.xull.manager.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@SpringBootApplication
@ComponentScan(basePackages="com.xull")
@ImportResource(value = "classpath:dubbo/dubbo-consumer.xml")
@PropertySource(value= {"classpath:resource/fdfs.properties"})
public class ManagerApplication {

	@RequestMapping("hello")
	@ResponseBody
	public String hello(){
		return "hello springBoot And loong!";
	}
	
	public static void main(String[] args) {
		SpringApplication.run(ManagerApplication.class, args);
	}
	
	
}