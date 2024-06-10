package dev.vulcanium.business.configuration.events.products;

import dev.vulcanium.business.model.catalog.product.Product;

public class DeleteProductEvent extends ProductEvent {

	private static final long serialVersionUID = 1L;

	public DeleteProductEvent(Object source, Product product) {
		super(source, product);
	}

}
