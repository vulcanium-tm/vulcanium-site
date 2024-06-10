package dev.vulcanium.site.tech.populator.order;

import org.apache.commons.lang3.Validate;

import dev.vulcanium.business.model.catalog.product.Product;
import dev.vulcanium.business.model.catalog.product.attribute.ProductAttribute;
import dev.vulcanium.business.services.catalog.product.ProductService;
import dev.vulcanium.business.services.catalog.product.attribute.ProductAttributeService;
import dev.vulcanium.business.exception.ConversionException;
import dev.vulcanium.business.exception.ServiceException;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.business.model.shoppingcart.ShoppingCartItem;
import dev.vulcanium.business.services.shoppingcart.ShoppingCartService;
import dev.vulcanium.business.utils.AbstractDataPopulator;
import dev.vulcanium.site.tech.model.order.PersistableOrderProduct;
import dev.vulcanium.site.tech.store.api.exception.ResourceNotFoundException;
import dev.vulcanium.site.tech.store.api.exception.ServiceRuntimeException;

public class ShoppingCartItemPopulator extends
		AbstractDataPopulator<PersistableOrderProduct, ShoppingCartItem> {


private ProductService productService;
private ProductAttributeService productAttributeService;
private ShoppingCartService shoppingCartService;

@Override
public ShoppingCartItem populate(PersistableOrderProduct source, ShoppingCartItem target,
                                 MerchantStore store, Language language)
		throws ConversionException {
	Validate.notNull(productService, "Requires to set productService");
	Validate.notNull(productAttributeService, "Requires to set productAttributeService");
	Validate.notNull(shoppingCartService, "Requires to set shoppingCartService");
	
	Product product = null;
	try {
		product = productService.getBySku(source.getSku(), store, language);
	} catch (ServiceException e) {
		throw new ServiceRuntimeException(e);
	}
	if(product==null ) {
		throw new ResourceNotFoundException("No product found for sku [" + source.getSku() +"]");
	}
	
	if(source.getAttributes()!=null) {
		
		for(dev.vulcanium.business.model.catalog.product.attribute.ProductAttribute attr : source.getAttributes()) {
			ProductAttribute attribute = productAttributeService.getById(attr.getId());
			if(attribute==null) {
				throw new ConversionException("ProductAttribute with id " + attr.getId() + " is null");
			}
			if(attribute.getProduct().getId().longValue()!=source.getProduct().getId().longValue()) {
				throw new ConversionException("ProductAttribute with id " + attr.getId() + " is not assigned to Product id " + source.getProduct().getId());
			}
			product.getAttributes().add(attribute);
		}
	}
	
	try {
		return shoppingCartService.populateShoppingCartItem(product, store);
	} catch (ServiceException e) {
		throw new ConversionException(e);
	}
	
}

@Override
protected ShoppingCartItem createTarget() {
	return null;
}

public void setProductAttributeService(ProductAttributeService productAttributeService) {
	this.productAttributeService = productAttributeService;
}

public ProductAttributeService getProductAttributeService() {
	return productAttributeService;
}

public void setProductService(ProductService productService) {
	this.productService = productService;
}

public ProductService getProductService() {
	return productService;
}

public void setShoppingCartService(ShoppingCartService shoppingCartService) {
	this.shoppingCartService = shoppingCartService;
}

public ShoppingCartService getShoppingCartService() {
	return shoppingCartService;
}

}
