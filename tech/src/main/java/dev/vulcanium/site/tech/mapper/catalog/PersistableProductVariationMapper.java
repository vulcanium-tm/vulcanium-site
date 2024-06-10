package dev.vulcanium.site.tech.mapper.catalog;

import org.jsoup.helper.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dev.vulcanium.business.services.catalog.product.attribute.ProductOptionService;
import dev.vulcanium.business.services.catalog.product.attribute.ProductOptionValueService;
import dev.vulcanium.business.model.catalog.product.attribute.ProductOption;
import dev.vulcanium.business.model.catalog.product.attribute.ProductOptionValue;
import dev.vulcanium.business.model.catalog.product.variation.ProductVariation;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.site.tech.mapper.Mapper;
import dev.vulcanium.site.tech.model.catalog.product.variation.PersistableProductVariation;
import dev.vulcanium.site.tech.store.api.exception.ConversionRuntimeException;

@Component
public class PersistableProductVariationMapper implements Mapper<PersistableProductVariation, ProductVariation> {

@Autowired
private ProductOptionService productOptionService;

@Autowired
private ProductOptionValueService productOptionValueService;

@Override
public ProductVariation convert(PersistableProductVariation source, MerchantStore store, Language language) {
	
	ProductVariation variation = new ProductVariation();
	return this.merge(source, variation, store, language);
	
}

@Override
public ProductVariation merge(PersistableProductVariation source, ProductVariation destination, MerchantStore store,
                              Language language) {
	Validate.notNull(destination, "ProductVariation cannot be null");
	
	destination.setId(source.getId());
	destination.setCode(source.getCode());
	destination.setMerchantStore(store);
	
	ProductOption option = productOptionService.getById(store, source.getOption());
	if(option == null) {
		throw new ConversionRuntimeException("ProductOption [" + source.getOption() + "] does not exists");
	}
	destination.setProductOption(option);
	
	ProductOptionValue optionValue = productOptionValueService.getById(store, source.getOptionValue());
	if(optionValue == null) {
		throw new ConversionRuntimeException("ProductOptionValue [" + source.getOptionValue() + "] does not exists");
	}
	destination.setProductOptionValue(optionValue);
	
	
	return destination;
	
	
}

}
