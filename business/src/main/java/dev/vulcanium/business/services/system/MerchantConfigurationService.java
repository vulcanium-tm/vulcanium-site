package dev.vulcanium.business.services.system;

import dev.vulcanium.business.exception.ServiceException;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.system.MerchantConfig;
import dev.vulcanium.business.model.system.MerchantConfiguration;
import dev.vulcanium.business.model.system.MerchantConfigurationType;
import dev.vulcanium.business.services.common.generic.SalesManagerEntityService;
import java.util.List;

public interface MerchantConfigurationService extends
		SalesManagerEntityService<Long, MerchantConfiguration> {
	
	MerchantConfiguration getMerchantConfiguration(String key, MerchantStore store) throws ServiceException;
	
	void saveOrUpdate(MerchantConfiguration entity) throws ServiceException;

	List<MerchantConfiguration> listByStore(MerchantStore store)
			throws ServiceException;

	List<MerchantConfiguration> listByType(MerchantConfigurationType type,
			MerchantStore store) throws ServiceException;

	MerchantConfig getMerchantConfig(MerchantStore store)
			throws ServiceException;

	void saveMerchantConfig(MerchantConfig config, MerchantStore store)
			throws ServiceException;

}
