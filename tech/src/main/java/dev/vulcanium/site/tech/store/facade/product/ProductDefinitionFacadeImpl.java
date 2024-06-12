package dev.vulcanium.site.tech.store.facade.product;

import dev.vulcanium.business.exception.ServiceException;
import dev.vulcanium.business.model.catalog.product.Product;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.business.services.catalog.product.ProductService;
import dev.vulcanium.business.store.api.exception.ResourceNotFoundException;
import dev.vulcanium.business.store.api.exception.ServiceRuntimeException;
import dev.vulcanium.business.utils.ImageFilePath;
import dev.vulcanium.site.tech.mapper.catalog.product.PersistableProductDefinitionMapper;
import dev.vulcanium.site.tech.mapper.catalog.product.ReadableProductDefinitionMapper;
import dev.vulcanium.site.tech.model.catalog.product.definition.PersistableProductDefinition;
import dev.vulcanium.site.tech.model.catalog.product.definition.ReadableProductDefinition;
import jakarta.inject.Inject;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service("productDefinitionFacade")
@Profile({ "default", "cloud", "gcp", "aws", "mysql", "local" })
public class ProductDefinitionFacadeImpl implements ProductDefinitionFacade {



@Inject
private ProductService productService;


@Autowired
private PersistableProductDefinitionMapper persistableProductDefinitionMapper;

@Autowired
private ReadableProductDefinitionMapper readableProductDefinitionMapper;

@Autowired
private ProductVariantFacade productVariantFacade;

@Inject
@Qualifier("img")
private ImageFilePath imageUtils;

@Override
public Long saveProductDefinition(MerchantStore store, PersistableProductDefinition product, Language language) {
	
	
	Product target = null;
	if (product.getId() != null && product.getId().longValue() > 0) {
		Optional<Product> p = productService.retrieveById(product.getId(), store);
		if(p.isEmpty()) {
			throw new ResourceNotFoundException("Product with id [" + product.getId() + "] not found for store [" + store.getCode() + "]");
		}
		target = p.get();
	} else {
		target = new Product();
	}
	
	try {
		target = persistableProductDefinitionMapper.merge(product, target, store, language);
		
		productService.saveProduct(target);
		product.setId(target.getId());
		
		
		return target.getId();
	} catch (Exception e) {
		throw new ServiceRuntimeException(e);
	}
	
}

@Override
public void update(Long id, PersistableProductDefinition product, MerchantStore merchant,
                   Language language) {
	product.setId(id);
	this.saveProductDefinition(merchant, product, language);
}

@Override
public ReadableProductDefinition getProduct(MerchantStore store, Long id, Language language) {
	Product product = productService.findOne(id, store);
	return readableProductDefinitionMapper.convert(product, store, language);
}

@Override
public ReadableProductDefinition getProductBySku(MerchantStore store, String uniqueCode, Language language) {
	
	Product product = null;
	try {
		product = productService.getBySku(uniqueCode, store, language);
	} catch (ServiceException e) {
		throw new ServiceRuntimeException(e);
	}
	return readableProductDefinitionMapper.convert(product, store, language);
	
}

}
