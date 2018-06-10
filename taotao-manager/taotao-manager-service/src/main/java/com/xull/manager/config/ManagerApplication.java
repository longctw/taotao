package com.xull.manager.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

import com.alibaba.druid.pool.DruidDataSource;

@SpringBootApplication
@PropertySource(value={"classpath:db.properties","classpath:resource/activemq.properties"})
@ComponentScan(basePackages="com.xull.manager")
@ImportResource(value="classpath:dubbo/dubbo-provider.xml")
public class ManagerApplication {
	
	@Value("${jdbc.url}")
    private String jdbcUrl;

    @Value("${jdbc.driver}")
    private String jdbcDriverClassName;

    @Value("${jdbc.username}")
    private String jdbcUsername;

    @Value("${jdbc.password}")
    private String jdbcPassword;

    @Bean(destroyMethod = "close")
    public DruidDataSource dataSource() {
    	DruidDataSource dataSource = new DruidDataSource();
    	
    	dataSource.setUrl(jdbcUrl);
    	dataSource.setUsername(jdbcUsername);
    	dataSource.setPassword(jdbcPassword);
    	dataSource.setDriverClassName(jdbcDriverClassName);
    	
    	dataSource.setMaxActive(10);
    	dataSource.setMinIdle(5);
    	
    	return dataSource;
    }
    
    public static void main(String[] args) {
		SpringApplication.run(ManagerApplication.class, args);
	}
	
}
