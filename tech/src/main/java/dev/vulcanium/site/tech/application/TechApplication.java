package dev.vulcanium.site.tech.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;


@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class TechApplication{

public static void main(String[] args) {
	SpringApplication.run(TechApplication.class, args);
}

}
