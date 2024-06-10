package dev.vulcanium.business.services.catalog.product.variant;

import dev.vulcanium.business.exception.ServiceException;
import dev.vulcanium.business.model.catalog.product.variant.ProductVariantGroup;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.business.services.common.generic.SalesManagerEntityService;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface ProductVariantGroupService extends SalesManagerEntityService<Long, ProductVariantGroup> {

	
	Optional<ProductVariantGroup> getById(Long id, MerchantStore store);
	
	Optional<ProductVariantGroup> getByProductVariant(Long productVariantId, MerchantStore store, Language language);

	Page<ProductVariantGroup> getByProductId(MerchantStore store, Long productId, Language language, int page, int count);

	ProductVariantGroup saveOrUpdate(ProductVariantGroup entity) throws ServiceException;
	
	
}
