package dev.vulcanium.site.tech.populator.catalog;

import org.apache.commons.lang3.Validate;

import dev.vulcanium.business.exception.ConversionException;
import dev.vulcanium.business.utils.AbstractDataPopulator;
import dev.vulcanium.business.model.catalog.product.Product;
import dev.vulcanium.business.model.catalog.product.image.ProductImage;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.site.tech.model.catalog.product.PersistableImage;

public class PersistableProductImagePopulator extends AbstractDataPopulator<PersistableImage, ProductImage> {


private Product product;

@Override
public ProductImage populate(PersistableImage source, ProductImage target, MerchantStore store, Language language)
		throws ConversionException {
	
	Validate.notNull(product,"Must set a product setProduct(Product)");
	Validate.notNull(product.getId(),"Product must have an id not null");
	Validate.notNull(source.getContentType(),"Content type must be set on persistable image");
	
	
	target.setDefaultImage(source.isDefaultImage());
	target.setImageType(source.getImageType());
	target.setProductImage(source.getName());
	if(source.getImageUrl() != null) {
		target.setProductImageUrl(source.getImageUrl());
	}
	target.setProduct(product);
	
	return target;
}

@Override
protected ProductImage createTarget() {
	return null;
}

public Product getProduct() {
	return product;
}

public void setProduct(Product product) {
	this.product = product;
}

}
