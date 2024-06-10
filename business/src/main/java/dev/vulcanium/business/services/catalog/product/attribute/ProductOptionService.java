package dev.vulcanium.business.services.catalog.product.attribute;

import dev.vulcanium.business.exception.ServiceException;
import dev.vulcanium.business.model.catalog.product.attribute.ProductOption;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.business.services.common.generic.SalesManagerEntityService;
import java.util.List;
import org.springframework.data.domain.Page;

public interface ProductOptionService extends SalesManagerEntityService<Long, ProductOption> {

	List<ProductOption> listByStore(MerchantStore store, Language language)
			throws ServiceException;


	List<ProductOption> getByName(MerchantStore store, String name,
			Language language) throws ServiceException;

	void saveOrUpdate(ProductOption entity) throws ServiceException;


	List<ProductOption> listReadOnly(MerchantStore store, Language language)
			throws ServiceException;


	ProductOption getByCode(MerchantStore store, String optionCode);
	
	ProductOption getById(MerchantStore store, Long optionId);
	
	Page<ProductOption> getByMerchant(MerchantStore store, Language language, String name, int page, int count);
	



}
