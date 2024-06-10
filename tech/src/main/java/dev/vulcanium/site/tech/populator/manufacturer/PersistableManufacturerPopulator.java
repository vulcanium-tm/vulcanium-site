package dev.vulcanium.site.tech.populator.manufacturer;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.Validate;

import dev.vulcanium.business.exception.ConversionException;
import dev.vulcanium.business.services.reference.language.LanguageService;
import dev.vulcanium.business.utils.AbstractDataPopulator;
import dev.vulcanium.business.model.catalog.product.manufacturer.Manufacturer;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.site.tech.model.catalog.manufacturer.ManufacturerDescription;
import dev.vulcanium.site.tech.model.catalog.manufacturer.PersistableManufacturer;

public class PersistableManufacturerPopulator extends AbstractDataPopulator<PersistableManufacturer, Manufacturer>
{


private LanguageService languageService;

@Override
public Manufacturer populate(PersistableManufacturer source,
                             Manufacturer target, MerchantStore store, Language language)
		throws ConversionException {
	
	Validate.notNull(languageService, "Requires to set LanguageService");
	
	try {
		
		target.setMerchantStore(store);
		target.setCode(source.getCode());
		
		
		if(!CollectionUtils.isEmpty(source.getDescriptions())) {
			Set<dev.vulcanium.business.model.catalog.product.manufacturer.ManufacturerDescription> descriptions = new HashSet<dev.vulcanium.business.model.catalog.product.manufacturer.ManufacturerDescription>();
			for(ManufacturerDescription description : source.getDescriptions()) {
				dev.vulcanium.business.model.catalog.product.manufacturer.ManufacturerDescription desc = new dev.vulcanium.business.model.catalog.product.manufacturer.ManufacturerDescription();
				if(desc.getId() != null && desc.getId().longValue()>0) {
					desc.setId(description.getId());
				}
				if(target.getDescriptions() != null) {
					for(dev.vulcanium.business.model.catalog.product.manufacturer.ManufacturerDescription d : target.getDescriptions()) {
						if(d.getLanguage().getCode().equals(description.getLanguage()) || desc.getId() != null && d.getId().longValue() == desc.getId().longValue()) {
							desc = d;
						}
					}
				}
				
				desc.setManufacturer(target);
				desc.setDescription(description.getDescription());
				desc.setName(description.getName());
				Language lang = languageService.getByCode(description.getLanguage());
				if(lang==null) {
					throw new ConversionException("Language is null for code " + description.getLanguage() + " use language ISO code [en, fr ...]");
				}
				desc.setLanguage(lang);
				descriptions.add(desc);
			}
			target.setDescriptions(descriptions);
		}
		
	} catch (Exception e) {
		throw new ConversionException(e);
	}
	
	
	return target;
}

@Override
protected Manufacturer createTarget() {
	return null;
}

public void setLanguageService(LanguageService languageService) {
	this.languageService = languageService;
}

public LanguageService getLanguageService() {
	return languageService;
}
}
