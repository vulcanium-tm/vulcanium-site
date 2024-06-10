package dev.vulcanium.site.tech.store.facade.shoppingcart;

import java.util.List;
import java.util.Optional;
import dev.vulcanium.site.tech.model.customer.Customer;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.business.model.shoppingcart.ShoppingCart;
import dev.vulcanium.site.tech.model.shoppingcart.PersistableShoppingCartItem;
import dev.vulcanium.site.tech.model.shoppingcart.ReadableShoppingCart;
import dev.vulcanium.site.tech.model.shoppingcart.ShoppingCartData;
import dev.vulcanium.site.tech.model.shoppingcart.ShoppingCartItem;
import org.springframework.lang.Nullable;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.site.tech.model.shoppingcart.ReadableShoppingCart;
import org.springframework.lang.Nullable;

/**
 * </p>Shopping cart Facade which provide abstraction layer between
 * SM core module and Controller.
 * Only Data Object will be exposed to controller by hiding model
 * object from view.</p>
 */


public interface ShoppingCartFacade {

ReadableShoppingCart get(Optional<String> cart, Long customerId, MerchantStore store, Language language);

public ShoppingCartData addItemsToShoppingCart(ShoppingCartData shoppingCart,final ShoppingCartItem item, final MerchantStore store,final Language language,final Customer customer) throws Exception;
public ShoppingCart createCartModel(final String shoppingCartCode, final MerchantStore store,final Customer customer) throws Exception;
/**
 * Method responsible for getting shopping cart from
 * either session or from underlying DB.
 */
/**
 * @param supportPromoCode
 * @param customer
 * @param store
 * @param shoppingCartId
 * @param language
 * @return
 * @throws Exception
 */

void deleteShoppingCart(final Long id, final MerchantStore store) throws Exception;

ShoppingCart getShoppingCartModel(final String shoppingCartCode, MerchantStore store) throws Exception;
ShoppingCart getShoppingCartModel(Long id, MerchantStore store) throws Exception;ShoppingCart getShoppingCartModel(final Customer customer, MerchantStore store) throws Exception;

void deleteShoppingCart(String code, MerchantStore store) throws Exception;
void saveOrUpdateShoppingCart(ShoppingCart cart) throws Exception;

/**
 * Get ShoppingCart
 * This method is used by the API
 * @param customer
 * @param store
 * @param language
 * @return
 * @throws Exception
 */
ReadableShoppingCart getCart(Customer customer, MerchantStore store, Language language) throws Exception;

/**
 * Modify an item to an existing cart, quantity of line item will reflect item.getQuantity
 * @param cartCode
 * @param item
 * @param store
 * @param language
 * @return
 * @throws Exception
 */
ReadableShoppingCart modifyCart(String cartCode, PersistableShoppingCartItem item, MerchantStore store,
                                Language language) throws Exception;

/**
 * Adds a promo code / coupon code to an existing code
 * @param cartCode
 * @param promo
 * @param store
 * @param language
 * @return
 * @throws Exception
 */
ReadableShoppingCart modifyCart(String cartCode, String promo, MerchantStore store,
                                Language language) throws Exception;

/**
 * Modify a list of items to an existing cart, quantity of line item will reflect item.getQuantity
 * @param cartCode
 * @param items
 * @param store
 * @param language
 * @return
 * @throws Exception
 */
ReadableShoppingCart modifyCartMulti(String cartCode, List<PersistableShoppingCartItem> items, MerchantStore store,
                                     Language language) throws Exception;

/**
 * Add item to shopping cart
 * @param item
 * @param store
 * @param language
 */
ReadableShoppingCart addToCart(PersistableShoppingCartItem item, MerchantStore store,
                               Language language);

/**
 * Removes a shopping cart item
 * @param cartCode
 * @param sku
 * @param merchant
 * @param language
 * @param returnCart
 * @return ReadableShoppingCart or NULL
 * @throws Exception
 */
@Nullable
ReadableShoppingCart removeShoppingCartItem(String cartCode, String sku, MerchantStore merchant, Language language, boolean returnCart) throws Exception;

/**
 * Add product to ShoppingCart
 * This method is used by the API
 * @param customer
 * @param item
 * @param store
 * @param language
 * @return
 * @throws Exception
 */
ReadableShoppingCart addToCart(Customer customer, PersistableShoppingCartItem item, MerchantStore store, Language language) throws Exception;

/**
 * Retrieves a shopping cart by ID
 * @param shoppingCartId
 * @param store
 * @param language
 * @return
 * @throws Exception
 */
ReadableShoppingCart getById(Long shoppingCartId, MerchantStore store, Language language) throws Exception;

/**
 * Retrieves a shopping cart
 * @param code
 * @param store
 * @param language
 * @return
 * @throws Exception
 */
ReadableShoppingCart getByCode(String code, MerchantStore store, Language language) throws Exception;


/**
 * Set an order id to a shopping cart
 * @param code
 * @param orderId
 * @param store
 * @throws Exception
 */
void setOrderId(String code, Long orderId, MerchantStore store) throws Exception;


/**
 * Transform cart model to readable cart
 * @param cart
 * @param store
 * @param language
 * @return
 */
ReadableShoppingCart readableCart(ShoppingCart cart, MerchantStore store, Language language);

}
