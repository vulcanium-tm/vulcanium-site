package dev.vulcanium.site.tech.application.config;

import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class PropertiesConfig {

@Bean(name = "properties")
public PropertiesFactoryBean mapper() {
	PropertiesFactoryBean bean = new PropertiesFactoryBean();
	bean.setLocation(new ClassPathResource("properties.properties"));
	return bean;
}
}
