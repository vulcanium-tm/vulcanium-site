package dev.vulcanium.business.services.catalog.product.attribute;

import dev.vulcanium.business.exception.ServiceException;
import dev.vulcanium.business.model.catalog.product.Product;
import dev.vulcanium.business.model.catalog.product.attribute.ProductAttribute;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.business.repositories.catalog.product.attribute.PageableProductAttributeRepository;
import dev.vulcanium.business.repositories.catalog.product.attribute.ProductAttributeRepository;
import dev.vulcanium.business.services.common.generic.SalesManagerEntityServiceImpl;
import java.util.List;
import jakarta.inject.Inject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service("productAttributeService")
public class ProductAttributeServiceImpl extends SalesManagerEntityServiceImpl<Long, ProductAttribute>
		implements ProductAttributeService {

	private ProductAttributeRepository productAttributeRepository;
	@Autowired
	private PageableProductAttributeRepository pageableProductAttributeRepository;

	@Inject
	public ProductAttributeServiceImpl(ProductAttributeRepository productAttributeRepository) {
		super(productAttributeRepository);
		this.productAttributeRepository = productAttributeRepository;
	}

	@Override
	public ProductAttribute getById(Long id) {

		return productAttributeRepository.findOne(id);

	}

	@Override
	public List<ProductAttribute> getByOptionId(MerchantStore store, Long id) throws ServiceException {

		return productAttributeRepository.findByOptionId(store.getId(), id);

	}

	@Override
	public List<ProductAttribute> getByAttributeIds(MerchantStore store, Product product, List<Long> ids)
			throws ServiceException {

		return productAttributeRepository.findByAttributeIds(store.getId(), product.getId(), ids);

	}

	@Override
	public List<ProductAttribute> getByOptionValueId(MerchantStore store, Long id) throws ServiceException {

		return productAttributeRepository.findByOptionValueId(store.getId(), id);

	}

	/**
	 * Returns all product attributes
	 */
	@Override
	public Page<ProductAttribute> getByProductId(MerchantStore store, Product product, Language language, int page,
			int count) throws ServiceException {

		Pageable pageRequest = PageRequest.of(page, count);
		return pageableProductAttributeRepository.findByProductId(store.getId(), product.getId(), language.getId(),
				pageRequest);

	}

	@Override
	public Page<ProductAttribute> getByProductId(MerchantStore store, Product product, int page, int count)
			throws ServiceException {
		Pageable pageRequest = PageRequest.of(page, count);
		return pageableProductAttributeRepository.findByProductId(store.getId(), product.getId(), pageRequest);

	}

	@Override
	public ProductAttribute saveOrUpdate(ProductAttribute productAttribute) throws ServiceException {
		productAttribute = productAttributeRepository.save(productAttribute);
		return productAttribute;

	}

	@Override
	public void delete(ProductAttribute attribute) throws ServiceException {

		// override method, this allows the error that we try to remove a detached
		// variant
		attribute = this.getById(attribute.getId());
		super.delete(attribute);

	}

	@Override
	public List<ProductAttribute> getProductAttributesByCategoryLineage(MerchantStore store, String lineage,
			Language language) throws Exception {
		return productAttributeRepository.findOptionsByCategoryLineage(store.getId(), lineage, language.getId());
	}

}
