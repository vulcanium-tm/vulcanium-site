package dev.vulcanium.business.services.tax;

import dev.vulcanium.business.exception.ServiceException;
import dev.vulcanium.business.model.customer.Customer;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.order.OrderSummary;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.business.model.tax.TaxConfiguration;
import dev.vulcanium.business.model.tax.TaxItem;
import java.util.List;


public interface TaxService   {

	/**
	 * Retrieves tax configurations (TaxConfiguration) for a given MerchantStore
	 * @param store
	 * @return
	 * @throws ServiceException
	 */
	TaxConfiguration getTaxConfiguration(MerchantStore store)
			throws ServiceException;

	/**
	 * Saves ShippingConfiguration to MerchantConfiguration table
	 * @param shippingConfiguration
	 * @param store
	 * @throws ServiceException
	 */
	void saveTaxConfiguration(TaxConfiguration shippingConfiguration,
			MerchantStore store) throws ServiceException;

	/**
	 * Calculates tax over an OrderSummary
	 * @param orderSummary
	 * @param customer
	 * @param store
	 * @param locale
	 * @return
	 * @throws ServiceException
	 */
	List<TaxItem> calculateTax(OrderSummary orderSummary, Customer customer,
			MerchantStore store, Language language) throws ServiceException;


}
