package dev.vulcanium.site.tech.populator.order;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.Validate;

import dev.vulcanium.business.exception.ConversionException;
import dev.vulcanium.business.services.catalog.product.ProductService;
import dev.vulcanium.business.services.catalog.product.attribute.ProductAttributeService;
import dev.vulcanium.business.services.catalog.product.file.DigitalProductService;
import dev.vulcanium.business.utils.AbstractDataPopulator;
import dev.vulcanium.business.model.catalog.product.Product;
import dev.vulcanium.business.model.catalog.product.attribute.ProductAttribute;
import dev.vulcanium.business.model.catalog.product.file.DigitalProduct;
import dev.vulcanium.business.model.catalog.product.price.FinalPrice;
import dev.vulcanium.business.model.catalog.product.price.ProductPrice;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.order.orderproduct.OrderProduct;
import dev.vulcanium.business.model.order.orderproduct.OrderProductAttribute;
import dev.vulcanium.business.model.order.orderproduct.OrderProductDownload;
import dev.vulcanium.business.model.order.orderproduct.OrderProductPrice;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.business.model.shoppingcart.ShoppingCartAttributeItem;
import dev.vulcanium.business.model.shoppingcart.ShoppingCartItem;
import dev.vulcanium.business.constants.ApplicationConstants;

public class OrderProductPopulator extends
		AbstractDataPopulator<ShoppingCartItem, OrderProduct> {

private ProductService productService;
private DigitalProductService digitalProductService;
private ProductAttributeService productAttributeService;


public ProductAttributeService getProductAttributeService() {
	return productAttributeService;
}

public void setProductAttributeService(
		ProductAttributeService productAttributeService) {
	this.productAttributeService = productAttributeService;
}

public DigitalProductService getDigitalProductService() {
	return digitalProductService;
}

public void setDigitalProductService(DigitalProductService digitalProductService) {
	this.digitalProductService = digitalProductService;
}

/**
 * Converts a ShoppingCartItem carried in the ShoppingCart to an OrderProduct
 * that will be saved in the system
 */
@Override
public OrderProduct populate(ShoppingCartItem source, OrderProduct target,
                             MerchantStore store, Language language) throws ConversionException {
	
	Validate.notNull(productService,"productService must be set");
	Validate.notNull(digitalProductService,"digitalProductService must be set");
	Validate.notNull(productAttributeService,"productAttributeService must be set");
	
	
	try {
		Product modelProduct = productService.getBySku(source.getSku(), store, language);
		if(modelProduct==null) {
			throw new ConversionException("Cannot get product with sku " + source.getSku());
		}
		
		if(modelProduct.getMerchantStore().getId().intValue()!=store.getId().intValue()) {
			throw new ConversionException("Invalid product with sku " + source.getSku());
		}
		
		DigitalProduct digitalProduct = digitalProductService.getByProduct(store, modelProduct);
		
		if(digitalProduct!=null) {
			OrderProductDownload orderProductDownload = new OrderProductDownload();
			orderProductDownload.setOrderProductFilename(digitalProduct.getProductFileName());
			orderProductDownload.setOrderProduct(target);
			orderProductDownload.setDownloadCount(0);
			orderProductDownload.setMaxdays(ApplicationConstants.MAX_DOWNLOAD_DAYS);
			target.getDownloads().add(orderProductDownload);
		}
		
		target.setOneTimeCharge(source.getItemPrice());
		target.setProductName(source.getProduct().getDescriptions().iterator().next().getName());
		target.setProductQuantity(source.getQuantity());
		target.setSku(source.getProduct().getSku());
		
		FinalPrice finalPrice = source.getFinalPrice();
		if(finalPrice==null) {
			throw new ConversionException("Object final price not populated in shoppingCartItem (source)");
		}
		OrderProductPrice orderProductPrice = orderProductPrice(finalPrice);
		orderProductPrice.setOrderProduct(target);
		
		Set<OrderProductPrice> prices = new HashSet<OrderProductPrice>();
		prices.add(orderProductPrice);
		
		List<FinalPrice> otherPrices = finalPrice.getAdditionalPrices();
		if(otherPrices!=null) {
			for(FinalPrice otherPrice : otherPrices) {
				OrderProductPrice other = orderProductPrice(otherPrice);
				other.setOrderProduct(target);
				prices.add(other);
			}
		}
		
		target.setPrices(prices);
		
		Set<ShoppingCartAttributeItem> attributeItems = source.getAttributes();
		if(!CollectionUtils.isEmpty(attributeItems)) {
			Set<OrderProductAttribute> attributes = new HashSet<OrderProductAttribute>();
			for(ShoppingCartAttributeItem attribute : attributeItems) {
				OrderProductAttribute orderProductAttribute = new OrderProductAttribute();
				orderProductAttribute.setOrderProduct(target);
				Long id = attribute.getProductAttributeId();
				ProductAttribute attr = productAttributeService.getById(id);
				if(attr==null) {
					throw new ConversionException("Attribute id " + id + " does not exists");
				}
				
				if(attr.getProduct().getMerchantStore().getId().intValue()!=store.getId().intValue()) {
					throw new ConversionException("Attribute id " + id + " invalid for this store");
				}
				
				orderProductAttribute.setProductAttributeIsFree(attr.getProductAttributeIsFree());
				orderProductAttribute.setProductAttributeName(attr.getProductOption().getDescriptionsSettoList().get(0).getName());
				orderProductAttribute.setProductAttributeValueName(attr.getProductOptionValue().getDescriptionsSettoList().get(0).getName());
				orderProductAttribute.setProductAttributePrice(attr.getProductAttributePrice());
				orderProductAttribute.setProductAttributeWeight(attr.getProductAttributeWeight());
				orderProductAttribute.setProductOptionId(attr.getProductOption().getId());
				orderProductAttribute.setProductOptionValueId(attr.getProductOptionValue().getId());
				attributes.add(orderProductAttribute);
			}
			target.setOrderAttributes(attributes);
		}
		
		
	} catch (Exception e) {
		throw new ConversionException(e);
	}
	
	
	return target;
}

@Override
protected OrderProduct createTarget() {
	return null;
}

public void setProductService(ProductService productService) {
	this.productService = productService;
}

public ProductService getProductService() {
	return productService;
}

private OrderProductPrice orderProductPrice(FinalPrice price) {
	
	OrderProductPrice orderProductPrice = new OrderProductPrice();
	
	ProductPrice productPrice = price.getProductPrice();
	
	orderProductPrice.setDefaultPrice(productPrice.isDefaultPrice());
	
	orderProductPrice.setProductPrice(price.getFinalPrice());
	orderProductPrice.setProductPriceCode(productPrice.getCode());
	if(productPrice.getDescriptions()!=null && productPrice.getDescriptions().size()>0) {
		orderProductPrice.setProductPriceName(productPrice.getDescriptions().iterator().next().getName());
	}
	if(price.isDiscounted()) {
		orderProductPrice.setProductPriceSpecial(productPrice.getProductPriceSpecialAmount());
		orderProductPrice.setProductPriceSpecialStartDate(productPrice.getProductPriceSpecialStartDate());
		orderProductPrice.setProductPriceSpecialEndDate(productPrice.getProductPriceSpecialEndDate());
	}
	
	return orderProductPrice;
}


}
