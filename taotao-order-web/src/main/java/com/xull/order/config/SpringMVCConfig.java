package com.xull.order.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.xull.order.interceptor.UserLoginHandlerInterceptor;

@Configuration
public class SpringMVCConfig extends WebMvcConfigurerAdapter{
	
	@Autowired
	private UserLoginHandlerInterceptor userLoginInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(userLoginInterceptor).addPathPatterns("/order/**");
	}
}
