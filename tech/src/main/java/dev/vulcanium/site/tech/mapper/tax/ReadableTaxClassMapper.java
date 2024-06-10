package dev.vulcanium.site.tech.mapper.tax;

import dev.vulcanium.site.tech.mapper.Mapper;
import org.springframework.stereotype.Component;

import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.business.model.tax.taxclass.TaxClass;
import dev.vulcanium.site.tech.model.tax.ReadableTaxClass;

@Component
public class ReadableTaxClassMapper implements Mapper<TaxClass, ReadableTaxClass>{

@Override
public ReadableTaxClass convert(TaxClass source, MerchantStore store, Language language) {
	ReadableTaxClass taxClass = new ReadableTaxClass();
	taxClass.setId(source.getId());
	taxClass.setCode(source.getCode());
	taxClass.setName(source.getTitle());
	taxClass.setStore(store.getCode());
	return taxClass;
}

@Override
public ReadableTaxClass merge(TaxClass source, ReadableTaxClass destination, MerchantStore store,
                              Language language) {
	destination.setId(source.getId());
	destination.setCode(source.getCode());
	destination.setName(source.getTitle());
	destination.setStore(store.getCode());
	return destination;
}

}
