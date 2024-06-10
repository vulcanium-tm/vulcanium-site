package dev.vulcanium.site.tech.populator.system;

import org.apache.commons.lang3.Validate;

import dev.vulcanium.business.exception.ConversionException;
import dev.vulcanium.business.utils.AbstractDataPopulator;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.business.model.system.optin.Optin;
import dev.vulcanium.site.tech.model.system.ReadableOptin;

public class ReadableOptinPopulator extends AbstractDataPopulator<Optin, ReadableOptin> {

@Override
public ReadableOptin populate(Optin source, ReadableOptin target, MerchantStore store, Language language)
		throws ConversionException {
	Validate.notNull(store,"MerchantStore cannot be null");
	Validate.notNull(source,"Optin cannot be null");
	
	if(target==null) {
		target = new ReadableOptin();
	}
	
	target.setCode(source.getCode());
	target.setDescription(source.getDescription());
	target.setEndDate(source.getEndDate());
	target.setId(source.getId());
	target.setOptinType(source.getOptinType().name());
	target.setStartDate(source.getStartDate());
	target.setStore(store.getCode());
	
	return target;
}

@Override
protected ReadableOptin createTarget() {
	return null;
}

}
