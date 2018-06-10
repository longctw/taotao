package com.xull.search.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.alibaba.druid.pool.DruidDataSource;

@Configuration
@PropertySource(value={"classpath:resource/db.properties"})
public class DataSourceConfig {
	
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
}
