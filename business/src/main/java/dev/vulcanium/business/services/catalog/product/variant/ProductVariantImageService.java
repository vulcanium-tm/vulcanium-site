package dev.vulcanium.business.services.catalog.product.variant;

import dev.vulcanium.business.model.catalog.product.variant.ProductVariantImage;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.services.common.generic.SalesManagerEntityService;
import java.util.List;

public interface ProductVariantImageService extends SalesManagerEntityService<Long, ProductVariantImage> {

	
	List<ProductVariantImage> list(Long productVariantId, MerchantStore store);
	List<ProductVariantImage> listByProduct(Long productId, MerchantStore store);
	List<ProductVariantImage> listByProductVariantGroup(Long productVariantGroupId, MerchantStore store);
	
}
