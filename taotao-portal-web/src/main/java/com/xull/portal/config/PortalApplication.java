package com.xull.portal.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@ComponentScan(basePackages={"com.xull.portal"})
@ImportResource(value={"classpath:dubbo/dubbo-consumer.xml"})
@PropertySource(value={"classpath:resource/ad1.properties"})
public class PortalApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(PortalApplication.class, args);
	}
	
}
