package dev.vulcanium.business.repositories.customer;

import dev.vulcanium.business.model.customer.CustomerCriteria;
import dev.vulcanium.business.model.customer.CustomerList;
import dev.vulcanium.business.model.merchant.MerchantStore;



public interface CustomerRepositoryCustom {

	CustomerList listByStore(MerchantStore store, CustomerCriteria criteria);
	

}
