package dev.vulcanium.business.services.catalog.product.attribute;

import dev.vulcanium.business.exception.ServiceException;
import dev.vulcanium.business.model.catalog.product.Product;
import dev.vulcanium.business.model.catalog.product.attribute.ProductAttribute;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.business.services.common.generic.SalesManagerEntityService;
import java.util.List;
import org.springframework.data.domain.Page;

public interface ProductAttributeService extends
		SalesManagerEntityService<Long, ProductAttribute> {

	ProductAttribute saveOrUpdate(ProductAttribute productAttribute)
			throws ServiceException;
	
	List<ProductAttribute> getByOptionId(MerchantStore store,
			Long id) throws ServiceException;

	List<ProductAttribute> getByOptionValueId(MerchantStore store,
			Long id) throws ServiceException;

	Page<ProductAttribute> getByProductId(MerchantStore store, Product product, Language language, int page, int count)
			throws ServiceException;
	
	Page<ProductAttribute> getByProductId(MerchantStore store, Product product, int page, int count)
			throws ServiceException;

	List<ProductAttribute> getByAttributeIds(MerchantStore store, Product product, List<Long> ids)
			throws ServiceException;
	
	List<ProductAttribute> getProductAttributesByCategoryLineage(MerchantStore store, String lineage, Language language) throws Exception;
}
