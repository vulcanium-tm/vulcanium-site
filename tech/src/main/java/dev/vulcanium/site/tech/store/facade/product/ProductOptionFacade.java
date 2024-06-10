package dev.vulcanium.site.tech.store.facade.product;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.site.tech.model.catalog.product.attribute.PersistableProductAttribute;
import dev.vulcanium.site.tech.model.catalog.product.attribute.PersistableProductOptionValue;
import dev.vulcanium.site.tech.model.catalog.product.attribute.api.PersistableProductOptionEntity;
import dev.vulcanium.site.tech.model.catalog.product.attribute.api.ReadableProductAttributeEntity;
import dev.vulcanium.site.tech.model.catalog.product.attribute.api.ReadableProductAttributeList;
import dev.vulcanium.site.tech.model.catalog.product.attribute.api.ReadableProductOptionEntity;
import dev.vulcanium.site.tech.model.catalog.product.attribute.api.ReadableProductOptionList;
import dev.vulcanium.site.tech.model.catalog.product.attribute.api.ReadableProductOptionValue;
import dev.vulcanium.site.tech.model.catalog.product.attribute.api.ReadableProductOptionValueList;
import dev.vulcanium.site.tech.model.entity.CodeEntity;

public interface ProductOptionFacade {

ReadableProductOptionEntity getOption(Long optionId, MerchantStore store, Language language);

ReadableProductOptionValue getOptionValue(Long optionValueId, MerchantStore store, Language language);

ReadableProductOptionEntity saveOption(PersistableProductOptionEntity option, MerchantStore store, Language language);

ReadableProductOptionValue saveOptionValue(PersistableProductOptionValue optionValue, MerchantStore store, Language language);

List<CodeEntity> createAttributes(List<PersistableProductAttribute> attributes, Long productId, MerchantStore store);
void updateAttributes(List<PersistableProductAttribute> attributes, Long productId, MerchantStore store);

void addOptionValueImage(MultipartFile image, Long optionValueId, MerchantStore store, Language language);

void removeOptionValueImage(Long optionValueId, MerchantStore store, Language language);

boolean optionExists(String code, MerchantStore store);

boolean optionValueExists(String code, MerchantStore store);

void deleteOption(Long optionId, MerchantStore store);

void deleteOptionValue(Long optionValueId, MerchantStore store);

ReadableProductOptionList options(MerchantStore store, Language language, String name, int page, int count);

ReadableProductOptionValueList optionValues(MerchantStore store, Language language, String name, int page, int count);

ReadableProductAttributeEntity saveAttribute(Long productId, PersistableProductAttribute attribute, MerchantStore store, Language language);

ReadableProductAttributeEntity getAttribute(Long productId, Long attributeId, MerchantStore store, Language language);

ReadableProductAttributeList getAttributesList(Long productId, MerchantStore store, Language language, int page, int count);

void deleteAttribute(Long productId, Long attributeId, MerchantStore store);

}