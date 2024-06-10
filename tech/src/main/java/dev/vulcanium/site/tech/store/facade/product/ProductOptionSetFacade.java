package dev.vulcanium.site.tech.store.facade.product;

import java.util.List;

import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.site.tech.model.catalog.product.attribute.optionset.PersistableProductOptionSet;
import dev.vulcanium.site.tech.model.catalog.product.attribute.optionset.ReadableProductOptionSet;

public interface ProductOptionSetFacade {

ReadableProductOptionSet get(Long id, MerchantStore store, Language language);
boolean exists(String code, MerchantStore store);
List<ReadableProductOptionSet> list(MerchantStore store, Language language);
List<ReadableProductOptionSet> list(MerchantStore store, Language language, String type);
void create(PersistableProductOptionSet optionSet, MerchantStore store, Language language);
void update(Long id, PersistableProductOptionSet optionSet, MerchantStore store, Language language);
void delete(Long id, MerchantStore store);

}
