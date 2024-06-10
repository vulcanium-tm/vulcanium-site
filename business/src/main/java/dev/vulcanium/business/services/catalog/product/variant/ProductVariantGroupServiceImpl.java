package dev.vulcanium.business.services.catalog.product.variant;

import dev.vulcanium.business.exception.ServiceException;
import dev.vulcanium.business.model.catalog.product.variant.ProductVariantGroup;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.business.repositories.catalog.product.variant.PageableProductVariantGroupRepository;
import dev.vulcanium.business.repositories.catalog.product.variant.ProductVariantGroupRepository;
import dev.vulcanium.business.services.common.generic.SalesManagerEntityServiceImpl;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service("productVariantGroupService")
public class ProductVariantGroupServiceImpl extends SalesManagerEntityServiceImpl<Long, ProductVariantGroup> implements ProductVariantGroupService {

	
	@Autowired
	private PageableProductVariantGroupRepository pageableProductVariantGroupRepository;
	
	private ProductVariantGroupRepository productVariantGroupRepository;
	
	public ProductVariantGroupServiceImpl(ProductVariantGroupRepository repository) {
		super(repository);
		this.productVariantGroupRepository = repository;
	}

	@Override
	public Optional<ProductVariantGroup> getById(Long id, MerchantStore store) {
		return  productVariantGroupRepository.findOne(id, store.getCode());

	}

	@Override
	public Optional<ProductVariantGroup> getByProductVariant(Long productVariantId, MerchantStore store,
			Language language) {
		return productVariantGroupRepository.finByProductVariant(productVariantId, store.getCode());
	}

	@Override
	public ProductVariantGroup saveOrUpdate(ProductVariantGroup entity) throws ServiceException {
		
		entity = productVariantGroupRepository.save(entity);
		return entity;
		
	}

	@Override
	public Page<ProductVariantGroup> getByProductId(MerchantStore store, Long productId, Language language, int page,
			int count) {
		
		Pageable pageRequest = PageRequest.of(page, count);
		return pageableProductVariantGroupRepository.findByProductId(store.getId(), productId, pageRequest);
	}


}
