package dev.vulcanium.site.tech.store.facade.catalog;

import dev.vulcanium.business.model.catalog.Catalog;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.site.tech.model.catalog.*;
import dev.vulcanium.site.tech.model.entity.ReadableEntityList;

import java.util.Optional;

public interface CatalogFacade {

ReadableCatalog saveCatalog(PersistableCatalog catalog, MerchantStore store, Language language);

void updateCatalog(Long catalogId, PersistableCatalog catalog, MerchantStore store, Language language);

void deleteCatalog(Long catalogId, MerchantStore store, Language language);

boolean uniqueCatalog(String code, MerchantStore store);

ReadableCatalog getCatalog(String code, MerchantStore store, Language language);

Catalog getCatalog(String code, MerchantStore store);

ReadableCatalog getCatalog(Long id, MerchantStore store, Language language);

ReadableEntityList<ReadableCatalog> getListCatalogs(Optional<String> code, MerchantStore store, Language language, int page, int count);

ReadableEntityList<ReadableCatalogCategoryEntry> listCatalogEntry(Optional<String> product, Long catalogId, MerchantStore store, Language language, int page, int count);

ReadableCatalogCategoryEntry getCatalogEntry(Long id, MerchantStore store, Language language);

ReadableCatalogCategoryEntry addCatalogEntry(PersistableCatalogCategoryEntry entry, MerchantStore store, Language language);

void removeCatalogEntry(Long catalogId, Long catalogEntryId, MerchantStore store, Language language);

}
