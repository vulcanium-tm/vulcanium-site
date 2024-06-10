package dev.vulcanium.site.tech.mapper.catalog;

import jakarta.inject.Inject;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import dev.vulcanium.business.model.catalog.product.image.ProductImage;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.site.tech.mapper.Mapper;
import dev.vulcanium.site.tech.model.catalog.product.ReadableImage;
import dev.vulcanium.site.tech.utils.ImageFilePath;

@Component
public class ReadableProductImageMapper implements Mapper<ProductImage, ReadableImage> {


@Inject
@Qualifier("img")
private ImageFilePath imageUtils;

@Override
public ReadableImage convert(ProductImage source, MerchantStore store, Language language) {
	ReadableImage destination = new ReadableImage();
	return merge(source, destination, store, language);
}

@Override
public ReadableImage merge(ProductImage source, ReadableImage destination, MerchantStore store, Language language) {
	
	String contextPath = imageUtils.getContextPath();
	
	destination.setImageName(source.getProductImage());
	destination.setDefaultImage(source.isDefaultImage());
	destination.setOrder(source.getSortOrder() != null ? source.getSortOrder().intValue() : 0);
	
	if (source.getImageType() == 1 && source.getProductImageUrl()!=null) {
		destination.setImageUrl(source.getProductImageUrl());
	} else {
		StringBuilder imgPath = new StringBuilder();
		imgPath.append(contextPath).append(imageUtils.buildProductImageUtils(store, source.getProduct().getSku(), source.getProductImage()));
		destination.setImageUrl(imgPath.toString());
	}
	destination.setId(source.getId());
	destination.setImageType(source.getImageType());
	if(source.getProductImageUrl()!=null){
		destination.setExternalUrl(source.getProductImageUrl());
	}
	if(source.getImageType()==1 && source.getProductImageUrl()!=null) {//video
		destination.setVideoUrl(source.getProductImageUrl());
	}
	
	return destination;
}

}
