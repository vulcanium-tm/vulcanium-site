package dev.vulcanium.site.tech.store.facade.product;

import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.site.tech.model.catalog.product.inventory.PersistableInventory;
import dev.vulcanium.site.tech.model.catalog.product.inventory.ReadableInventory;
import dev.vulcanium.site.tech.model.entity.ReadableEntityList;

public interface ProductInventoryFacade {

ReadableInventory get(Long inventoryId, MerchantStore store, Language language);

ReadableEntityList<ReadableInventory> get(String sku, MerchantStore store, Language language, int page, int count);

ReadableInventory add(PersistableInventory inventory, MerchantStore store, Language language);

void update(PersistableInventory inventory, MerchantStore store, Language language);

void delete(Long productId, Long inventoryId, MerchantStore store);

ReadableEntityList<ReadableInventory> get(Long productId, MerchantStore store, Language language, int page, int count);



}
