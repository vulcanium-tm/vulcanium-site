package dev.vulcanium.site.tech.data.init;

import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.system.MerchantConfig;
import dev.vulcanium.business.services.merchant.MerchantStoreService;
import dev.vulcanium.business.services.reference.init.InitializationDatabase;
import dev.vulcanium.business.services.system.MerchantConfigurationService;
import dev.vulcanium.business.services.system.SystemConfigurationService;
import dev.vulcanium.business.services.user.GroupService;
import dev.vulcanium.business.services.user.PermissionService;
import dev.vulcanium.business.utils.CoreConfiguration;
import dev.vulcanium.site.tech.admin.security.WebUserServices;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class InitializationLoader {

private static final Logger LOGGER = LoggerFactory.getLogger(InitializationLoader.class);

@Value("${db.init.data:true}")
private boolean initDefaultData;


@Inject
private MerchantConfigurationService merchantConfigurationService;

@Inject
private InitializationDatabase initializationDatabase;

//@Inject
//private InitData initData;

@Inject
private SystemConfigurationService systemConfigurationService;

@Inject
private WebUserServices userDetailsService;

@Inject
protected PermissionService  permissionService;

@Inject
protected GroupService   groupService;

@Inject
private CoreConfiguration configuration;

@Inject
protected MerchantStoreService merchantService;


@PostConstruct
public void init() {
	
	try {
		
		//Check flag to populate or not the database
		if(!this.initDefaultData) {
			return;
		}
		
		if (initializationDatabase.isEmpty()) {
			
			
			//All default data to be created
			
			LOGGER.info(String.format("%s : Shopizer database is empty, populate it....", "sm-shop"));
			
			initializationDatabase.populate("sm-shop");
			
			MerchantStore store = merchantService.getByCode(MerchantStore.DEFAULT_STORE);
			
			userDetailsService.createDefaultAdmin();
			MerchantConfig config = new MerchantConfig();
			config.setAllowPurchaseItems(true);
			config.setDisplayAddToCartOnFeaturedItems(true);
			
			merchantConfigurationService.saveMerchantConfig(config, store);
			
			
		}
		
	} catch (Exception e) {
		LOGGER.error("Error in the init method",e);
	}
	
}




}
