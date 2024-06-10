package dev.vulcanium.business.services.catalog.inventory;

import dev.vulcanium.business.exception.ServiceException;
import dev.vulcanium.business.model.catalog.product.Product;
import dev.vulcanium.business.model.catalog.product.inventory.ProductInventory;
import dev.vulcanium.business.model.catalog.product.variant.ProductVariant;

public interface ProductInventoryService {
	
	
	ProductInventory inventory(Product product) throws ServiceException;
	ProductInventory inventory(ProductVariant variant) throws ServiceException;

}
