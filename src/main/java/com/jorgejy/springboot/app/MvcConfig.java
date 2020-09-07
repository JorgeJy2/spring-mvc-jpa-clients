package com.jorgejy.springboot.app;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig  implements WebMvcConfigurer{

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// WebMvcConfigurer.super.addResourceHandlers(registry);
		//registry.addResourceHandler("/upload/**").addResourceLocations("file:/home/jorge/spring-uploads/");
		
	}

	public void addViewControllers(ViewControllerRegistry controllerRegistry) {
		controllerRegistry.addViewController("/error_403").setViewName("error_403");
	}
	
}
