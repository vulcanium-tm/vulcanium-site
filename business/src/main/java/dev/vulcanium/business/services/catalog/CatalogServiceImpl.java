package dev.vulcanium.business.services.catalog;

import dev.vulcanium.business.exception.ServiceException;
import dev.vulcanium.business.model.catalog.Catalog;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.business.repositories.catalog.CatalogRepository;
import dev.vulcanium.business.repositories.catalog.PageableCatalogRepository;
import dev.vulcanium.business.services.common.generic.SalesManagerEntityServiceImpl;
import java.util.Optional;
import jakarta.inject.Inject;
import org.jsoup.helper.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service("catalogService")
public class CatalogServiceImpl 
extends SalesManagerEntityServiceImpl<Long, Catalog> 
implements CatalogService{
	
	
	private CatalogRepository catalogRepository;
	
	@Autowired
	private PageableCatalogRepository pageableCatalogRepository;

	@Inject
	public CatalogServiceImpl(CatalogRepository repository) {
		super(repository);
		this.catalogRepository = repository;
	}

	@Override
	public Catalog saveOrUpdate(Catalog catalog, MerchantStore store) {
		catalogRepository.save(catalog);
		return catalog;
	}

	@Override
	public Page<Catalog> getCatalogs(MerchantStore store, Language language, String name, int page, int count) {
		Pageable pageRequest = PageRequest.of(page, count);
		return pageableCatalogRepository.listByStore(store.getId(), name, pageRequest);
	}

	@Override
	public void delete(Catalog catalog) throws ServiceException {
		Validate.notNull(catalog,"Catalog must not be null");
		catalogRepository.delete(catalog);
	}

	@Override
	public Optional<Catalog> getById(Long catalogId, MerchantStore store) {
		return catalogRepository.findById(catalogId, store.getId());
	}

	@Override
	public Optional<Catalog> getByCode(String code, MerchantStore store) {
		return catalogRepository.findByCode(code, store.getId());
	}

	@Override
	public boolean existByCode(String code, MerchantStore store) {
		return catalogRepository.existsByCode(code, store.getId());
	}
	
	

}
