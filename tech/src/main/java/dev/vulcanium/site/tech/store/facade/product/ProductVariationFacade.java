package dev.vulcanium.site.tech.store.facade.product;

import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.site.tech.model.catalog.product.variation.PersistableProductVariation;
import dev.vulcanium.site.tech.model.catalog.product.variation.ReadableProductVariation;
import dev.vulcanium.site.tech.model.entity.ReadableEntityList;

public interface ProductVariationFacade {


ReadableProductVariation get(Long variationId, MerchantStore store, Language language);
boolean exists(String code, MerchantStore store);
Long create(PersistableProductVariation optionSet, MerchantStore store, Language language);
void update(Long variationId, PersistableProductVariation variation, MerchantStore store, Language language);
void delete(Long variation, MerchantStore store);
ReadableEntityList<ReadableProductVariation> list(MerchantStore store, Language language, int page, int count);




}
