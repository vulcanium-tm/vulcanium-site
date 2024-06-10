package dev.vulcanium.business.services.catalog.product.type;

import dev.vulcanium.business.exception.ServiceException;
import dev.vulcanium.business.model.catalog.product.type.ProductType;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.business.repositories.catalog.product.type.PageableProductTypeRepository;
import dev.vulcanium.business.repositories.catalog.product.type.ProductTypeRepository;
import dev.vulcanium.business.services.common.generic.SalesManagerEntityServiceImpl;
import java.util.List;
import jakarta.inject.Inject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service("productTypeService")
public class ProductTypeServiceImpl extends SalesManagerEntityServiceImpl<Long, ProductType>
		implements ProductTypeService {

	private ProductTypeRepository productTypeRepository;
	
	@Autowired
	private PageableProductTypeRepository pageableProductTypeRepository;

	@Inject
	public ProductTypeServiceImpl(ProductTypeRepository productTypeRepository) {
		super(productTypeRepository);
		this.productTypeRepository = productTypeRepository;
	}

	@Override
	public ProductType getByCode(String code, MerchantStore store, Language language) throws ServiceException {
		return productTypeRepository.findByCode(code, store.getId());
	}

	@Override
	public void update(String code, MerchantStore store, ProductType type) throws ServiceException {
		productTypeRepository.save(type);

	}
	
	@Override
	public ProductType getProductType(String productTypeCode) {
		return productTypeRepository.findByCode(productTypeCode);
	}

	@Override
	public Page<ProductType> getByMerchant(MerchantStore store, Language language, int page, int count) throws ServiceException {
		Pageable pageRequest = PageRequest.of(page, count);
		return pageableProductTypeRepository.listByStore(store.getId(), pageRequest);
	}

	@Override
	public ProductType getById(Long id, MerchantStore store, Language language) throws ServiceException {
		return productTypeRepository.findById(id, store.getId(), language.getId());
	}

	@Override
	public ProductType saveOrUpdate(ProductType productType) throws ServiceException {
		if(productType.getId()!=null && productType.getId() > 0) {
			this.update(productType);
		} else {
			productType = super.saveAndFlush(productType);
		}
		
		return productType;
	}

	@Override
	public List<ProductType> listProductTypes(List<Long> ids, MerchantStore store, Language language)
			throws ServiceException {
		return productTypeRepository.findByIds(ids, store.getId(), language.getId());
	}

	@Override
	public ProductType getById(Long id, MerchantStore store) throws ServiceException {
		return productTypeRepository.findById(id, store.getId());
	}


}
