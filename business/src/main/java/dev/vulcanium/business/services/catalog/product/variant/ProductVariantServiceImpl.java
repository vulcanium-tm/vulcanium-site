package dev.vulcanium.business.services.catalog.product.variant;

import dev.vulcanium.business.exception.ServiceException;
import dev.vulcanium.business.model.catalog.product.Product;
import dev.vulcanium.business.model.catalog.product.variant.ProductVariant;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.business.repositories.catalog.product.variant.PageableProductVariantRepositoty;
import dev.vulcanium.business.repositories.catalog.product.variant.ProductVariantRepository;
import dev.vulcanium.business.services.common.generic.SalesManagerEntityServiceImpl;
import java.util.List;
import java.util.Optional;
import jakarta.inject.Inject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service("productVariantService")
public class ProductVariantServiceImpl extends SalesManagerEntityServiceImpl<Long, ProductVariant>
		implements ProductVariantService {
	

	private ProductVariantRepository productVariantRepository;

	@Autowired
	private PageableProductVariantRepositoty pageableProductVariantRepositoty;

	@Inject
	public ProductVariantServiceImpl(ProductVariantRepository productVariantRepository) {
		super(productVariantRepository);
		this.productVariantRepository = productVariantRepository;
	}

	@Override
	public Optional<ProductVariant> getById(Long id, Long productId, MerchantStore store) {
		return productVariantRepository.findById(id, productId, store.getId());
	}

	public Page<ProductVariant> getByProductId(MerchantStore store, Product product, Language language, int page,
			int count) {
		Pageable pageRequest = PageRequest.of(page, count);
		return pageableProductVariantRepositoty.findByProductId(store.getId(), product.getId(), pageRequest);
	}

	@Override
	public List<ProductVariant> getByProductId(MerchantStore store, Product product, Language language) {
		return productVariantRepository.findByProductId(store.getId(), product.getId());
	}

	@Override
	public Optional<ProductVariant> getBySku(String sku, Long productId, MerchantStore store, Language language) {
		return productVariantRepository.findBySku(sku, productId, store.getId(), language.getId());
	}

	@Override
	public boolean exist(String sku, Long productId) {

		ProductVariant instance = productVariantRepository.existsBySkuAndProduct(sku, productId);
		return instance != null? true:false;

	}

	@Override
	public Optional<ProductVariant> getById(Long id, MerchantStore store) {

		return productVariantRepository.findOne(id,store.getId());
	}

	@Override
	public List<ProductVariant> getByIds(List<Long> ids, MerchantStore store) {

		return productVariantRepository.findByIds(ids, store.getId());
	}

	@Override
	public ProductVariant saveProductVariant(ProductVariant variant) throws ServiceException {

		variant = productVariantRepository.save(variant);
		return variant;
	}
	
	@Override
	public void delete(ProductVariant instance) throws ServiceException{
		super.delete(instance);
	}

}
