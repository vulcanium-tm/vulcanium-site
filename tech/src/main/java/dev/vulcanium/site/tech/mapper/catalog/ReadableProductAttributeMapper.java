package dev.vulcanium.site.tech.mapper.catalog;

import dev.vulcanium.business.model.catalog.product.attribute.ProductAttribute;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.business.services.catalog.pricing.PricingService;
import dev.vulcanium.business.store.api.exception.ConversionRuntimeException;
import dev.vulcanium.site.tech.mapper.Mapper;
import dev.vulcanium.site.tech.model.catalog.product.attribute.api.ReadableProductAttributeEntity;
import dev.vulcanium.site.tech.model.catalog.product.attribute.api.ReadableProductOptionEntity;
import dev.vulcanium.site.tech.model.catalog.product.attribute.api.ReadableProductOptionValue;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReadableProductAttributeMapper implements Mapper<ProductAttribute, ReadableProductAttributeEntity> {

@Autowired
private ReadableProductOptionMapper readableProductOptionMapper;

@Autowired
private ReadableProductOptionValueMapper readableProductOptionValueMapper;

@Autowired
private PricingService pricingService;


@Override
public ReadableProductAttributeEntity convert(ProductAttribute source, MerchantStore store, Language language) {
	ReadableProductAttributeEntity productAttribute = new ReadableProductAttributeEntity();
	return merge(source, productAttribute, store, language);
}

@Override
public ReadableProductAttributeEntity merge(ProductAttribute source, ReadableProductAttributeEntity destination,
                                            MerchantStore store, Language language) {
	
	ReadableProductAttributeEntity attr = new ReadableProductAttributeEntity();
	if(destination !=null) {
		attr = destination;
	}
	try {
		attr.setId(source.getId());//attribute of the option
		
		if(source.getProductAttributePrice()!=null && source.getProductAttributePrice().doubleValue()>0) {
			String formatedPrice;
			formatedPrice = pricingService.getDisplayAmount(source.getProductAttributePrice(), store);
			attr.setProductAttributePrice(formatedPrice);
			attr.setProductAttributeUnformattedPrice(pricingService.getStringAmount(source.getProductAttributePrice(), store));
		}
		
		attr.setProductAttributeWeight(source.getAttributeAdditionalWeight());
		attr.setAttributeDisplayOnly(source.getAttributeDisplayOnly());
		attr.setAttributeDefault(source.getAttributeDefault());
		if(!StringUtils.isBlank(source.getAttributeSortOrder())) {
			attr.setSortOrder(Integer.parseInt(source.getAttributeSortOrder()));
		}
		
		if(source.getProductOption()!=null) {
			ReadableProductOptionEntity option = readableProductOptionMapper.convert(source.getProductOption(), store, language);
			attr.setOption(option);
		}
		
		if(source.getProductOptionValue()!=null) {
			ReadableProductOptionValue optionValue = readableProductOptionValueMapper.convert(source.getProductOptionValue(), store, language);
			attr.setOptionValue(optionValue);
		}
		
	} catch (Exception e) {
		throw new ConversionRuntimeException("Exception while product attribute conversion",e);
	}
	
	
	return attr;
}

}
