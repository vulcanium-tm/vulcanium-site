package dev.vulcanium.business.services.catalog.product.attribute;

import dev.vulcanium.business.exception.ServiceException;
import dev.vulcanium.business.model.catalog.product.attribute.ProductOptionValue;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.business.services.common.generic.SalesManagerEntityService;
import java.util.List;
import org.springframework.data.domain.Page;

public interface ProductOptionValueService extends SalesManagerEntityService<Long, ProductOptionValue> {

	void saveOrUpdate(ProductOptionValue entity) throws ServiceException;

	List<ProductOptionValue> getByName(MerchantStore store, String name,
			Language language) throws ServiceException;


	List<ProductOptionValue> listByStore(MerchantStore store, Language language)
			throws ServiceException;

	List<ProductOptionValue> listByStoreNoReadOnly(MerchantStore store,
			Language language) throws ServiceException;

	ProductOptionValue getByCode(MerchantStore store, String optionValueCode);
	
	ProductOptionValue getById(MerchantStore store, Long optionValueId);
	
	Page<ProductOptionValue> getByMerchant(MerchantStore store, Language language, String name, int page, int count);
	

}
