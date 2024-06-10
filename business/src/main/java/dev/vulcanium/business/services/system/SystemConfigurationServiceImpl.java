package dev.vulcanium.business.services.system;

import dev.vulcanium.business.exception.ServiceException;
import dev.vulcanium.business.model.system.SystemConfiguration;
import dev.vulcanium.business.repositories.system.SystemConfigurationRepository;
import dev.vulcanium.business.services.common.generic.SalesManagerEntityServiceImpl;
import jakarta.inject.Inject;
import org.springframework.stereotype.Service;

@Service("systemConfigurationService")
public class SystemConfigurationServiceImpl extends
		SalesManagerEntityServiceImpl<Long, SystemConfiguration> implements
		SystemConfigurationService {

	
	private SystemConfigurationRepository systemConfigurationReposotory;
	
	@Inject
	public SystemConfigurationServiceImpl(
			SystemConfigurationRepository systemConfigurationReposotory) {
			super(systemConfigurationReposotory);
			this.systemConfigurationReposotory = systemConfigurationReposotory;
	}
	
	public SystemConfiguration getByKey(String key) throws ServiceException {
		return systemConfigurationReposotory.findByKey(key);
	}
	



}
