package dev.vulcanium.business.services.shoppingcart;

import dev.vulcanium.business.exception.ServiceException;
import dev.vulcanium.business.model.catalog.product.Product;
import dev.vulcanium.business.model.customer.Customer;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.shipping.ShippingProduct;
import dev.vulcanium.business.model.shoppingcart.ShoppingCart;
import dev.vulcanium.business.model.shoppingcart.ShoppingCartItem;
import dev.vulcanium.business.services.common.generic.SalesManagerEntityService;
import java.util.List;

public interface ShoppingCartService extends SalesManagerEntityService<Long, ShoppingCart> {

	ShoppingCart getShoppingCart(Customer customer, MerchantStore store) throws ServiceException;

	void saveOrUpdate(ShoppingCart shoppingCart) throws ServiceException;

	ShoppingCart getById(Long id, MerchantStore store) throws ServiceException;

	ShoppingCart getByCode(String code, MerchantStore store) throws ServiceException;


	/**
	 * Creates a list of ShippingProduct based on the ShoppingCart if items are
	 * virtual return list will be null
	 * 
	 * @param cart
	 * @return
	 * @throws ServiceException
	 */
	List<ShippingProduct> createShippingProduct(ShoppingCart cart) throws ServiceException;

	/**
	 * Populates a ShoppingCartItem from a Product and attributes if any. Calculate price based on availability
	 * 
	 * @param product
	 * @return
	 * @throws ServiceException
	 */
	ShoppingCartItem populateShoppingCartItem(Product product, MerchantStore store) throws ServiceException;

	void deleteCart(ShoppingCart cart) throws ServiceException;

	void removeShoppingCart(ShoppingCart cart) throws ServiceException;

	/**
	 *
	 * @param userShoppingModel
	 * @param sessionCart
	 * @param store
	 * @return {@link ShoppingCart} merged Shopping Cart
	 * @throws Exception
	 */
	ShoppingCart mergeShoppingCarts(final ShoppingCart userShoppingCart, final ShoppingCart sessionCart,
			final MerchantStore store) throws Exception;


	/**
	 * Removes a shopping cart item
	 * @param item
	 */
	void deleteShoppingCartItem(Long id);

}