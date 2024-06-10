package dev.vulcanium.business.services.catalog.product.type;

import dev.vulcanium.business.exception.ServiceException;
import dev.vulcanium.business.model.catalog.product.type.ProductType;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.business.services.common.generic.SalesManagerEntityService;
import java.util.List;
import org.springframework.data.domain.Page;

public interface ProductTypeService extends SalesManagerEntityService<Long, ProductType> {

	ProductType getProductType(String productTypeCode);
	Page<ProductType> getByMerchant(MerchantStore store, Language language, int page, int count) throws ServiceException;
    ProductType getByCode(String code, MerchantStore store, Language language) throws ServiceException;
    ProductType getById(Long id, MerchantStore store, Language language) throws ServiceException;
    ProductType getById(Long id, MerchantStore store) throws ServiceException;
    void update(String code, MerchantStore store, ProductType type) throws ServiceException;
    ProductType saveOrUpdate(ProductType productType) throws ServiceException;
    List<ProductType> listProductTypes(List<Long> ids, MerchantStore store, Language language) throws ServiceException;
    

}
