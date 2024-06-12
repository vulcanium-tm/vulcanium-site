package dev.vulcanium.site.tech.populator.order;

import dev.vulcanium.business.exception.ConversionException;
import dev.vulcanium.business.exception.ServiceException;
import dev.vulcanium.business.model.catalog.product.Product;
import dev.vulcanium.business.model.catalog.product.image.ProductImage;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.order.orderproduct.OrderProduct;
import dev.vulcanium.business.model.order.orderproduct.OrderProductAttribute;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.business.services.catalog.pricing.PricingService;
import dev.vulcanium.business.services.catalog.product.ProductService;
import dev.vulcanium.business.utils.AbstractDataPopulator;
import dev.vulcanium.business.utils.ImageFilePath;
import dev.vulcanium.site.tech.model.catalog.product.ReadableProduct;
import dev.vulcanium.site.tech.model.order.ReadableOrderProduct;
import dev.vulcanium.site.tech.model.order.ReadableOrderProductAttribute;
import dev.vulcanium.site.tech.populator.catalog.ReadableProductPopulator;
import dev.vulcanium.site.tech.store.api.exception.ServiceRuntimeException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

/**
 * Use mappers
 */
@Deprecated
public class ReadableOrderProductPopulator extends
		AbstractDataPopulator<OrderProduct, ReadableOrderProduct> {

private ProductService productService;
private PricingService pricingService;
private ImageFilePath imageUtils;



public ImageFilePath getimageUtils() {
	return imageUtils;
}

public void setimageUtils(ImageFilePath imageUtils) {
	this.imageUtils = imageUtils;
}

@Override
public ReadableOrderProduct populate(OrderProduct source,
                                     ReadableOrderProduct target, MerchantStore store, Language language)
		throws ConversionException {
	
	Validate.notNull(productService,"Requires ProductService");
	Validate.notNull(pricingService,"Requires PricingService");
	Validate.notNull(imageUtils,"Requires imageUtils");
	target.setId(source.getId());
	target.setOrderedQuantity(source.getProductQuantity());
	try {
		target.setPrice(pricingService.getDisplayAmount(source.getOneTimeCharge(), store));
	} catch(Exception e) {
		throw new ConversionException("Cannot convert price",e);
	}
	target.setProductName(source.getProductName());
	target.setSku(source.getSku());
	
	BigDecimal subTotal = source.getOneTimeCharge();
	subTotal = subTotal.multiply(new BigDecimal(source.getProductQuantity()));
	
	try {
		String subTotalPrice = pricingService.getDisplayAmount(subTotal, store);
		target.setSubTotal(subTotalPrice);
	} catch(Exception e) {
		throw new ConversionException("Cannot format price",e);
	}
	
	if(source.getOrderAttributes()!=null) {
		List<ReadableOrderProductAttribute> attributes = new ArrayList<ReadableOrderProductAttribute>();
		for(OrderProductAttribute attr : source.getOrderAttributes()) {
			ReadableOrderProductAttribute readableAttribute = new ReadableOrderProductAttribute();
			try {
				String price = pricingService.getDisplayAmount(attr.getProductAttributePrice(), store);
				readableAttribute.setAttributePrice(price);
			} catch (ServiceException e) {
				throw new ConversionException("Cannot format price",e);
			}
			
			readableAttribute.setAttributeName(attr.getProductAttributeName());
			readableAttribute.setAttributeValue(attr.getProductAttributeValueName());
			attributes.add(readableAttribute);
		}
		target.setAttributes(attributes);
	}
	
	
	String productSku = source.getSku();
	if(!StringUtils.isBlank(productSku)) {
		Product product = null;
		try {
			product = productService.getBySku(productSku, store, language);
		} catch (ServiceException e) {
			throw new ServiceRuntimeException(e);
		}
		if(product!=null) {
			
			
			
			ReadableProductPopulator populator = new ReadableProductPopulator();
			populator.setPricingService(pricingService);
			populator.setimageUtils(imageUtils);
			
			ReadableProduct productProxy = populator.populate(product, new ReadableProduct(), store, language);
			target.setProduct(productProxy);
			
			Set<ProductImage> images = product.getImages();
			ProductImage defaultImage = null;
			if(images!=null) {
				for(ProductImage image : images) {
					if(defaultImage==null) {
						defaultImage = image;
					}
					if(image.isDefaultImage()) {
						defaultImage = image;
					}
				}
			}
			if(defaultImage!=null) {
				target.setImage(defaultImage.getProductImage());
			}
		}
	}
	
	
	return target;
}

@Override
protected ReadableOrderProduct createTarget() {
	
	return null;
}

public ProductService getProductService() {
	return productService;
}

public void setProductService(ProductService productService) {
	this.productService = productService;
}

public PricingService getPricingService() {
	return pricingService;
}

public void setPricingService(PricingService pricingService) {
	this.pricingService = pricingService;
}

}
