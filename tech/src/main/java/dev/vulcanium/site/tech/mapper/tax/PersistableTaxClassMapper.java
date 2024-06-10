package dev.vulcanium.site.tech.mapper.tax;

import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Component;

import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.business.model.tax.taxclass.TaxClass;
import dev.vulcanium.site.tech.mapper.Mapper;
import dev.vulcanium.site.tech.model.tax.PersistableTaxClass;

@Component
public class PersistableTaxClassMapper implements Mapper<PersistableTaxClass, TaxClass> {

@Override
public TaxClass convert(PersistableTaxClass source, MerchantStore store, Language language) {
	Validate.notNull(source, "PersistableTaxClass cannot be null");
	Validate.notNull(store, "MerchantStore cannot be null");
	TaxClass taxClass = new TaxClass();
	taxClass.setMerchantStore(store);
	taxClass.setTitle(source.getName());
	taxClass.setId(source.getId());
	return this.merge(source, taxClass, store, language);
}

@Override
public TaxClass merge(PersistableTaxClass source, TaxClass destination, MerchantStore store, Language language) {
	Validate.notNull(source, "PersistableTaxClass cannot be null");
	Validate.notNull(destination, "TaxClass cannot be null");
	Validate.notNull(store, "MerchantStore cannot be null");
	
	destination.setCode(source.getCode());
	if(source.getId()!=null && source.getId().longValue() > 0) {
		destination.setId(source.getId());
	}
	destination.setMerchantStore(store);
	destination.setTitle(source.getName());
	
	return destination;
}

}
