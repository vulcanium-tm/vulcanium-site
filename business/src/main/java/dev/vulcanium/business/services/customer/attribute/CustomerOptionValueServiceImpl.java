package dev.vulcanium.business.services.customer.attribute;

import dev.vulcanium.business.exception.ServiceException;
import dev.vulcanium.business.model.customer.attribute.CustomerAttribute;
import dev.vulcanium.business.model.customer.attribute.CustomerOptionSet;
import dev.vulcanium.business.model.customer.attribute.CustomerOptionValue;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.business.repositories.customer.attribute.CustomerOptionValueRepository;
import dev.vulcanium.business.services.common.generic.SalesManagerEntityServiceImpl;
import java.util.List;
import jakarta.inject.Inject;
import org.springframework.stereotype.Service;


@Service("customerOptionValueService")
public class CustomerOptionValueServiceImpl extends
		SalesManagerEntityServiceImpl<Long, CustomerOptionValue> implements
		CustomerOptionValueService {

	@Inject
	private CustomerAttributeService customerAttributeService;
	
	private CustomerOptionValueRepository customerOptionValueRepository;
	
	@Inject
	private CustomerOptionSetService customerOptionSetService;
	
	@Inject
	public CustomerOptionValueServiceImpl(
			CustomerOptionValueRepository customerOptionValueRepository) {
			super(customerOptionValueRepository);
			this.customerOptionValueRepository = customerOptionValueRepository;
	}
	
	
	@Override
	public List<CustomerOptionValue> listByStore(MerchantStore store, Language language) throws ServiceException {
		
		return customerOptionValueRepository.findByStore(store.getId(), language.getId());
	}
	


	
	@Override
	public void saveOrUpdate(CustomerOptionValue entity) throws ServiceException {
		
		
		//save or update (persist and attach entities
		if(entity.getId()!=null && entity.getId()>0) {

			super.update(entity);
			
		} else {
			
			super.save(entity);
			
		}
		
	}
	
	
	public void delete(CustomerOptionValue customerOptionValue) throws ServiceException {
		
		//remove all attributes having this option
		List<CustomerAttribute> attributes = customerAttributeService.getByCustomerOptionValueId(customerOptionValue.getMerchantStore(), customerOptionValue.getId());
		
		for(CustomerAttribute attribute : attributes) {
			customerAttributeService.delete(attribute);
		}
		
		List<CustomerOptionSet> optionSets = customerOptionSetService.listByOptionValue(customerOptionValue, customerOptionValue.getMerchantStore());
		
		for(CustomerOptionSet optionSet : optionSets) {
			customerOptionSetService.delete(optionSet);
		}
		
		CustomerOptionValue option = super.getById(customerOptionValue.getId());
		
		//remove option
		super.delete(option);
		
	}
	
	@Override
	public CustomerOptionValue getByCode(MerchantStore store, String optionValueCode) {
		return customerOptionValueRepository.findByCode(store.getId(), optionValueCode);
	}



}
