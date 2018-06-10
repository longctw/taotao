package com.xull.cart.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource(value={"classpath:resource/config.properties"})
@ComponentScan(basePackages = {"com.xull.cart"})
@ImportResource(value = {"classpath:dubbo/dubbo-consumer.xml"})
public class CartApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(CartApplication.class, args);
	}
	
}
