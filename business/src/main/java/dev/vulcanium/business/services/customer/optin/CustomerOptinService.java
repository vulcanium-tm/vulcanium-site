package dev.vulcanium.business.services.customer.optin;

import dev.vulcanium.business.exception.ServiceException;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.system.optin.CustomerOptin;
import dev.vulcanium.business.services.common.generic.SalesManagerEntityService;

/**
 * Used for optin in customers
 * An implementation example is for signin in users
 * @author carlsamson
 *
 */
public interface CustomerOptinService extends SalesManagerEntityService<Long, CustomerOptin> {
	
	/**
	 * Optin a given customer. This has no reference to a specific Customer object but contains
	 * only email, first name and lastname
	 * @param optin
	 * @throws ServiceException
	 */
	void optinCumtomer(CustomerOptin optin) throws ServiceException;
	
	
	/**
	 * Removes a specific CustomerOptin
	 * @param optin
	 * @throws ServiceException
	 */
	void optoutCumtomer(CustomerOptin optin) throws ServiceException;
	
	/**
	 * Find an existing CustomerOptin
	 * @param store
	 * @param emailAddress
	 * @param code
	 * @return
	 * @throws ServiceException
	 */
	CustomerOptin findByEmailAddress(MerchantStore store, String emailAddress, String code) throws ServiceException;
	

}
