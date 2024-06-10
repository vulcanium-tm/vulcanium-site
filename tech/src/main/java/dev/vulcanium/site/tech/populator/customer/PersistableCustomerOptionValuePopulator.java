package dev.vulcanium.site.tech.populator.customer;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.Validate;

import dev.vulcanium.business.exception.ConversionException;
import dev.vulcanium.business.services.reference.language.LanguageService;
import dev.vulcanium.business.utils.AbstractDataPopulator;
import dev.vulcanium.business.model.customer.attribute.CustomerOptionValue;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.site.tech.model.customer.attribute.CustomerOptionValueDescription;
import dev.vulcanium.site.tech.model.customer.attribute.PersistableCustomerOptionValue;

public class PersistableCustomerOptionValuePopulator extends
		AbstractDataPopulator<PersistableCustomerOptionValue, CustomerOptionValue> {


private LanguageService languageService;

@Override
public CustomerOptionValue populate(PersistableCustomerOptionValue source,
                                    CustomerOptionValue target, MerchantStore store, Language language)
		throws ConversionException {
	
	
	Validate.notNull(languageService, "Requires to set LanguageService");
	
	
	try {
		
		target.setCode(source.getCode());
		target.setMerchantStore(store);
		target.setSortOrder(source.getOrder());
		
		if(!CollectionUtils.isEmpty(source.getDescriptions())) {
			Set<dev.vulcanium.business.model.customer.attribute.CustomerOptionValueDescription> descriptions = new HashSet<dev.vulcanium.business.model.customer.attribute.CustomerOptionValueDescription>();
			for(CustomerOptionValueDescription desc  : source.getDescriptions()) {
				dev.vulcanium.business.model.customer.attribute.CustomerOptionValueDescription description = new dev.vulcanium.business.model.customer.attribute.CustomerOptionValueDescription();
				Language lang = languageService.getByCode(desc.getLanguage());
				if(lang==null) {
					throw new ConversionException("Language is null for code " + description.getLanguage() + " use language ISO code [en, fr ...]");
				}
				description.setLanguage(lang);
				description.setName(desc.getName());
				description.setTitle(desc.getTitle());
				description.setCustomerOptionValue(target);
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
protected CustomerOptionValue createTarget() {
	return null;
}

public void setLanguageService(LanguageService languageService) {
	this.languageService = languageService;
}

public LanguageService getLanguageService() {
	return languageService;
}

}
