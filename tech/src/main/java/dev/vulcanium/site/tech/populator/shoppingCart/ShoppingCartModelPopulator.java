package dev.vulcanium.site.tech.populator.shoppingCart;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import dev.vulcanium.business.exception.ServiceException;
import dev.vulcanium.business.services.catalog.product.ProductService;
import dev.vulcanium.business.services.catalog.product.attribute.ProductAttributeService;
import dev.vulcanium.business.services.shoppingcart.ShoppingCartService;
import dev.vulcanium.business.utils.AbstractDataPopulator;
import dev.vulcanium.business.model.catalog.product.Product;
import dev.vulcanium.business.model.catalog.product.attribute.ProductAttribute;
import dev.vulcanium.business.model.customer.Customer;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.business.model.shoppingcart.ShoppingCart;
import dev.vulcanium.site.tech.model.shoppingcart.ShoppingCartAttribute;
import dev.vulcanium.site.tech.model.shoppingcart.ShoppingCartData;
import dev.vulcanium.site.tech.model.shoppingcart.ShoppingCartItem;

@Service(value="shoppingCartModelPopulator")
public class ShoppingCartModelPopulator
		extends AbstractDataPopulator<ShoppingCartData,ShoppingCart>
{

private static final Logger LOG = LoggerFactory.getLogger(ShoppingCartModelPopulator.class);

private ShoppingCartService shoppingCartService;

private Customer customer;

public ShoppingCartService getShoppingCartService() {
	return shoppingCartService;
}


public void setShoppingCartService(ShoppingCartService shoppingCartService) {
	this.shoppingCartService = shoppingCartService;
}


private ProductService productService;


public ProductService getProductService() {
	return productService;
}


public void setProductService(ProductService productService) {
	this.productService = productService;
}


private ProductAttributeService productAttributeService;


public ProductAttributeService getProductAttributeService() {
	return productAttributeService;
}


public void setProductAttributeService(
		ProductAttributeService productAttributeService) {
	this.productAttributeService = productAttributeService;
}


@Override
public ShoppingCart populate(ShoppingCartData shoppingCart,ShoppingCart cartMdel,final MerchantStore store, Language language)
{
	
	try{
		if ( shoppingCart.getId() > 0  && StringUtils.isNotBlank( shoppingCart.getCode()))
		{
			cartMdel = shoppingCartService.getByCode( shoppingCart.getCode(), store );
			if(cartMdel==null){
				cartMdel=new ShoppingCart();
				cartMdel.setShoppingCartCode( shoppingCart.getCode() );
				cartMdel.setMerchantStore( store );
				if ( customer != null )
				{
					cartMdel.setCustomerId( customer.getId() );
				}
				shoppingCartService.create( cartMdel );
			}
		}
		else
		{
			cartMdel.setShoppingCartCode( shoppingCart.getCode() );
			cartMdel.setMerchantStore( store );
			if ( customer != null )
			{
				cartMdel.setCustomerId( customer.getId() );
			}
			shoppingCartService.create( cartMdel );
		}
		
		List<ShoppingCartItem> items = shoppingCart.getShoppingCartItems();
		Set<dev.vulcanium.business.model.shoppingcart.ShoppingCartItem> newItems =
				new HashSet<dev.vulcanium.business.model.shoppingcart.ShoppingCartItem>();
		if ( items != null && items.size() > 0 ){
			for (ShoppingCartItem item : items){
				
				Set<dev.vulcanium.business.model.shoppingcart.ShoppingCartItem> cartItems = cartMdel.getLineItems();
				if (cartItems!=null && cartItems.size()>0){
					
					for (dev.vulcanium.business.model.shoppingcart.ShoppingCartItem dbItem : cartItems){
						if (dbItem.getId().longValue()==item.getId()){
							dbItem.setQuantity(item.getQuantity());
							Set<dev.vulcanium.business.model.shoppingcart.ShoppingCartAttributeItem> attributes = dbItem.getAttributes();
							Set<dev.vulcanium.business.model.shoppingcart.ShoppingCartAttributeItem> newAttributes = new HashSet<dev.vulcanium.business.model.shoppingcart.ShoppingCartAttributeItem>();
							List<ShoppingCartAttribute> cartAttributes = item.getShoppingCartAttributes();
							if (!CollectionUtils.isEmpty(cartAttributes)){
								for (ShoppingCartAttribute attribute : cartAttributes){
									for (dev.vulcanium.business.model.shoppingcart.ShoppingCartAttributeItem dbAttribute : attributes){
										if (dbAttribute.getId().longValue()==attribute.getId()){
											newAttributes.add(dbAttribute);
										}
									}
								}
								
								dbItem.setAttributes(newAttributes);
							}
							else{
								dbItem.removeAllAttributes();
							}
							newItems.add(dbItem);
						}
					}
				}
				else{
					dev.vulcanium.business.model.shoppingcart.ShoppingCartItem cartItem = createCartItem(cartMdel, item, store);
					Set<dev.vulcanium.business.model.shoppingcart.ShoppingCartItem> lineItems = cartMdel.getLineItems();
					if (lineItems==null){
						lineItems = new HashSet<>();
						cartMdel.setLineItems(lineItems);
					}
					lineItems.add(cartItem);
					shoppingCartService.update(cartMdel);
				}
			}
		}
	}catch(ServiceException se){
		LOG.error( "Error while converting cart data to cart model.."+se );
		throw new ConversionException( "Unable to create cart model", se );
	}
	
	return cartMdel;
}


private dev.vulcanium.business.model.shoppingcart.ShoppingCartItem createCartItem(
		dev.vulcanium.business.model.shoppingcart.ShoppingCart cart,
		ShoppingCartItem shoppingCartItem,
		MerchantStore store ) throws Exception
{
	
	Product product = productService.getBySku(shoppingCartItem.getSku(), store, store.getDefaultLanguage());
	if ( product == null )
	{
		throw new Exception( "Item with sku " + shoppingCartItem.getSku() + " does not exist" );
	}
	
	if ( product.getMerchantStore().getId().intValue() != store.getId().intValue() )
	{
		throw new Exception( "Item with sku " + shoppingCartItem.getSku() + " does not belong to merchant "
				                     + store.getId() );
	}
	
	dev.vulcanium.business.model.shoppingcart.ShoppingCartItem item =
			new dev.vulcanium.business.model.shoppingcart.ShoppingCartItem( cart, product );
	item.setQuantity( shoppingCartItem.getQuantity() );
	item.setItemPrice( shoppingCartItem.getProductPrice() );
	item.setShoppingCart( cart );
	item.setSku(shoppingCartItem.getSku());
	
	List<ShoppingCartAttribute> cartAttributes = shoppingCartItem.getShoppingCartAttributes();
	if ( !CollectionUtils.isEmpty( cartAttributes ) )
	{
		Set<dev.vulcanium.business.model.shoppingcart.ShoppingCartAttributeItem> newAttributes =
				new HashSet<dev.vulcanium.business.model.shoppingcart.ShoppingCartAttributeItem>();
		for ( ShoppingCartAttribute attribute : cartAttributes )
		{
			ProductAttribute productAttribute = productAttributeService.getById( attribute.getAttributeId() );
			if ( productAttribute != null
					     && productAttribute.getProduct().getId().longValue() == product.getId().longValue() )
			{
				dev.vulcanium.business.model.shoppingcart.ShoppingCartAttributeItem attributeItem =
						new dev.vulcanium.business.model.shoppingcart.ShoppingCartAttributeItem( item,
								productAttribute );
				if ( attribute.getAttributeId() > 0 )
				{
					attributeItem.setId( attribute.getId() );
				}
				item.addAttributes( attributeItem );
			}
			
		}
	}
	
	return item;
	
}




@Override
protected ShoppingCart createTarget()
{
	
	return new ShoppingCart();
}


public Customer getCustomer() {
	return customer;
}


public void setCustomer(Customer customer) {
	this.customer = customer;
}

}
