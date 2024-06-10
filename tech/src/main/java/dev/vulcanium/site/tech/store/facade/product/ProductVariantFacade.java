package dev.vulcanium.site.tech.store.facade.product;

import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.site.tech.model.catalog.product.variant.PersistableProductVariant;
import dev.vulcanium.site.tech.model.catalog.product.variant.ReadableProductVariant;
import dev.vulcanium.site.tech.model.entity.ReadableEntityList;

public interface ProductVariantFacade {

ReadableProductVariant get(Long instanceId, Long productId, MerchantStore store, Language language);
boolean exists(String sku, MerchantStore store, Long productId, Language language);
Long create(PersistableProductVariant productVariant, Long productId, MerchantStore store, Language language);
void update(Long instanceId, PersistableProductVariant instance, Long productId, MerchantStore store, Language language);
void delete(Long productVariant, Long productId, MerchantStore store);
ReadableEntityList<ReadableProductVariant> list(Long productId, MerchantStore store, Language language, int page, int count);


}
