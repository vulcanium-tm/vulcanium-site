package dev.vulcanium.business.services.system.optin;

import dev.vulcanium.business.exception.ServiceException;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.system.optin.Optin;
import dev.vulcanium.business.model.system.optin.OptinType;
import dev.vulcanium.business.repositories.system.OptinRepository;
import dev.vulcanium.business.services.common.generic.SalesManagerEntityServiceImpl;
import jakarta.inject.Inject;
import org.springframework.stereotype.Service;

@Service
public class OptinServiceImpl extends SalesManagerEntityServiceImpl<Long, Optin> implements OptinService {
	
	
	private OptinRepository optinRepository;
	
	@Inject
	public OptinServiceImpl(OptinRepository optinRepository) {
		super(optinRepository);
		this.optinRepository = optinRepository;
	}


	@Override
	public Optin getOptinByCode(MerchantStore store, String code) throws ServiceException {
		return optinRepository.findByMerchantAndCode(store.getId(), code);
	}

	@Override
	public Optin getOptinByMerchantAndType(MerchantStore store, OptinType type) throws ServiceException {
		return optinRepository.findByMerchantAndType(store.getId(), type);
	}

}
