package dev.vulcanium.business.services.catalog.product.file;

import dev.vulcanium.business.exception.ServiceException;
import dev.vulcanium.business.services.common.generic.SalesManagerEntityService;
import dev.vulcanium.business.model.catalog.product.Product;
import dev.vulcanium.business.model.catalog.product.file.DigitalProduct;
import dev.vulcanium.business.model.content.InputContentFile;
import dev.vulcanium.business.model.merchant.MerchantStore;


public interface DigitalProductService extends SalesManagerEntityService<Long, DigitalProduct> {

	void saveOrUpdate(DigitalProduct digitalProduct) throws ServiceException;

	void addProductFile(Product product, DigitalProduct digitalProduct,
			InputContentFile inputFile) throws ServiceException;



	DigitalProduct getByProduct(MerchantStore store, Product product)
			throws ServiceException;

	
}
