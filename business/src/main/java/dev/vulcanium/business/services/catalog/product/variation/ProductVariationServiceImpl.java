package dev.vulcanium.business.services.catalog.product.variation;

import dev.vulcanium.business.exception.ServiceException;
import dev.vulcanium.business.model.catalog.product.variation.ProductVariation;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.business.repositories.catalog.product.variation.PageableProductVariationRepository;
import dev.vulcanium.business.repositories.catalog.product.variation.ProductVariationRepository;
import dev.vulcanium.business.services.common.generic.SalesManagerEntityServiceImpl;
import java.util.List;
import java.util.Optional;
import jakarta.inject.Inject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service("productVariationeService")
public class ProductVariationServiceImpl extends
		SalesManagerEntityServiceImpl<Long, ProductVariation> implements
		ProductVariationService {

	@Inject
	private ProductVariationRepository productVariationRepository;
	
	@Inject
	public ProductVariationServiceImpl(
			ProductVariationRepository productVariationSetRepository) {
		super(productVariationSetRepository);
		this.productVariationRepository = productVariationSetRepository;
	}


	@Autowired
	private PageableProductVariationRepository pageableProductVariationSetRepository;


	@Override
	public Optional<ProductVariation> getById(MerchantStore store, Long id, Language lang) {
		return productVariationRepository.findOne(store.getId(), id, lang.getId());
	}
	
	@Override
	public Optional<ProductVariation> getByCode(MerchantStore store, String code) {
		return productVariationRepository.findByCode(code, store.getId());
	}



	@Override
	public Page<ProductVariation> getByMerchant(MerchantStore store, Language language, String code, int page,
			int count) {
		Pageable p = PageRequest.of(page, count);
		return pageableProductVariationSetRepository.list(store.getId(), code, p);
	}

	@Override
	public Optional<ProductVariation> getById(MerchantStore store, Long id) {
		return productVariationRepository.findOne(store.getId(), id);
	}
	
	@Override
	public void saveOrUpdate(ProductVariation entity) throws ServiceException {

		//save or update (persist and attach entities
		if(entity.getId()!=null && entity.getId()>0) {

			super.update(entity);
			
		} else {
			
			super.save(entity);
			
		}
		
	}

	@Override
	public List<ProductVariation> getByIds(List<Long> ids, MerchantStore store) {
		return productVariationRepository.findByIds(store.getId(), ids);
	}



}
