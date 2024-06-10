package dev.vulcanium.business.services.customer.attribute;

import dev.vulcanium.business.exception.ServiceException;
import dev.vulcanium.business.model.customer.attribute.CustomerOption;
import dev.vulcanium.business.model.customer.attribute.CustomerOptionSet;
import dev.vulcanium.business.model.customer.attribute.CustomerOptionValue;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.business.repositories.customer.attribute.CustomerOptionSetRepository;
import dev.vulcanium.business.services.common.generic.SalesManagerEntityServiceImpl;
import java.util.List;
import jakarta.inject.Inject;
import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Service;



@Service("customerOptionSetService")
public class CustomerOptionSetServiceImpl extends
		SalesManagerEntityServiceImpl<Long, CustomerOptionSet> implements CustomerOptionSetService {


	@Inject
	private CustomerOptionSetRepository customerOptionSetRepository;
	

	@Inject
	public CustomerOptionSetServiceImpl(
			CustomerOptionSetRepository customerOptionSetRepository) {
			super(customerOptionSetRepository);
			this.customerOptionSetRepository = customerOptionSetRepository;
	}
	

	@Override
	public List<CustomerOptionSet> listByOption(CustomerOption option, MerchantStore store) throws ServiceException {
		Validate.notNull(store,"merchant store cannot be null");
		Validate.notNull(option,"option cannot be null");
		
		return customerOptionSetRepository.findByOptionId(store.getId(), option.getId());
	}
	
	@Override
	public void delete(CustomerOptionSet customerOptionSet) throws ServiceException {
		customerOptionSet = customerOptionSetRepository.findOne(customerOptionSet.getId());
		super.delete(customerOptionSet);
	}
	
	@Override
	public List<CustomerOptionSet> listByStore(MerchantStore store, Language language) throws ServiceException {
		Validate.notNull(store,"merchant store cannot be null");

		
		return customerOptionSetRepository.findByStore(store.getId(),language.getId());
	}


	@Override
	public void saveOrUpdate(CustomerOptionSet entity) throws ServiceException {
		Validate.notNull(entity,"customer option set cannot be null");

		if(entity.getId()>0) {
			super.update(entity);
		} else {
			super.create(entity);
		}
		
	}


	@Override
	public List<CustomerOptionSet> listByOptionValue(
			CustomerOptionValue optionValue, MerchantStore store)
			throws ServiceException {
		return customerOptionSetRepository.findByOptionValueId(store.getId(), optionValue.getId());
	}


	




}
