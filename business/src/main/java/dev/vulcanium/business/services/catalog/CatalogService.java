package dev.vulcanium.business.services.catalog;

import dev.vulcanium.business.exception.ServiceException;
import dev.vulcanium.business.model.catalog.Catalog;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.business.services.common.generic.SalesManagerEntityService;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface CatalogService extends SalesManagerEntityService<Long, Catalog> {
	
	
	/**
	 * Creates a new Catalog
	 * @param store
	 * @return Catalog
	 * @throws ServiceException
	 */
	Catalog saveOrUpdate(Catalog catalog, MerchantStore store);

	Optional<Catalog> getById(Long catalogId, MerchantStore store);

	Optional<Catalog> getByCode(String code, MerchantStore store);
	
	/**
	 * Get a list of Catalog associated with a MarketPlace
	 * @param marketPlace
	 * @return List<Catalog>
	 * @throws ServiceException
	 */
	Page<Catalog> getCatalogs(MerchantStore store, Language language, String name, int page, int count);
	
	/**
	 * Delete a Catalog and related objects
	 */
	void delete(Catalog catalog) throws ServiceException;
	
	boolean existByCode(String code, MerchantStore store);

}
