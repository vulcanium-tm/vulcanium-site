package dev.vulcanium.business.services.catalog.product.price;

import dev.vulcanium.business.exception.ServiceException;
import dev.vulcanium.business.model.catalog.product.price.ProductPrice;
import dev.vulcanium.business.model.catalog.product.price.ProductPriceDescription;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.services.common.generic.SalesManagerEntityService;
import java.util.List;

public interface ProductPriceService extends SalesManagerEntityService<Long, ProductPrice> {

	void addDescription(ProductPrice price, ProductPriceDescription description) throws ServiceException;

	ProductPrice saveOrUpdate(ProductPrice price) throws ServiceException;
	
	List<ProductPrice> findByProductSku(String sku, MerchantStore store);
	
	ProductPrice findById(Long priceId, String sku, MerchantStore store);
	
	List<ProductPrice> findByInventoryId(Long productInventoryId, String sku, MerchantStore store);
	

}
