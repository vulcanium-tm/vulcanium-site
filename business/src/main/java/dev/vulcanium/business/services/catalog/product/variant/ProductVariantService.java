package dev.vulcanium.business.services.catalog.product.variant;

import dev.vulcanium.business.exception.ServiceException;
import dev.vulcanium.business.model.catalog.product.Product;
import dev.vulcanium.business.model.catalog.product.variant.ProductVariant;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.business.services.common.generic.SalesManagerEntityService;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface ProductVariantService extends SalesManagerEntityService<Long, ProductVariant> {
	
	Optional<ProductVariant> getById(Long id, Long productId, MerchantStore store);
	
	List<ProductVariant> getByIds(List<Long> ids, MerchantStore store);
	
	Optional<ProductVariant> getById(Long id, MerchantStore store);
	
	Optional<ProductVariant> getBySku(String sku, Long productId, MerchantStore store, Language language);
	
	List<ProductVariant> getByProductId(MerchantStore store, Product product, Language language);
	
	
	Page<ProductVariant> getByProductId(MerchantStore store, Product product, Language language, int page, int count);
	
	
	boolean exist(String sku, Long productId);
	
	ProductVariant saveProductVariant(ProductVariant variant) throws ServiceException;
	


}
