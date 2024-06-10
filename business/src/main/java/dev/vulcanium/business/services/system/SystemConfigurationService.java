package dev.vulcanium.business.services.system;

import dev.vulcanium.business.exception.ServiceException;
import dev.vulcanium.business.model.system.SystemConfiguration;
import dev.vulcanium.business.services.common.generic.SalesManagerEntityService;

public interface SystemConfigurationService extends
		SalesManagerEntityService<Long, SystemConfiguration> {
	
	SystemConfiguration getByKey(String key) throws ServiceException;

}
