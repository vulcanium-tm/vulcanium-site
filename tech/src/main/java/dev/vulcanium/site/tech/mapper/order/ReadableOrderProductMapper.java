package dev.vulcanium.site.tech.mapper.order;

import dev.vulcanium.business.exception.ServiceException;
import dev.vulcanium.business.model.catalog.product.Product;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.order.orderproduct.OrderProduct;
import dev.vulcanium.business.model.order.orderproduct.OrderProductAttribute;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.business.services.catalog.pricing.PricingService;
import dev.vulcanium.business.services.catalog.product.ProductService;
import dev.vulcanium.business.utils.ImageFilePath;
import dev.vulcanium.site.tech.mapper.Mapper;
import dev.vulcanium.site.tech.mapper.catalog.product.ReadableProductMapper;
import dev.vulcanium.site.tech.model.catalog.product.ReadableProduct;
import dev.vulcanium.site.tech.model.order.ReadableOrderProduct;
import dev.vulcanium.site.tech.model.order.ReadableOrderProductAttribute;
import dev.vulcanium.site.tech.store.api.exception.ConversionRuntimeException;
import dev.vulcanium.site.tech.store.api.exception.ServiceRuntimeException;
import jakarta.inject.Inject;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class ReadableOrderProductMapper implements Mapper<OrderProduct, ReadableOrderProduct> {

@Autowired
PricingService pricingService;

@Autowired
ProductService productService;

@Autowired
ReadableProductMapper readableProductMapper;

@Inject
@Qualifier("img")
private ImageFilePath imageUtils;

@Override
public ReadableOrderProduct convert(OrderProduct source, MerchantStore store, Language language) {
	ReadableOrderProduct orderProduct = new ReadableOrderProduct();
	return this.merge(source, orderProduct, store, language);
}

@Override
public ReadableOrderProduct merge(OrderProduct source, ReadableOrderProduct target, MerchantStore store,
                                  Language language) {
	
	Validate.notNull(source, "OrderProduct cannot be null");
	Validate.notNull(target, "ReadableOrderProduct cannot be null");
	Validate.notNull(store, "MerchantStore cannot be null");
	Validate.notNull(language, "Language cannot be null");
	
	target.setId(source.getId());
	target.setOrderedQuantity(source.getProductQuantity());
	try {
		target.setPrice(pricingService.getDisplayAmount(source.getOneTimeCharge(), store));
	} catch (Exception e) {
		throw new ConversionRuntimeException("Cannot convert price", e);
	}
	target.setProductName(source.getProductName());
	target.setSku(source.getSku());
	
	// subtotal = price * quantity
	BigDecimal subTotal = source.getOneTimeCharge();
	subTotal = subTotal.multiply(new BigDecimal(source.getProductQuantity()));
	
	try {
		String subTotalPrice = pricingService.getDisplayAmount(subTotal, store);
		target.setSubTotal(subTotalPrice);
	} catch (Exception e) {
		throw new ConversionRuntimeException("Cannot format price", e);
	}
	
	if (source.getOrderAttributes() != null) {
		List<ReadableOrderProductAttribute> attributes = new ArrayList<ReadableOrderProductAttribute>();
		for (OrderProductAttribute attr : source.getOrderAttributes()) {
			ReadableOrderProductAttribute readableAttribute = new ReadableOrderProductAttribute();
			try {
				String price = pricingService.getDisplayAmount(attr.getProductAttributePrice(), store);
				readableAttribute.setAttributePrice(price);
			} catch (ServiceException e) {
				throw new ConversionRuntimeException("Cannot format price", e);
			}
			
			readableAttribute.setAttributeName(attr.getProductAttributeName());
			readableAttribute.setAttributeValue(attr.getProductAttributeValueName());
			attributes.add(readableAttribute);
		}
		target.setAttributes(attributes);
	}
	
	String productSku = source.getSku();
	if (!StringUtils.isBlank(productSku)) {
		Product product = null;
		try {
			product = productService.getBySku(productSku, store, language);
		} catch (ServiceException e) {
			throw new ServiceRuntimeException(e);
		}
		if (product != null) {
			
			
			ReadableProduct productProxy = readableProductMapper.convert(product, store, language);
			target.setProduct(productProxy);
			
			/**
			 
			 // TODO autowired
			 ReadableProductPopulator populator = new ReadableProductPopulator();
			 populator.setPricingService(pricingService);
			 populator.setimageUtils(imageUtils);
			 
			 ReadableProduct productProxy;
			 try {
			 productProxy = populator.populate(product, new ReadableProduct(), store, language);
			 target.setProduct(productProxy);
			 } catch (ConversionException e) {
			 throw new ConversionRuntimeException("Cannot convert product", e);
			 }
			 
			 Set<ProductImage> images = product.getImages();
			 ProductImage defaultImage = null;
			 if (images != null) {
			 for (ProductImage image : images) {
			 if (defaultImage == null) {
			 defaultImage = image;
			 }
			 if (image.isDefaultImage()) {
			 defaultImage = image;
			 }
			 }
			 }
			 if (defaultImage != null) {
			 target.setImage(defaultImage.getProductImage());
			 }
			 
			 **/
		}
	}
	
	return target;
}

}
