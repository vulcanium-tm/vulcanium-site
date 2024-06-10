package dev.vulcanium.site.tech.application.config;

import dev.vulcanium.business.modules.cms.impl.VendorCacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Properties;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class ShopServletContextListener implements ServletContextListener {
private static final Logger logger = LoggerFactory.getLogger(ShopServletContextListener.class);
@Override
public void contextInitialized(ServletContextEvent servletContextEvent) {
	logger.info("===context init===");
	System.getenv().forEach((k, v) -> {
		logger.debug(k + ":" + v);
	});
	Properties props = System.getProperties();
	props.forEach((k, v) -> {
		logger.debug(k + ":" + v);
	});
}

@Override
public void contextDestroyed(ServletContextEvent servletContextEvent) {
	logger.info("===context destroy===");
	VendorCacheManager cacheManager = VendorCacheManager.getInstance();
}
}
