package dev.vulcanium.site.tech.mapper.catalog;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.helper.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import dev.vulcanium.business.services.reference.language.LanguageService;
import dev.vulcanium.business.model.catalog.product.attribute.ProductOption;
import dev.vulcanium.business.model.catalog.product.attribute.ProductOptionDescription;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.site.tech.mapper.Mapper;
import dev.vulcanium.site.tech.model.catalog.product.attribute.api.PersistableProductOptionEntity;
import dev.vulcanium.site.tech.store.api.exception.ServiceRuntimeException;

@Component
public class PersistableProductOptionMapper implements Mapper<PersistableProductOptionEntity, ProductOption> {

@Autowired
private LanguageService languageService;



ProductOptionDescription description(dev.vulcanium.business.model.catalog.product.attribute.ProductOptionDescription description) throws Exception {
	Validate.notNull(description.getLanguage(),"description.language should not be null");
	ProductOptionDescription desc = new ProductOptionDescription();
	desc.setId(null);
	desc.setDescription(description.getDescription());
	desc.setName(description.getName());
	if(description.getId() != null && description.getId().longValue()>0) {
		desc.setId(description.getId());
	}
	Language lang = languageService.getByCode(String.valueOf(description.getLanguage()));
	desc.setLanguage(lang);
	return desc;
}


@Override
public ProductOption convert(PersistableProductOptionEntity source, MerchantStore store,
                             Language language) {
	ProductOption destination = new ProductOption();
	return merge(source, destination, store, language);
}


@Override
public ProductOption merge(PersistableProductOptionEntity source, ProductOption destination,
                           MerchantStore store, Language language) {
	if(destination == null) {
		destination = new ProductOption();
	}
	
	try {
		
		if(!CollectionUtils.isEmpty(source.getDescriptions())) {
			for(dev.vulcanium.site.tech.model.catalog.product.attribute.ProductOptionDescription desc : source.getDescriptions()) {
				ProductOptionDescription description = null;
				if(!CollectionUtils.isEmpty(destination.getDescriptions())) {
					for(ProductOptionDescription d : destination.getDescriptions()) {
						if(!StringUtils.isBlank(desc.getLanguage()) && desc.getLanguage().equals(d.getLanguage().getCode())) {
							d.setDescription(desc.getDescription());
							d.setName(desc.getName());
							d.setTitle(desc.getTitle());
							description = d;
							break;
						}
					}
				}
				if(description == null) {
					description = description(description);
					description.setProductOption(destination);
					destination.getDescriptions().add(description);
				}
			}
		}
		
		destination.setCode(source.getCode());
		destination.setMerchantStore(store);
		destination.setProductOptionSortOrder(source.getOrder());
		destination.setProductOptionType(source.getType());
		destination.setReadOnly(source.isReadOnly());
		
		
		return destination;
	} catch (Exception e) {
		throw new ServiceRuntimeException("Error while converting product option", e);
	}
}

}