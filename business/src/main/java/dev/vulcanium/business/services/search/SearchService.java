package dev.vulcanium.business.services.search;

import dev.vulcanium.business.exception.ServiceException;
import dev.vulcanium.business.model.catalog.product.Product;
import dev.vulcanium.business.model.merchant.MerchantStore;
import java.util.Optional;
import modules.commons.search.request.Document;
import modules.commons.search.request.SearchRequest;
import modules.commons.search.request.SearchResponse;

public interface SearchService {
	
	/**
	 * The indexing service for products. The index service must be invoked when a product is
	 * created or updated
	 * @param store
	 * @param product
	 * @throws ServiceException
	 */
	void index(MerchantStore store, Product product) throws ServiceException;

	/**
	 * Deletes a document in the appropriate language. Must be invoked when a product is deleted
	 * @param store
	 * @param product
	 * @throws ServiceException
	 */
	void deleteDocument(MerchantStore store, Product product)
			throws ServiceException;

	/**
	 * Similar keywords based on a a series of characters. Used in the auto-complete
	 */
	SearchResponse searchKeywords(MerchantStore store, String language, SearchRequest search, int entriesCount) throws ServiceException;

	/**
	 * Search products based on user entry
	 */

	SearchResponse search(MerchantStore store, String language, SearchRequest search,
					int entriesCount, int startIndex) throws ServiceException;

	
	Optional<Document> getDocument(String language, MerchantStore store, Long id) throws ServiceException;

}
