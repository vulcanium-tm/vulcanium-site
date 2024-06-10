package dev.vulcanium.business.configuration.db;

import dev.vulcanium.business.model.system.credentials.DbCredentials;
import jakarta.inject.Inject;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

public class DbConfig {
	
    @Inject Environment env;

    @Bean
    public DbCredentials dbCredentials() {
    	DbCredentials dbCredentials = new DbCredentials();
    	dbCredentials.setUserName(env.getProperty("user"));
    	dbCredentials.setPassword(env.getProperty("password"));
        return dbCredentials;
    }

}
