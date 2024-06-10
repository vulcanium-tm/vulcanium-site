package dev.vulcanium.site.tech.populator.store;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import dev.vulcanium.business.exception.ConversionException;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.site.tech.model.store.ReadableMerchantStore;

/**
 * Populates MerchantStore core entity model object with more complete details than the traditional ReadableMerchantStorePopulator
 */
public class ReadableMerchantStorePopulatorWithDetails extends
		ReadableMerchantStorePopulator {

protected final Log logger = LogFactory.getLog(getClass());

@Override
public ReadableMerchantStore populate(MerchantStore source,
                                      ReadableMerchantStore target, MerchantStore store, Language language)
		throws ConversionException {
	
	target = super.populate(source, target, store, language);
	
	target.setTemplate(source.getStoreTemplate());
	
	return target;
}

@Override
protected ReadableMerchantStore createTarget() {
	return null;
}

}
