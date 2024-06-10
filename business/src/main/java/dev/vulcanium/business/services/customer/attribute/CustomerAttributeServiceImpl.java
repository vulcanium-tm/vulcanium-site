package dev.vulcanium.business.services.customer.attribute;

import dev.vulcanium.business.exception.ServiceException;
import dev.vulcanium.business.model.customer.Customer;
import dev.vulcanium.business.model.customer.attribute.CustomerAttribute;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.repositories.customer.attribute.CustomerAttributeRepository;
import dev.vulcanium.business.services.common.generic.SalesManagerEntityServiceImpl;
import java.util.List;
import jakarta.inject.Inject;
import org.springframework.stereotype.Service;



@Service("customerAttributeService")
public class CustomerAttributeServiceImpl extends
		SalesManagerEntityServiceImpl<Long, CustomerAttribute> implements CustomerAttributeService {
	
	private CustomerAttributeRepository customerAttributeRepository;

	@Inject
	public CustomerAttributeServiceImpl(CustomerAttributeRepository customerAttributeRepository) {
		super(customerAttributeRepository);
		this.customerAttributeRepository = customerAttributeRepository;
	}
	




	@Override
	public void saveOrUpdate(CustomerAttribute customerAttribute)
			throws ServiceException {

			customerAttributeRepository.save(customerAttribute);

		
	}
	
	@Override
	public void delete(CustomerAttribute attribute) throws ServiceException {
		
		//override method, this allows the error that we try to remove a detached instance
		attribute = this.getById(attribute.getId());
		super.delete(attribute);
		
	}
	


	@Override
	public CustomerAttribute getByCustomerOptionId(MerchantStore store, Long customerId, Long id) {
		return customerAttributeRepository.findByOptionId(store.getId(), customerId, id);
	}



	@Override
	public List<CustomerAttribute> getByCustomer(MerchantStore store, Customer customer) {
		return customerAttributeRepository.findByCustomerId(store.getId(), customer.getId());
	}


	@Override
	public List<CustomerAttribute> getByCustomerOptionValueId(MerchantStore store,
			Long id) {
		return customerAttributeRepository.findByOptionValueId(store.getId(), id);
	}
	
	@Override
	public List<CustomerAttribute> getByOptionId(MerchantStore store,
			Long id) {
		return customerAttributeRepository.findByOptionId(store.getId(), id);
	}

}
