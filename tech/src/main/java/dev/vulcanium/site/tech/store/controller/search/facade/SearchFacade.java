package dev.vulcanium.site.tech.store.controller.search.facade;

import java.util.List;

import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.site.tech.model.catalog.SearchProductRequest;
import dev.vulcanium.site.tech.model.entity.ValueList;

import modules.commons.search.request.SearchItem;
import modules.commons.search.request.SearchResponse;

/**
 * Different services for searching and indexing data
 */
public interface SearchFacade {


/**
 * This utility method will re-index all products in the catalogue
 * @param store
 * @throws Exception
 */
public void indexAllData(MerchantStore store) throws Exception;

/**
 * Produces a search request against elastic search
 * @param searchRequest
 * @return
 * @throws Exception
 */
List<SearchItem> search(MerchantStore store, Language language, SearchProductRequest searchRequest);

/**
 * Copy sm-core search response to a simple readable format populated with corresponding products
 * @param searchResponse
 * @return
 */
//public SearchProductList convertToSearchProductList(SearchResponse searchResponse, MerchantStore store, int start, int count, Language language) throws Exception;

/**
 * List of keywords / autocompletes for a given word being typed
 * @param query
 * @param store
 * @param language
 * @return
 * @throws Exception
 */
ValueList autocompleteRequest(String query, MerchantStore store, Language language);
}
