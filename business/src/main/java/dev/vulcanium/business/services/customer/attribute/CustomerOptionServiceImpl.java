package dev.vulcanium.business.services.customer.attribute;

import dev.vulcanium.business.exception.ServiceException;
import dev.vulcanium.business.model.customer.attribute.CustomerAttribute;
import dev.vulcanium.business.model.customer.attribute.CustomerOption;
import dev.vulcanium.business.model.customer.attribute.CustomerOptionSet;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.business.repositories.customer.attribute.CustomerOptionRepository;
import dev.vulcanium.business.services.common.generic.SalesManagerEntityServiceImpl;
import java.util.List;
import jakarta.inject.Inject;
import org.springframework.stereotype.Service;



@Service("customerOptionService")
public class CustomerOptionServiceImpl extends
		SalesManagerEntityServiceImpl<Long, CustomerOption> implements CustomerOptionService {

	
	private CustomerOptionRepository customerOptionRepository;
	
	@Inject
	private CustomerAttributeService customerAttributeService;
	
	@Inject
	private CustomerOptionSetService customerOptionSetService;
	

	@Inject
	public CustomerOptionServiceImpl(
			CustomerOptionRepository customerOptionRepository) {
			super(customerOptionRepository);
			this.customerOptionRepository = customerOptionRepository;
	}
	
	@Override
	public List<CustomerOption> listByStore(MerchantStore store, Language language) throws ServiceException {

		return customerOptionRepository.findByStore(store.getId(), language.getId());

	}
	

	@Override
	public void saveOrUpdate(CustomerOption entity) throws ServiceException {
		
		
		//save or update (persist and attach entities
		if(entity.getId()!=null && entity.getId()>0) {
			super.update(entity);
		} else {
			super.save(entity);
		}
		
	}


	@Override
	public void delete(CustomerOption customerOption) throws ServiceException {
		
		//remove all attributes having this option
		List<CustomerAttribute> attributes = customerAttributeService.getByOptionId(customerOption.getMerchantStore(), customerOption.getId());
		
		for(CustomerAttribute attribute : attributes) {
			customerAttributeService.delete(attribute);
		}
		
		CustomerOption option = this.getById(customerOption.getId());
		
		List<CustomerOptionSet> optionSets = customerOptionSetService.listByOption(customerOption, customerOption.getMerchantStore());
		
		for(CustomerOptionSet optionSet : optionSets) {
			customerOptionSetService.delete(optionSet);
		}
		
		//remove option
		super.delete(option);
		
	}
	
	@Override
	public CustomerOption getByCode(MerchantStore store, String optionCode) {
		return customerOptionRepository.findByCode(store.getId(), optionCode);
	}
	

	




}
