package dev.vulcanium.business.configuration.events.products;

import dev.vulcanium.business.model.catalog.product.Product;
import dev.vulcanium.business.model.catalog.product.image.ProductImage;

public class SaveProductImageEvent extends ProductEvent {
	
	
	private static final long serialVersionUID = 1L;
	private ProductImage productImage;

	public ProductImage getProductImage() {
		return productImage;
	}

	public SaveProductImageEvent(Object source, ProductImage productImage, Product product) {
		super(source, product);
		this.productImage = productImage;

	}


}
