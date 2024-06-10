package dev.vulcanium.site.tech.store.facade.product;

import java.util.List;

import dev.vulcanium.business.model.catalog.category.Category;
import dev.vulcanium.business.model.catalog.product.Product;
import dev.vulcanium.business.model.catalog.product.review.ProductReview;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.site.tech.model.catalog.product.LightPersistableProduct;
import dev.vulcanium.site.tech.model.catalog.product.PersistableProductReview;
import dev.vulcanium.site.tech.model.catalog.product.ProductPriceEntity;
import dev.vulcanium.site.tech.model.catalog.product.ReadableProduct;
import dev.vulcanium.site.tech.model.catalog.product.ReadableProductReview;
import dev.vulcanium.site.tech.model.catalog.product.PersistableProduct;

public interface ProductCommonFacade {


/**
 * Create / Update product
 * @param store
 * @param product
 * @param language
 * @return
 */
Long saveProduct(MerchantStore store, PersistableProduct product,
                 Language language);

/**
 * Update minimal product details
 * @param product
 * @param merchant
 * @param language
 */
void update(Long productId, LightPersistableProduct product, MerchantStore merchant, Language language);

/**
 * Patch inventory by sku
 * @param sku
 * @param product
 * @param merchant
 * @param language
 */
void update(String sku, LightPersistableProduct product, MerchantStore merchant, Language language);

/**
 * Get a Product by id and store
 *
 * @param store
 * @param id
 * @param language
 * @return
 * @throws Exception
 */
ReadableProduct getProduct(MerchantStore store, Long id, Language language) throws Exception;

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
ReadableProduct getProductByCode(MerchantStore store, String uniqueCode, Language language)
		throws Exception;


/**
 * Sets a new price to an existing product
 *
 * @param product
 * @param price
 * @param language
 * @return
 * @throws Exception
 */
ReadableProduct updateProductPrice(ReadableProduct product, ProductPriceEntity price,
                                   Language language) throws Exception;

/**
 * Sets a new price to an existing product
 *
 * @param product
 * @param quantity
 * @param language
 * @return
 * @throws Exception
 */
ReadableProduct updateProductQuantity(ReadableProduct product, int quantity, Language language)
		throws Exception;

/**
 * Deletes a product for a given product id
 *
 * @param product
 * @throws Exception
 */
void deleteProduct(Product product) throws Exception;

/**
 * Delete product
 * @param id
 * @param store
 * @throws Exception
 */
void deleteProduct(Long id, MerchantStore store);



/**
 * Adds a product to a category
 *
 * @param category
 * @param product
 * @return
 */
ReadableProduct addProductToCategory(Category category, Product product, Language language);

/**
 * Removes item from a category
 *
 * @param category
 * @param product
 * @param language
 * @return
 * @throws Exception
 */
ReadableProduct removeProductFromCategory(Category category, Product product, Language language)
		throws Exception;


/**
 * Saves or updates a Product review
 *
 * @param review
 * @param language
 * @throws Exception
 */
void saveOrUpdateReview(PersistableProductReview review, MerchantStore store, Language language)
		throws Exception;

/**
 * Deletes a product review
 *
 * @param review
 * @param store
 * @param language
 * @throws Exception
 */
void deleteReview(ProductReview review, MerchantStore store, Language language) throws Exception;

/**
 * Get reviews for a given product
 *
 * @param product
 * @param store
 * @param language
 * @return
 * @throws Exception
 */
List<ReadableProductReview> getProductReviews(Product product, MerchantStore store,
                                              Language language) throws Exception;

/**
 * validates if product exists
 * @param sku
 * @param store
 * @return
 */
public boolean exists(String sku, MerchantStore store);



}
