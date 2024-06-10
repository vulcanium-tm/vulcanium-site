package dev.vulcanium.business.configuration;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan({"dev.vulcanium.business"})
@EnableAutoConfiguration
@EnableConfigurationProperties(ApplicationSearchConfiguration.class)
@EnableJpaRepositories(basePackages = "dev.vulcanium.business.repositories")
@EntityScan(basePackages = "dev.vulcanium.business.model")
@EnableTransactionManagement
@ImportResource("classpath:/spring/shop-core-context.xml")
public class CoreApplicationConfiguration {



}
