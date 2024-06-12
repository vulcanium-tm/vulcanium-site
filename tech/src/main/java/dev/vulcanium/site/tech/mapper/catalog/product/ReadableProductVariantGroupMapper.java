package dev.vulcanium.site.tech.mapper.catalog.product;

import dev.vulcanium.business.model.catalog.product.variant.ProductVariant;
import dev.vulcanium.business.model.catalog.product.variant.ProductVariantGroup;
import dev.vulcanium.business.model.catalog.product.variant.ProductVariantImage;
import dev.vulcanium.business.model.content.FileContentType;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.business.utils.ImageFilePath;
import dev.vulcanium.site.tech.mapper.Mapper;
import dev.vulcanium.site.tech.model.catalog.product.ReadableImage;
import dev.vulcanium.site.tech.model.catalog.product.variant.ReadableProductVariant;
import dev.vulcanium.site.tech.model.catalog.product.variantgroup.ReadableProductVariantGroup;
import jakarta.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;
import org.jsoup.helper.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class ReadableProductVariantGroupMapper implements Mapper<ProductVariantGroup, ReadableProductVariantGroup> {


@Autowired
private ReadableProductVariantMapper readableProductVariantMapper;

@Inject
@Qualifier("img")
private ImageFilePath imageUtils;

@Override
public ReadableProductVariantGroup convert(ProductVariantGroup source, MerchantStore store, Language language) {
	Validate.notNull(source, "productVariantGroup cannot be null");
	Validate.notNull(store, "MerchantStore cannot be null");
	Validate.notNull(language, "Language cannot be null");
	return this.merge(source, new ReadableProductVariantGroup(), store, language);
}

@Override
public ReadableProductVariantGroup merge(ProductVariantGroup source, ReadableProductVariantGroup destination,
                                         MerchantStore store, Language language) {
	Validate.notNull(source, "productVariantGroup cannot be null");
	Validate.notNull(store, "MerchantStore cannot be null");
	Validate.notNull(language, "Language cannot be null");
	if(destination == null) {
		destination = new ReadableProductVariantGroup();
	}
	
	destination.setId(source.getId());
	
	Set<ProductVariant> instances = source.getProductVariants();
	destination.setproductVariants(instances.stream().map(i -> this.instance(i, store, language)).collect(Collectors.toList()));
	
	Map<Long,ReadableImage> finalList = new HashMap<Long, ReadableImage>();
	
	List<ReadableImage> originalList = source.getImages().stream()
			                                   .map(i -> this.image(finalList, i, store, language))
			                                   .collect(Collectors.toList());
	
	
	destination.setImages(new ArrayList<ReadableImage>(finalList.values()));
	
	return destination;
}

private ReadableProductVariant instance(ProductVariant instance, MerchantStore store, Language language) {
	
	return readableProductVariantMapper.convert(instance, store, language);
}

private ReadableImage image(Map<Long,ReadableImage> finalList , ProductVariantImage img, MerchantStore store, Language language) {
	ReadableImage readable = null;
	if(!finalList.containsKey(img.getId())) {
		readable = new ReadableImage();
		readable.setId(img.getId());
		readable.setImageName(img.getProductImage());
		readable.setImageUrl(imageUtils.buildCustomTypeImageUtils(store, img.getProductImage(), FileContentType.VARIANT));
		readable.setDefaultImage(img.isDefaultImage());
		finalList.put(img.getId(), readable);
		
	}
	return readable;
	
}

}