package dev.vulcanium.site.tech.mapper.catalog;

import org.springframework.stereotype.Component;

import dev.vulcanium.business.model.catalog.Catalog;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.site.tech.mapper.Mapper;
import dev.vulcanium.site.tech.model.catalog.PersistableCatalog;

@Component
public class PersistableCatalogMapper implements Mapper<PersistableCatalog, Catalog> {

@Override
public Catalog convert(PersistableCatalog source, MerchantStore store, Language language) {
	Catalog c = new Catalog();
	return this.merge(source, c, store, language);
}

@Override
public Catalog merge(PersistableCatalog source, Catalog destination, MerchantStore store, Language language) {
	
	
	destination.setCode(source.getCode());
	destination.setDefaultCatalog(source.isDefaultCatalog());
	destination.setId(source.getId());
	destination.setMerchantStore(store);
	destination.setVisible(source.isVisible());
	
	return destination;
}

}
