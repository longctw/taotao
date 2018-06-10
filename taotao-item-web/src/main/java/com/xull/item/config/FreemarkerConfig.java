package com.xull.item.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

@Configuration
@PropertySource(value = {"classpath:freemarker/freemarker.properties"})
public class FreemarkerConfig {
	
	@Value("${TEMPLATE_LOADER_PATH}")
	private String TEMPLATE_LOADER_PATH;
	
	@Value("${DEFAULT_ENCODING}")
	private String DEFAULT_ENCODING;

	@Bean
	public FreeMarkerConfigurer freeMarkerConfigurer(){
		FreeMarkerConfigurer config = new FreeMarkerConfigurer();
		config.setTemplateLoaderPath(TEMPLATE_LOADER_PATH);
		config.setDefaultEncoding(DEFAULT_ENCODING);
		return config;
	}
}
