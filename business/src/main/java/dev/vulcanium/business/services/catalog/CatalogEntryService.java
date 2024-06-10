package dev.vulcanium.business.services.catalog;

import dev.vulcanium.business.exception.ServiceException;
import dev.vulcanium.business.model.catalog.Catalog;
import dev.vulcanium.business.model.catalog.CatalogCategoryEntry;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.business.services.common.generic.SalesManagerEntityService;
import org.springframework.data.domain.Page;

public interface CatalogEntryService extends SalesManagerEntityService<Long, CatalogCategoryEntry> {
	
	
	void add (CatalogCategoryEntry entry, Catalog catalog);
	
	void remove (CatalogCategoryEntry catalogEntry) throws ServiceException;
	
	Page<CatalogCategoryEntry> list(Catalog catalog, MerchantStore store, Language language, String name, int page, int count);

}
