package dev.vulcanium.site.tech.store.facade.shoppingcart;

import dev.vulcanium.business.model.customer.Customer;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.business.model.shoppingcart.ShoppingCart;
import dev.vulcanium.business.services.customer.CustomerService;
import dev.vulcanium.business.services.shoppingcart.ShoppingCartService;
import dev.vulcanium.business.store.api.exception.ResourceNotFoundException;
import dev.vulcanium.business.store.api.exception.ServiceRuntimeException;
import dev.vulcanium.site.tech.model.shoppingcart.PersistableShoppingCartItem;
import dev.vulcanium.site.tech.model.shoppingcart.ReadableShoppingCart;
import dev.vulcanium.site.tech.model.shoppingcart.ShoppingCartData;
import dev.vulcanium.site.tech.model.shoppingcart.ShoppingCartItem;
import dev.vulcanium.site.tech.store.facade.customer.CustomerFacade;
import java.util.List;
import java.util.Optional;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("shoppingCartFacade")
public class ShoppingCartFacadeImpl implements ShoppingCartFacade {

@Autowired
private CustomerService customerService;

@Autowired
private ShoppingCartService shoppingCartService;

@Autowired
private CustomerFacade customerFacade;

@Autowired
private ShoppingCartFacade shoppingCartFacade; // legacy
// facade

@Override
public ReadableShoppingCart get(Optional<String> cart, Long customerId, MerchantStore store, Language language) {
	
	Validate.notNull(customerId, "Customer id cannot be null");
	Validate.notNull(store, "MerchantStore cannot be null");
	
	try {
		
		// lookup customer
		Customer customer = customerService.getById(customerId);
		
		if (customer == null) {
			throw new ResourceNotFoundException("No Customer found for id [" + customerId + "]");
		}
		
		ShoppingCart cartModel = shoppingCartService.getShoppingCart(customer, store);
		
		if(cart.isPresent()) {
			cartModel = customerFacade.mergeCart(customer, cart.get(), store, language);
		}
		
		if(cartModel == null) {
			return null;
		}
		
		ReadableShoppingCart readableCart = shoppingCartFacade.readableCart(cartModel, store, language);
		
		return readableCart;
		
	} catch (Exception e) {
		
		throw new ServiceRuntimeException(e);
		
	}
	
}

public ShoppingCartData addItemsToShoppingCart(ShoppingCartData shoppingCart, ShoppingCartItem item, MerchantStore store, Language language, dev.vulcanium.site.tech.model.customer.Customer customer) throws Exception{
	return null;
}

public ShoppingCart createCartModel(String shoppingCartCode, MerchantStore store, dev.vulcanium.site.tech.model.customer.Customer customer) throws Exception{
	return null;
}

public void deleteShoppingCart(Long id, MerchantStore store) throws Exception{

}

public ShoppingCart getShoppingCartModel(String shoppingCartCode, MerchantStore store) throws Exception{
	return null;
}

public ShoppingCart getShoppingCartModel(Long id, MerchantStore store) throws Exception{
	return null;
}

public ShoppingCart getShoppingCartModel(dev.vulcanium.site.tech.model.customer.Customer customer, MerchantStore store) throws Exception{
	return null;
}

public void deleteShoppingCart(String code, MerchantStore store) throws Exception{

}

public void saveOrUpdateShoppingCart(ShoppingCart cart) throws Exception{

}

public ReadableShoppingCart getCart(dev.vulcanium.site.tech.model.customer.Customer customer, MerchantStore store, Language language) throws Exception{
	return null;
}

public ReadableShoppingCart modifyCart(String cartCode, PersistableShoppingCartItem item, MerchantStore store, Language language) throws Exception{
	return null;
}

public ReadableShoppingCart modifyCart(String cartCode, String promo, MerchantStore store, Language language) throws Exception{
	return null;
}

public ReadableShoppingCart modifyCartMulti(String cartCode, List<PersistableShoppingCartItem> items, MerchantStore store, Language language) throws Exception{
	return null;
}

public ReadableShoppingCart addToCart(PersistableShoppingCartItem item, MerchantStore store, Language language){
	return null;
}

public ReadableShoppingCart removeShoppingCartItem(String cartCode, String sku, MerchantStore merchant, Language language, boolean returnCart) throws Exception{
	return null;
}

public ReadableShoppingCart addToCart(dev.vulcanium.site.tech.model.customer.Customer customer, PersistableShoppingCartItem item, MerchantStore store, Language language) throws Exception{
	return null;
}

public ReadableShoppingCart getById(Long shoppingCartId, MerchantStore store, Language language) throws Exception{
	return null;
}

public ReadableShoppingCart getByCode(String code, MerchantStore store, Language language) throws Exception{
	return null;
}

public void setOrderId(String code, Long orderId, MerchantStore store) throws Exception{

}

public ReadableShoppingCart readableCart(ShoppingCart cart, MerchantStore store, Language language){
	return null;
}

}
