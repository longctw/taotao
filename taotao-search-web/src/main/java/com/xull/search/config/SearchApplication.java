package com.xull.search.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@ComponentScan(basePackages={"com.xull.search"})
@ImportResource(value={"classpath:dubbo/dubbo-consumer.xml"})
@PropertySource(value={"classpath:resource/solr.properties"})
public class SearchApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(SearchApplication.class, args);
	}
}
