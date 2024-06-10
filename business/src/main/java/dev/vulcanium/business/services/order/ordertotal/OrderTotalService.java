package dev.vulcanium.business.services.order.ordertotal;

import dev.vulcanium.business.model.customer.Customer;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.order.OrderSummary;
import dev.vulcanium.business.model.order.OrderTotalVariation;
import dev.vulcanium.business.model.reference.language.Language;

/**
 * Additional dynamic order total calculation
 * from the rules engine and other modules
 * @author carlsamson
 *
 */
public interface OrderTotalService {
	
	OrderTotalVariation findOrderTotalVariation(final OrderSummary summary, final Customer customer, final MerchantStore store, final Language language) throws Exception;

}
