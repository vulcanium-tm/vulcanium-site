package dev.vulcanium.business.configuration;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import modules.commons.search.configuration.Credentials;
import modules.commons.search.configuration.SearchHost;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Reads search related properties required for search starter
 */


@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "search")
@PropertySource("classpath:shop-core.properties")
public class ApplicationSearchConfiguration {
	
    private String clusterName;
    private Credentials credentials;
    private List<SearchHost> host;
    private List<String> searchLanguages;
	
	
}
