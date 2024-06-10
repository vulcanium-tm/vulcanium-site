package dev.vulcanium.site.tech.mapper.catalog;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.helper.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dev.vulcanium.business.services.reference.language.LanguageService;
import dev.vulcanium.business.model.catalog.product.attribute.ProductOptionValue;
import dev.vulcanium.business.model.catalog.product.attribute.ProductOptionValueDescription;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.site.tech.mapper.Mapper;
import dev.vulcanium.site.tech.model.catalog.product.attribute.PersistableProductOptionValue;
import dev.vulcanium.site.tech.store.api.exception.ServiceRuntimeException;

@Component
public class PersistableProductOptionValueMapper
		implements Mapper<PersistableProductOptionValue, ProductOptionValue> {

@Autowired
private LanguageService languageService;

ProductOptionValueDescription description(
		dev.vulcanium.business.model.catalog.product.attribute.ProductOptionValueDescription description)
		throws Exception {
	Validate.notNull(description.getLanguage(), "description.language should not be null");
	ProductOptionValueDescription desc = new ProductOptionValueDescription();
	desc.setId(null);
	desc.setDescription(description.getDescription());
	desc.setName(description.getName());
	if(StringUtils.isBlank(desc.getName())) {
		desc.setName(description.getDescription());
	}
	if (description.getId() != null && description.getId().longValue() > 0) {
		desc.setId(description.getId());
	}
	Language lang = languageService.getByCode(String.valueOf(description.getLanguage()));
	desc.setLanguage(lang);
	return desc;
}

@Override
public ProductOptionValue merge(PersistableProductOptionValue source, ProductOptionValue destination,
                                MerchantStore store, Language language) {
	if (destination == null) {
		destination = new ProductOptionValue();
	}
	
	try {
		
		if(StringUtils.isBlank(source.getCode())) {
			if(!StringUtils.isBlank(destination.getCode())) {
				source.setCode(destination.getCode());
			}
		}
		
		if (!CollectionUtils.isEmpty(source.getDescriptions())) {
			for (dev.vulcanium.site.tech.model.catalog.product.attribute.ProductOptionValueDescription desc : source
					                                                                                                .getDescriptions()) {
				ProductOptionValueDescription description = null;
				if (!CollectionUtils.isEmpty(destination.getDescriptions())) {
					for (ProductOptionValueDescription d : destination.getDescriptions()) {
						if (!StringUtils.isBlank(desc.getLanguage())
								    && desc.getLanguage().equals(d.getLanguage())) {
							
							d.setDescription(desc.getDescription());
							d.setName(desc.getName());
							d.setTitle(desc.getTitle());
							if(StringUtils.isBlank(d.getName())) {
								d.setName(d.getDescription());
							}
							description = d;
							break;
							
						}
					}
				}
				if(description == null) {
					description = description(description);
					description.setProductOptionValue(destination);
					destination.getDescriptions().add(description);
				}
			}
		}
		
		destination.setCode(source.getCode());
		destination.setMerchantStore(store);
		destination.setProductOptionValueSortOrder(source.getSortOrder());
		
		
		return destination;
	} catch (Exception e) {
		throw new ServiceRuntimeException("Error while converting product option", e);
	}
}

@Override
public ProductOptionValue convert(PersistableProductOptionValue source, MerchantStore store,
                                  Language language) {
	ProductOptionValue destination = new ProductOptionValue();
	return merge(source, destination, store, language);
}


}