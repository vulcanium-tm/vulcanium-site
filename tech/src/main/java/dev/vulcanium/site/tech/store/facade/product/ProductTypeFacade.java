package dev.vulcanium.site.tech.store.facade.product;

import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.site.tech.model.catalog.product.type.PersistableProductType;
import dev.vulcanium.site.tech.model.catalog.product.type.ReadableProductType;
import dev.vulcanium.site.tech.model.catalog.product.type.ReadableProductTypeList;

public interface ProductTypeFacade {

ReadableProductTypeList getByMerchant(MerchantStore store, Language language, int count, int page);

ReadableProductType get(MerchantStore store, Long id, Language language);

ReadableProductType get(MerchantStore store, String code, Language language);

Long save(PersistableProductType type, MerchantStore store, Language language);

void update(PersistableProductType type, Long id, MerchantStore store, Language language);

void delete(Long id, MerchantStore store, Language language);

boolean exists(String code, MerchantStore store, Language language);

}
