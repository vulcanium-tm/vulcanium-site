package dev.vulcanium.business.services.catalog;

import dev.vulcanium.business.exception.ServiceException;
import dev.vulcanium.business.model.catalog.Catalog;
import dev.vulcanium.business.model.catalog.CatalogCategoryEntry;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.business.repositories.catalog.CatalogEntryRepository;
import dev.vulcanium.business.repositories.catalog.PageableCatalogEntryRepository;
import dev.vulcanium.business.services.common.generic.SalesManagerEntityServiceImpl;
import jakarta.inject.Inject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service("catalogEntryService")
public class CatalogEntryServiceImpl extends SalesManagerEntityServiceImpl<Long, CatalogCategoryEntry> 
implements CatalogEntryService{
	
	@Autowired
	private PageableCatalogEntryRepository pageableCatalogEntryRepository;

	private CatalogEntryRepository catalogEntryRepository;
	
	@Inject
	public CatalogEntryServiceImpl(CatalogEntryRepository repository) {
		super(repository);
		this.catalogEntryRepository = repository;
	}

	@Override
	public void add(CatalogCategoryEntry entry, Catalog catalog) {
		entry.setCatalog(catalog);
		catalogEntryRepository.save(entry);
	}


	@Override
	public Page<CatalogCategoryEntry> list(Catalog catalog, MerchantStore store, Language language, String name, int page,
			int count) {
		Pageable pageRequest = PageRequest.of(page, count);
		return pageableCatalogEntryRepository.listByCatalog(catalog.getId(), store.getId(), language.getId(), name, pageRequest);

	}

	@Override
	public void remove(CatalogCategoryEntry catalogEntry) throws ServiceException {
		catalogEntryRepository.delete(catalogEntry);
		
	}


}
