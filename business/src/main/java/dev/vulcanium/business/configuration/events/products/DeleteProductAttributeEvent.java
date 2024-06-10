package dev.vulcanium.business.configuration.events.products;

import dev.vulcanium.business.model.catalog.product.Product;
import dev.vulcanium.business.model.catalog.product.attribute.ProductAttribute;

public class DeleteProductAttributeEvent extends ProductEvent {
	
	
	private static final long serialVersionUID = 1L;
	private ProductAttribute productAttribute;

	public DeleteProductAttributeEvent(Object source, ProductAttribute productAttribute, Product product) {
		super(source, product);
		this.productAttribute=productAttribute;
	}

	public ProductAttribute getProductAttribute() {
		return productAttribute;
	}

}
