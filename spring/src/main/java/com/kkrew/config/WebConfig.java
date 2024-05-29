package com.kkrew.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.kkrew.interceptor.LoginInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer{

	LoginInterceptor interceptor;
	
	public WebConfig(LoginInterceptor interceptor) {
		this.interceptor = interceptor;
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedOriginPatterns("*").allowedMethods("*");
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(interceptor).addPathPatterns("/**").excludePathPatterns("/user/**");
	}
}
