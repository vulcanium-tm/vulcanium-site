package dev.vulcanium.site.tech.populator.catalog;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.Validate;

import dev.vulcanium.business.exception.ConversionException;
import dev.vulcanium.business.services.reference.language.LanguageService;
import dev.vulcanium.business.utils.AbstractDataPopulator;
import dev.vulcanium.business.model.catalog.product.attribute.ProductOptionValue;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.site.tech.model.catalog.product.attribute.PersistableProductOptionValue;
import dev.vulcanium.site.tech.model.catalog.product.attribute.ProductOptionValueDescription;


/**
 * Converts a PersistableProductOptionValue to
 * a ProductOptionValue model object
 */
public class PersistableProductOptionValuePopulator extends
		AbstractDataPopulator<PersistableProductOptionValue, ProductOptionValue> {


private LanguageService languageService;

public LanguageService getLanguageService() {
	return languageService;
}

public void setLanguageService(LanguageService languageService) {
	this.languageService = languageService;
}

@Override
public ProductOptionValue populate(PersistableProductOptionValue source,
                                   ProductOptionValue target, MerchantStore store, Language language)
		throws ConversionException {
	
	Validate.notNull(languageService, "Requires to set LanguageService");
	
	
	try {
		
		
		target.setMerchantStore(store);
		target.setProductOptionValueSortOrder(source.getOrder());
		target.setCode(source.getCode());
		
		if(!CollectionUtils.isEmpty(source.getDescriptions())) {
			Set<dev.vulcanium.business.model.catalog.product.attribute.ProductOptionValueDescription> descriptions = new HashSet<dev.vulcanium.business.model.catalog.product.attribute.ProductOptionValueDescription>();
			for(ProductOptionValueDescription desc  : source.getDescriptions()) {
				dev.vulcanium.business.model.catalog.product.attribute.ProductOptionValueDescription description = new dev.vulcanium.business.model.catalog.product.attribute.ProductOptionValueDescription();
				Language lang = languageService.getByCode(desc.getLanguage());
				if(lang==null) {
					throw new ConversionException("Language is null for code " + description.getLanguage() + " use language ISO code [en, fr ...]");
				}
				description.setLanguage(lang);
				description.setName(desc.getName());
				description.setTitle(desc.getTitle());
				description.setProductOptionValue(target);
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
protected ProductOptionValue createTarget() {
	return null;
}

}
