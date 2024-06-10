package dev.vulcanium.business.services.system;

import dev.vulcanium.business.exception.ServiceException;
import dev.vulcanium.business.model.system.IntegrationModule;
import dev.vulcanium.business.services.common.generic.SalesManagerEntityService;
import java.util.List;

public interface ModuleConfigurationService extends
		SalesManagerEntityService<Long, IntegrationModule> {

	/**
	 * List all integration modules ready to be used by integrations such as payment and shipping
	 * @param module
	 * @return
	 */
	List<IntegrationModule> getIntegrationModules(String module);

	IntegrationModule getByCode(String moduleCode);
	
	void createOrUpdateModule(String json) throws ServiceException;
	


}
