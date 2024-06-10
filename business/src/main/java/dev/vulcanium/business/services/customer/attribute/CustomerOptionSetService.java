package dev.vulcanium.business.services.customer.attribute;

import dev.vulcanium.business.exception.ServiceException;
import dev.vulcanium.business.model.customer.attribute.CustomerOption;
import dev.vulcanium.business.model.customer.attribute.CustomerOptionSet;
import dev.vulcanium.business.model.customer.attribute.CustomerOptionValue;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.business.services.common.generic.SalesManagerEntityService;
import java.util.List;


public interface CustomerOptionSetService extends SalesManagerEntityService<Long, CustomerOptionSet> {



	void saveOrUpdate(CustomerOptionSet entity) throws ServiceException;




	List<CustomerOptionSet> listByStore(MerchantStore store,
			Language language) throws ServiceException;




	List<CustomerOptionSet> listByOption(CustomerOption option,
			MerchantStore store) throws ServiceException;
	

	List<CustomerOptionSet> listByOptionValue(CustomerOptionValue optionValue,
			MerchantStore store) throws ServiceException;

}
