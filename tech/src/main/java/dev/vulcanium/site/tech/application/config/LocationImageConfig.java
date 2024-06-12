package dev.vulcanium.site.tech.application.config;

import dev.vulcanium.business.utils.CloudFilePathUtils;
import dev.vulcanium.business.utils.ImageFilePath;
import dev.vulcanium.business.utils.LocalImageFilePathUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LocationImageConfig {

@Value("${config.cms.contentUrl}")
private String contentUrl;

@Value("${config.cms.method}")
private String method;

@Value("${config.cms.static.path}")
private String staticPath;


@Bean
public ImageFilePath img() {
	
	if(!StringUtils.isEmpty(method) && !method.equals("default")) {
		CloudFilePathUtils cloudFilePathUtils = new CloudFilePathUtils();
		cloudFilePathUtils.setBasePath(contentUrl);
		cloudFilePathUtils.setContentUrlPath(contentUrl);
		return cloudFilePathUtils;
		
	} else {
		
		
		LocalImageFilePathUtils localImageFilePathUtils = new LocalImageFilePathUtils();
		localImageFilePathUtils.setBasePath(staticPath);
		localImageFilePathUtils.setContentUrlPath(contentUrl);
		return localImageFilePathUtils;
	}
	
	
}


}
