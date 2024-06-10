package dev.vulcanium.business.services.customer;

import dev.vulcanium.business.exception.ServiceException;
import dev.vulcanium.business.model.common.Address;
import dev.vulcanium.business.model.customer.Customer;
import dev.vulcanium.business.model.customer.CustomerCriteria;
import dev.vulcanium.business.model.customer.CustomerList;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.services.common.generic.SalesManagerEntityService;
import java.util.List;



public interface CustomerService  extends SalesManagerEntityService<Long, Customer> {

	List<Customer> getByName(String firstName);

	List<Customer> getListByStore(MerchantStore store);

	Customer getByNick(String nick);

	void saveOrUpdate(Customer customer) throws ServiceException ;

	CustomerList getListByStore(MerchantStore store, CustomerCriteria criteria);

	Customer getByNick(String nick, int storeId);
	Customer getByNick(String nick, String code);
	
	/**
	 * Password reset token
	 * @param storeCode
	 * @param token
	 * @return
	 */
	Customer getByPasswordResetToken(String storeCode, String token);

	/**
	 * Return an {@link dev.vulcanium.business.common.model.Address} object from the client IP address. Uses underlying GeoLocation module
	 * @param store
	 * @param ipAddress
	 * @return
	 * @throws ServiceException
	 */
	Address getCustomerAddress(MerchantStore store, String ipAddress)
			throws ServiceException;


}
