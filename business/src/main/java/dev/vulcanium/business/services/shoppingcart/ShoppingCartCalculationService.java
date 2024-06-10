/**
 *
 */
package dev.vulcanium.business.services.shoppingcart;

import dev.vulcanium.business.exception.ServiceException;
import dev.vulcanium.business.model.customer.Customer;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.order.OrderTotalSummary;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.business.model.shoppingcart.ShoppingCart;

/**
 * Interface declaring various methods used to calculate {@link ShoppingCart}
 * object details.
 * 
 * @author Umesh Awasthi
 * @since 1.2
 *
 */
public interface ShoppingCartCalculationService {
	/**
	 * Method which will be used to calculate price for each line items as well
	 * Total and Sub-total for {@link ShoppingCart}.
	 * 
	 * @param cartModel
	 *            ShoopingCart mode representing underlying DB object
	 * @param customer
	 * @param store
	 * @param language
	 * @throws ServiceException
	 */
	OrderTotalSummary calculate(final ShoppingCart cartModel, final Customer customer, final MerchantStore store,
			final Language language) throws ServiceException;

	/**
	 * Method which will be used to calculate price for each line items as well
	 * Total and Sub-total for {@link ShoppingCart}.
	 * 
	 * @param cartModel
	 *            ShoopingCart mode representing underlying DB object
	 * @param store
	 * @param language
	 * @throws ServiceException
	 */
	OrderTotalSummary calculate(final ShoppingCart cartModel, final MerchantStore store, final Language language)
			throws ServiceException;
}
