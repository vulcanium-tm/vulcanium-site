package dev.vulcanium.business.services.catalog.product.attribute;

import dev.vulcanium.business.exception.ServiceException;
import dev.vulcanium.business.model.catalog.product.attribute.ProductOptionSet;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.business.repositories.catalog.product.attribute.ProductOptionSetRepository;
import dev.vulcanium.business.services.common.generic.SalesManagerEntityServiceImpl;
import java.util.List;
import jakarta.inject.Inject;
import org.springframework.stereotype.Service;

@Service("productOptionSetService")
public class ProductOptionSetServiceImpl extends
		SalesManagerEntityServiceImpl<Long, ProductOptionSet> implements ProductOptionSetService {

	
	private ProductOptionSetRepository productOptionSetRepository;
	

	@Inject
	public ProductOptionSetServiceImpl(
			ProductOptionSetRepository productOptionSetRepository) {
			super(productOptionSetRepository);
			this.productOptionSetRepository = productOptionSetRepository;
	}


	@Override
	public List<ProductOptionSet> listByStore(MerchantStore store, Language language) throws ServiceException {
		return productOptionSetRepository.findByStore(store.getId(), language.getId());
	}


	@Override
	public ProductOptionSet getById(MerchantStore store, Long optionSetId, Language lang) {
		return productOptionSetRepository.findOne(store.getId(), optionSetId, lang.getId());
	}


	@Override
	public ProductOptionSet getCode(MerchantStore store, String code) {
		return productOptionSetRepository.findByCode(store.getId(), code);
	}


	@Override
	public List<ProductOptionSet> getByProductType(Long productTypeId, MerchantStore store, Language lang) {
		return productOptionSetRepository.findByProductType(productTypeId, store.getId(), lang.getId());
	}



}
