package dev.vulcanium.site.tech.mapper.catalog;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dev.vulcanium.business.exception.ServiceException;
import dev.vulcanium.business.services.reference.language.LanguageService;
import dev.vulcanium.business.model.catalog.product.type.ProductType;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.site.tech.mapper.Mapper;
import dev.vulcanium.site.tech.model.catalog.product.type.PersistableProductType;
import dev.vulcanium.site.tech.model.catalog.product.type.ProductTypeDescription;
import dev.vulcanium.site.tech.store.api.exception.ConversionRuntimeException;


@Component
public class PersistableProductTypeMapper implements Mapper<PersistableProductType, ProductType> {

@Autowired
private LanguageService languageService;

@Override
public ProductType convert(PersistableProductType source, MerchantStore store, Language language) {
	ProductType type = new ProductType();
	return this.merge(source, type, store, language);
}

@Override
public ProductType merge(PersistableProductType source, ProductType destination, MerchantStore store,
                         Language language) {
	Validate.notNull(destination, "ReadableProductType cannot be null");
	try {
		return type(source, destination);
	} catch (ServiceException e) {
		throw new ConversionRuntimeException(e);
	}
}

private ProductType type (PersistableProductType type, ProductType destination) throws ServiceException {
	if(destination == null) {
		destination = new ProductType();
	}
	destination.setCode(type.getCode());
	destination.setId(type.getId());
	destination.setAllowAddToCart(type.isAllowAddToCart());
	destination.setVisible(type.isVisible());
	//destination.set
	
	List<dev.vulcanium.business.model.catalog.product.type.ProductTypeDescription> descriptions = new ArrayList<dev.vulcanium.business.model.catalog.product.type.ProductTypeDescription>();
	if(!CollectionUtils.isEmpty(type.getDescriptions())) {
		
		for(ProductTypeDescription d : type.getDescriptions()) {
			dev.vulcanium.business.model.catalog.product.type.ProductTypeDescription desc = typeDescription(d, destination, d.getLanguage());
			descriptions.add(desc);
			
			
		}
		
		destination.setDescriptions(new HashSet<dev.vulcanium.business.model.catalog.product.type.ProductTypeDescription>(descriptions));
		
	}
	
	return destination;
}

private dev.vulcanium.business.model.catalog.product.type.ProductTypeDescription typeDescription(ProductTypeDescription description, ProductType typeModel, String lang) throws ServiceException {
	
	dev.vulcanium.business.model.catalog.product.type.ProductTypeDescription desc = null;
	if(!CollectionUtils.isEmpty(typeModel.getDescriptions()) ){
		desc = this.findAppropriateDescription(typeModel, lang);
	}
	
	if(desc == null) {
		desc = new dev.vulcanium.business.model.catalog.product.type.ProductTypeDescription();
	}
	
	desc.setName(description.getName());
	desc.setDescription(description.getDescription());
	desc.setLanguage(languageService.getByCode(description.getLanguage()));
	desc.setProductType(typeModel);
	return desc;
}

private dev.vulcanium.business.model.catalog.product.type.ProductTypeDescription findAppropriateDescription(ProductType typeModel, String lang) {
	java.util.Optional<dev.vulcanium.business.model.catalog.product.type.ProductTypeDescription> d = typeModel.getDescriptions().stream().filter(t -> t.getLanguage().getCode().equals(lang)).findFirst();
	if(d.isPresent()) {
		return d.get();
	} else {
		return null;
	}
}

}
