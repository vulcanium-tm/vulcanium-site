package dev.vulcanium.site.tech.store.facade.product;

import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.site.tech.model.catalog.product.definition.PersistableProductDefinition;
import dev.vulcanium.site.tech.model.catalog.product.definition.ReadableProductDefinition;

public interface ProductDefinitionFacade {

/**
 *
 * @param store
 * @param product
 * @param language
 * @return
 */
Long saveProductDefinition(MerchantStore store, PersistableProductDefinition product, Language language);

/**
 *
 * @param productId
 * @param product
 * @param merchant
 * @param language
 */
void update(Long productId, PersistableProductDefinition product, MerchantStore merchant, Language language);

/**
 *
 * @param store
 * @param id
 * @param language
 * @return
 */
ReadableProductDefinition getProduct(MerchantStore store, Long id, Language language);

/**
 *
 * @param store
 * @param uniqueCode
 * @param language
 * @return
 */
ReadableProductDefinition getProductBySku(MerchantStore store, String uniqueCode, Language language);

}
