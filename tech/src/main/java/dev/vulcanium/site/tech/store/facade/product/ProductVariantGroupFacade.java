package dev.vulcanium.site.tech.store.facade.product;

import org.springframework.web.multipart.MultipartFile;

import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.site.tech.model.catalog.product.variantgroup.PersistableProductVariantGroup;
import dev.vulcanium.site.tech.model.catalog.product.variantgroup.ReadableProductVariantGroup;
import dev.vulcanium.site.tech.model.entity.ReadableEntityList;

public interface ProductVariantGroupFacade {

ReadableProductVariantGroup get(Long instanceGroupId, MerchantStore store, Language language);
Long create(PersistableProductVariantGroup productVariantGroup, MerchantStore store, Language language);
void update(Long productVariantGroup, PersistableProductVariantGroup instance, MerchantStore store, Language language);
void delete(Long productVariant, Long productId, MerchantStore store);
ReadableEntityList<ReadableProductVariantGroup> list(Long productId, MerchantStore store, Language language, int page, int count);

void addImage(MultipartFile image, Long instanceGroupId,
              MerchantStore store, Language language);

void removeImage(Long imageId, Long instanceGroupId, MerchantStore store);

}
