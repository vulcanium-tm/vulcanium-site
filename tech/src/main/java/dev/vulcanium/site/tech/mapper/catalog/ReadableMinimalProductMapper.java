package dev.vulcanium.site.tech.mapper.catalog;

import dev.vulcanium.business.exception.ServiceException;
import dev.vulcanium.business.model.catalog.product.Product;
import dev.vulcanium.business.model.catalog.product.description.ProductDescription;
import dev.vulcanium.business.model.catalog.product.image.ProductImage;
import dev.vulcanium.business.model.catalog.product.price.FinalPrice;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.business.services.catalog.pricing.PricingService;
import dev.vulcanium.business.utils.DateUtil;
import dev.vulcanium.business.utils.ImageFilePath;
import dev.vulcanium.site.tech.mapper.Mapper;
import dev.vulcanium.site.tech.model.catalog.product.ProductSpecification;
import dev.vulcanium.site.tech.model.catalog.product.ReadableImage;
import dev.vulcanium.site.tech.model.catalog.product.ReadableMinimalProduct;
import dev.vulcanium.site.tech.model.entity.ReadableDescription;
import dev.vulcanium.site.tech.store.api.exception.ConversionRuntimeException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.jsoup.helper.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class ReadableMinimalProductMapper implements Mapper<Product, ReadableMinimalProduct> {

@Autowired
private PricingService pricingService;


@Autowired
@Qualifier("img")
private ImageFilePath imageUtils;

@Override
public ReadableMinimalProduct convert(Product source, MerchantStore store, Language language) {
	// TODO Auto-generated method stub
	ReadableMinimalProduct minimal = new ReadableMinimalProduct();
	return this.merge(source, minimal, store, language);
}

@Override
public ReadableMinimalProduct merge(Product source, ReadableMinimalProduct destination, MerchantStore store,
                                    Language language) {
	Validate.notNull(source, "Product cannot be null");
	Validate.notNull(destination, "ReadableMinimalProduct cannot be null");
	
	
	for (ProductDescription desc : source.getDescriptions()) {
		if (language != null && desc.getLanguage() != null
				    && desc.getLanguage().getId().intValue() == language.getId().intValue()) {
			destination.setDescription(this.description(desc));
			break;
		}
	}
	
	destination.setId(source.getId());
	destination.setAvailable(source.isAvailable());
	destination.setProductShipeable(source.isProductShipeable());
	
	ProductSpecification specifications = new ProductSpecification();
	specifications.setHeight(source.getProductHeight());
	specifications.setLength(source.getProductLength());
	specifications.setWeight(source.getProductWeight());
	specifications.setWidth(source.getProductWidth());
	destination.setProductSpecifications(specifications);
	
	destination.setPreOrder(source.isPreOrder());
	destination.setRefSku(source.getRefSku());
	destination.setSortOrder(source.getSortOrder());
	destination.setSku(source.getSku());
	
	if(source.getDateAvailable() != null) {
		destination.setDateAvailable(DateUtil.formatDate(source.getDateAvailable()));
	}
	
	if(source.getProductReviewAvg()!=null) {
		double avg = source.getProductReviewAvg().doubleValue();
		double rating = Math.round(avg * 2) / 2.0f;
		destination.setRating(rating);
	}
	
	destination.setProductVirtual(source.getProductVirtual());
	if(source.getProductReviewCount()!=null) {
		destination.setRatingCount(source.getProductReviewCount().intValue());
	}
	
	try {
		FinalPrice price = pricingService.calculateProductPrice(source);
		if(price != null) {
			
			destination.setFinalPrice(pricingService.getDisplayAmount(price.getFinalPrice(), store));
			destination.setPrice(price.getFinalPrice());
			destination.setOriginalPrice(pricingService.getDisplayAmount(price.getOriginalPrice(), store));
			
		}
	} catch (ServiceException e) {
		throw new ConversionRuntimeException("An error occured during price calculation", e);
	}
	
	Set<ProductImage> images = source.getImages();
	if(images!=null && images.size()>0) {
		List<ReadableImage> imageList = new ArrayList<ReadableImage>();
		
		String contextPath = imageUtils.getContextPath();
		
		for(ProductImage img : images) {
			ReadableImage prdImage = new ReadableImage();
			prdImage.setImageName(img.getProductImage());
			prdImage.setDefaultImage(img.isDefaultImage());
			
			StringBuilder imgPath = new StringBuilder();
			imgPath.append(contextPath).append(imageUtils.buildProductImageUtils(store, source.getSku(), img.getProductImage()));
			
			prdImage.setImageUrl(imgPath.toString());
			prdImage.setId(img.getId());
			prdImage.setImageType(img.getImageType());
			if(img.getProductImageUrl()!=null){
				prdImage.setExternalUrl(img.getProductImageUrl());
			}
			if(img.getImageType()==1 && img.getProductImageUrl()!=null) {//video
				prdImage.setVideoUrl(img.getProductImageUrl());
			}
			
			if(prdImage.isDefaultImage()) {
				destination.setImage(prdImage);
			}
			
			imageList.add(prdImage);
		}
		destination
				.setImages(imageList);
	}
	
	
	return null;
}

private ReadableDescription description(ProductDescription description) {
	ReadableDescription desc = new ReadableDescription();
	desc.setDescription(description.getDescription());
	desc.setName(description.getName());
	desc.setId(description.getId());
	desc.setLanguage(description.getLanguage().getCode());
	return desc;
}

}
