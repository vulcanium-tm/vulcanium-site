package dev.vulcanium.business.services.customer.attribute;

import dev.vulcanium.business.exception.ServiceException;
import dev.vulcanium.business.model.customer.Customer;
import dev.vulcanium.business.model.customer.attribute.CustomerAttribute;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.services.common.generic.SalesManagerEntityService;
import java.util.List;



public interface CustomerAttributeService extends
		SalesManagerEntityService<Long, CustomerAttribute> {

	void saveOrUpdate(CustomerAttribute customerAttribute)
			throws ServiceException;

	CustomerAttribute getByCustomerOptionId(MerchantStore store,
			Long customerId, Long id);

	List<CustomerAttribute> getByCustomerOptionValueId(MerchantStore store,
			Long id);

	List<CustomerAttribute> getByOptionId(MerchantStore store, Long id);


	List<CustomerAttribute> getByCustomer(MerchantStore store, Customer customer);
	

}
