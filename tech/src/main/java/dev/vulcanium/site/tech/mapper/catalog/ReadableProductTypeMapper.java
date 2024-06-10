package dev.vulcanium.site.tech.mapper.catalog;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Component;

import dev.vulcanium.business.model.catalog.product.type.ProductType;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.site.tech.mapper.Mapper;
import dev.vulcanium.site.tech.model.catalog.product.type.ProductTypeDescription;
import dev.vulcanium.site.tech.model.catalog.product.type.ReadableProductType;
import dev.vulcanium.site.tech.model.catalog.product.type.ReadableProductTypeFull;

@Component
public class ReadableProductTypeMapper implements Mapper<ProductType, ReadableProductType> {

@Override
public ReadableProductType convert(ProductType source, MerchantStore store, Language language) {
	ReadableProductType type = new ReadableProductType();
	return this.merge(source, type, store, language);
}

@Override
public ReadableProductType merge(ProductType source, ReadableProductType destination, MerchantStore store,
                                 Language language) {
	Validate.notNull(source, "ProductType cannot be null");
	Validate.notNull(destination, "ReadableProductType cannot be null");
	return type(source, language);
}

private ReadableProductType type (ProductType type, Language language) {
	ReadableProductType readableType = null;
	
	
	if(language != null) {
		readableType = new ReadableProductType();
		if(!CollectionUtils.isEmpty(type.getDescriptions())) {
			Optional<ProductTypeDescription> desc = type.getDescriptions().stream().filter(t -> t.getLanguage().getCode().equals(language.getCode()))
					                                        .map(d -> typeDescription(d)).findFirst();
			if(desc.isPresent()) {
				readableType.setDescription(desc.get());
			}
		}
	} else {
		
		readableType = new ReadableProductTypeFull();
		List<ProductTypeDescription> descriptions = type.getDescriptions().stream().map(t -> this.typeDescription(t)).collect(Collectors.toList());
		((ReadableProductTypeFull)readableType).setDescriptions(descriptions);
		
	}
	
	readableType.setCode(type.getCode());
	readableType.setId(type.getId());
	readableType.setVisible(type.getVisible() != null && type.getVisible().booleanValue() ? true:false);
	readableType.setAllowAddToCart(type.getAllowAddToCart() != null && type.getAllowAddToCart().booleanValue() ? true:false);
	
	return readableType;
}

private ProductTypeDescription typeDescription(dev.vulcanium.business.model.catalog.product.type.ProductTypeDescription description) {
	ProductTypeDescription desc = new ProductTypeDescription();
	desc.setId(description.getId());
	desc.setName(description.getName());
	desc.setDescription(description.getDescription());
	desc.setLanguage(description.getLanguage().getCode());
	return desc;
}

}
