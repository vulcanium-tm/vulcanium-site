package dev.vulcanium.business.services.customer.attribute;

import dev.vulcanium.business.exception.ServiceException;
import dev.vulcanium.business.model.customer.attribute.CustomerOptionValue;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.business.services.common.generic.SalesManagerEntityService;
import java.util.List;


public interface CustomerOptionValueService extends SalesManagerEntityService<Long, CustomerOptionValue> {



	List<CustomerOptionValue> listByStore(MerchantStore store, Language language)
			throws ServiceException;

	void saveOrUpdate(CustomerOptionValue entity) throws ServiceException;

	CustomerOptionValue getByCode(MerchantStore store, String optionValueCode);



}
