package dev.vulcanium.site.tech.mapper.catalog.product;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.inject.Inject;

import org.apache.commons.collections.CollectionUtils;
import org.jsoup.helper.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import dev.vulcanium.business.model.catalog.product.Product;
import dev.vulcanium.business.model.catalog.product.variant.ProductVariantImage;
import dev.vulcanium.business.model.catalog.product.variant.ProductVariant;
import dev.vulcanium.business.model.content.FileContentType;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.site.tech.mapper.Mapper;
import dev.vulcanium.site.tech.mapper.catalog.ReadableProductVariationMapper;
import dev.vulcanium.site.tech.mapper.inventory.ReadableInventoryMapper;
import dev.vulcanium.site.tech.model.catalog.product.ReadableImage;
import dev.vulcanium.site.tech.model.catalog.product.inventory.ReadableInventory;
import dev.vulcanium.site.tech.model.catalog.product.variant.ReadableProductVariant;
import dev.vulcanium.site.tech.store.api.exception.ResourceNotFoundException;
import dev.vulcanium.site.tech.utils.DateUtil;
import dev.vulcanium.site.tech.utils.ImageFilePath;

@Component
public class ReadableProductVariantMapper implements Mapper<ProductVariant, ReadableProductVariant> {


@Autowired
private ReadableProductVariationMapper readableProductVariationMapper;

@Autowired
private ReadableInventoryMapper readableInventoryMapper;

@Inject
@Qualifier("img")
private ImageFilePath imagUtils;

@Override
public ReadableProductVariant convert(ProductVariant source, MerchantStore store, Language language) {
	ReadableProductVariant readableproductVariant = new ReadableProductVariant();
	return this.merge(source, readableproductVariant, store, language);
}

@Override
public ReadableProductVariant merge(ProductVariant source, ReadableProductVariant destination,
                                    MerchantStore store, Language language) {
	
	Validate.notNull(source, "Product instance cannot be null");
	Validate.notNull(source.getProduct(), "Product cannot be null");
	
	if(destination == null) {
		destination = new ReadableProductVariant();
	}
	
	destination.setSortOrder(source.getSortOrder() != null ? source.getSortOrder().intValue():0);
	destination.setAvailable(source.isAvailable());
	destination.setDateAvailable(DateUtil.formatDate(source.getDateAvailable()));
	destination.setId(source.getId());
	destination.setDefaultSelection(source.isDefaultSelection());
	destination.setProductId(source.getProduct().getId());
	destination.setSku(source.getSku());
	destination.setSortOrder(source.getSortOrder());
	destination.setCode(source.getCode());
	
	Product baseProduct = source.getProduct();
	if(baseProduct == null) {
		throw new ResourceNotFoundException("Product instances do not include the parent product [" + destination.getSku() + "]");
	}
	
	destination.setProductShipeable(baseProduct.isProductShipeable());
	
	destination.setStore(store.getCode());
	destination.setVariation(readableProductVariationMapper.convert(source.getVariation(), store, language));
	if(source.getVariationValue() != null) {
		destination.setVariationValue(readableProductVariationMapper.convert(source.getVariationValue(), store, language));
	}
	
	if(source.getProductVariantGroup() != null) {
		Set<String> nameSet = new HashSet<>();
		List<ReadableImage> instanceImages = source.getProductVariantGroup().getImages().stream().map(i -> this.image(i, store, language))
				                                     .filter(e -> nameSet.add(e.getImageUrl()))
				                                     .collect(Collectors.toList());
		destination.setImages(instanceImages);
	}
	
	if(!CollectionUtils.isEmpty(source.getAvailabilities())) {
		List<ReadableInventory> inventories = source.getAvailabilities().stream().map(i -> readableInventoryMapper.convert(i, store, language)).collect(Collectors.toList());
		destination.setInventory(inventories);
	}
	
	return destination;
}

private ReadableImage image(ProductVariantImage instanceImage, MerchantStore store, Language language) {
	ReadableImage img = new ReadableImage();
	img.setDefaultImage(instanceImage.isDefaultImage());
	img.setId(instanceImage.getId());
	img.setImageName(instanceImage.getProductImage());
	img.setImageUrl(imagUtils.buildCustomTypeImageUtils(store, img.getImageName(), FileContentType.VARIANT));
	return img;
}


}
