package dev.vulcanium.business.services.catalog.product.price;

import dev.vulcanium.business.exception.ServiceException;
import dev.vulcanium.business.model.catalog.product.price.ProductPrice;
import dev.vulcanium.business.model.catalog.product.price.ProductPriceDescription;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.repositories.catalog.product.price.ProductPriceRepository;
import dev.vulcanium.business.services.common.generic.SalesManagerEntityServiceImpl;
import java.util.List;
import jakarta.inject.Inject;
import org.springframework.stereotype.Service;

@Service("productPrice")
public class ProductPriceServiceImpl extends SalesManagerEntityServiceImpl<Long, ProductPrice> 
	implements ProductPriceService {
	
	private ProductPriceRepository productPriceRepository;

	@Inject
	public ProductPriceServiceImpl(ProductPriceRepository productPriceRepository) {
		super(productPriceRepository);
		this.productPriceRepository = productPriceRepository;
	}

	@Override
	public void addDescription(ProductPrice price,
			ProductPriceDescription description) throws ServiceException {
		price.getDescriptions().add(description);
		update(price);
	}
	
	
	@Override
	public ProductPrice saveOrUpdate(ProductPrice price) throws ServiceException {
		
		
		ProductPrice returnEntity = productPriceRepository.save(price);

		return returnEntity;


	}
	
	@Override
	public void delete(ProductPrice price) throws ServiceException {
		
		//override method, this allows the error that we try to remove a detached variant
		price = this.getById(price.getId());
		super.delete(price);
		
	}

	@Override
	public List<ProductPrice> findByProductSku(String sku, MerchantStore store) {

		return productPriceRepository.findByProduct(sku, store.getCode());
	}

	@Override
	public ProductPrice findById(Long priceId, String sku, MerchantStore store) {
		
		return productPriceRepository.findByProduct(sku, priceId, store.getCode());
	}

	@Override
	public List<ProductPrice> findByInventoryId(Long productInventoryId, String sku, MerchantStore store) {

		return productPriceRepository.findByProductInventoty(sku, productInventoryId, store.getCode());
	}
	


}
