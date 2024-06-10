package dev.vulcanium.business.modules.order.total;

import dev.vulcanium.business.model.catalog.product.Product;
import dev.vulcanium.business.model.customer.Customer;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.order.OrderSummary;
import dev.vulcanium.business.model.order.OrderTotal;
import dev.vulcanium.business.model.shoppingcart.ShoppingCartItem;
import dev.vulcanium.business.modules.Module;

/**
 * Calculates order total based on specific
 * modules implementation
 * @author carlsamson
 *
 */
public interface OrderTotalPostProcessorModule extends Module {

/**
 * Uses the OrderSummary and external tools for applying if necessary
 * variations on the OrderTotal calculation.
 * @param summary OrderSummary
 * @param shoppingCartItem ShoppingCartItem
 * @param product Product
 * @param customer Customer
 * @param store MerchantStore
 * @return OrderTotal OrderTotal
 * @throws Exception Exception
 */
OrderTotal caculateProductPiceVariation(final OrderSummary summary, final ShoppingCartItem shoppingCartItem, final Product product, final Customer customer, final MerchantStore store) throws Exception;

}
