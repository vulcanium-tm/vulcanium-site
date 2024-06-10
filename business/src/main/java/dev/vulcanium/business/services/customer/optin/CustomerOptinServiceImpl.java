package dev.vulcanium.business.services.customer.optin;

import dev.vulcanium.business.exception.ServiceException;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.system.optin.CustomerOptin;
import dev.vulcanium.business.repositories.customer.optin.CustomerOptinRepository;
import dev.vulcanium.business.services.common.generic.SalesManagerEntityServiceImpl;
import jakarta.inject.Inject;
import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Service;


@Service
public class CustomerOptinServiceImpl extends SalesManagerEntityServiceImpl<Long, CustomerOptin> implements CustomerOptinService {
	
	
	private CustomerOptinRepository customerOptinRepository;
	
	
	@Inject
	public CustomerOptinServiceImpl(CustomerOptinRepository customerOptinRepository) {
		super(customerOptinRepository);
		this.customerOptinRepository = customerOptinRepository;
	}

	@Override
	public void optinCumtomer(CustomerOptin optin) throws ServiceException {
		Validate.notNull(optin,"CustomerOptin must not be null");
		
		customerOptinRepository.save(optin);
		

	}

	@Override
	public void optoutCumtomer(CustomerOptin optin) throws ServiceException {
		Validate.notNull(optin,"CustomerOptin must not be null");
		
		customerOptinRepository.delete(optin);

	}

	@Override
	public CustomerOptin findByEmailAddress(MerchantStore store, String emailAddress, String code) throws ServiceException {
		return customerOptinRepository.findByMerchantAndCodeAndEmail(store.getId(), code, emailAddress);
	}

}
