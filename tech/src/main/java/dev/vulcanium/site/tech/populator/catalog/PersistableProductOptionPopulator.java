package dev.vulcanium.site.tech.populator.catalog;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.Validate;

import dev.vulcanium.business.exception.ConversionException;
import dev.vulcanium.business.services.reference.language.LanguageService;
import dev.vulcanium.business.utils.AbstractDataPopulator;
import dev.vulcanium.business.model.catalog.product.attribute.ProductOption;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.site.tech.model.catalog.product.attribute.PersistableProductOption;
import dev.vulcanium.site.tech.model.catalog.product.attribute.ProductOptionDescription;

public class PersistableProductOptionPopulator extends
		AbstractDataPopulator<PersistableProductOption, ProductOption> {

private LanguageService languageService;

public LanguageService getLanguageService() {
	return languageService;
}

public void setLanguageService(LanguageService languageService) {
	this.languageService = languageService;
}

@Override
public ProductOption populate(PersistableProductOption source,
                              ProductOption target, MerchantStore store, Language language)
		throws ConversionException {
	Validate.notNull(languageService, "Requires to set LanguageService");
	
	
	try {
		
		
		target.setMerchantStore(store);
		target.setProductOptionSortOrder(source.getOrder());
		target.setCode(source.getCode());
		
		if(!CollectionUtils.isEmpty(source.getDescriptions())) {
			Set<dev.vulcanium.business.model.catalog.product.attribute.ProductOptionDescription> descriptions = new HashSet<dev.vulcanium.business.model.catalog.product.attribute.ProductOptionDescription>();
			for(ProductOptionDescription desc  : source.getDescriptions()) {
				dev.vulcanium.business.model.catalog.product.attribute.ProductOptionDescription description = new dev.vulcanium.business.model.catalog.product.attribute.ProductOptionDescription();
				Language lang = languageService.getByCode(desc.getLanguage());
				if(lang==null) {
					throw new ConversionException("Language is null for code " + description.getLanguage() + " use language ISO code [en, fr ...]");
				}
				description.setLanguage(lang);
				description.setName(desc.getName());
				description.setTitle(desc.getTitle());
				description.setProductOption(target);
				descriptions.add(description);
			}
			target.setDescriptions(descriptions);
		}
		
	} catch (Exception e) {
		throw new ConversionException(e);
	}
	
	
	return target;
}

@Override
protected ProductOption createTarget() {
	return null;
}

}
