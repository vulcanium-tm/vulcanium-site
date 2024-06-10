package dev.vulcanium.business.services.catalog.product.variation;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import dev.vulcanium.business.exception.ServiceException;
import dev.vulcanium.business.services.common.generic.SalesManagerEntityService;
import dev.vulcanium.business.model.catalog.product.variation.ProductVariation;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;

public interface ProductVariationService extends SalesManagerEntityService<Long, ProductVariation> {
	

	void saveOrUpdate(ProductVariation entity) throws ServiceException;

	Optional<ProductVariation> getById(MerchantStore store, Long id, Language lang);
	
	Optional<ProductVariation> getById(MerchantStore store, Long id);
	
	Optional<ProductVariation> getByCode(MerchantStore store, String code);
	
	Page<ProductVariation> getByMerchant(MerchantStore store, Language language, String code, int page, int count);
	
	List<ProductVariation> getByIds(List<Long> ids, MerchantStore store);

}
