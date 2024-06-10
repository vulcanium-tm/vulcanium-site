package dev.vulcanium.site.tech.store.facade.product;

import java.util.List;

import dev.vulcanium.business.model.catalog.product.Product;
import dev.vulcanium.business.model.catalog.product.ProductCriteria;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.site.tech.model.catalog.product.ReadableProduct;
import dev.vulcanium.site.tech.model.catalog.product.ReadableProductList;

public interface ProductFacade {

/**
 *
 * @param id
 * @param store
 * @return
 */
Product getProduct(Long id, MerchantStore store);

/**
 * Reads a product by code
 *
 * @param store
 * @param uniqueCode
 * @param language
 * @return
 * @throws Exception
 */
ReadableProduct getProductByCode(MerchantStore store, String uniqueCode, Language language);

/**
 * Get a product by sku and store
 *
 * @param store
 * @param sku
 * @param language
 * @return
 * @throws Exception
 */
ReadableProduct getProduct(MerchantStore store, String sku, Language language) throws Exception;

/**
 * Get a Product by friendlyUrl (slug), store and language
 *
 * @param store
 * @param friendlyUrl
 * @param language
 * @return
 * @throws Exception
 */
ReadableProduct getProductBySeUrl(MerchantStore store, String friendlyUrl, Language language) throws Exception;

/**
 * Filters a list of product based on criteria
 *
 * @param store
 * @param language
 * @param criterias
 * @return
 * @throws Exception
 */
ReadableProductList getProductListsByCriterias(MerchantStore store, Language language,
                                               ProductCriteria criterias) throws Exception;

/**
 * Get related items
 *
 * @param store
 * @param product
 * @param language
 * @return
 * @throws Exception
 */
List<ReadableProduct> relatedItems(MerchantStore store, Product product, Language language)
		throws Exception;
	
	
}
