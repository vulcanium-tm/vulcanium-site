package dev.vulcanium.site.tech.mapper.catalog.product;

import dev.vulcanium.business.model.catalog.product.Product;
import dev.vulcanium.business.model.catalog.product.availability.ProductAvailability;
import dev.vulcanium.business.model.catalog.product.variant.ProductVariant;
import dev.vulcanium.business.model.catalog.product.variation.ProductVariation;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.business.services.catalog.product.ProductService;
import dev.vulcanium.business.services.catalog.product.variation.ProductVariationService;
import dev.vulcanium.business.utils.DateUtil;
import dev.vulcanium.site.tech.mapper.Mapper;
import dev.vulcanium.site.tech.model.catalog.product.variant.PersistableProductVariant;
import dev.vulcanium.site.tech.store.api.exception.OperationNotAllowedException;
import dev.vulcanium.site.tech.store.api.exception.ResourceNotFoundException;
import dev.vulcanium.site.tech.store.api.exception.ServiceRuntimeException;
import java.util.Date;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PersistableProductVariantMapper implements Mapper<PersistableProductVariant, ProductVariant> {

@Autowired
private ProductVariationService productVariationService;

@Autowired
private PersistableProductAvailabilityMapper persistableProductAvailabilityMapper;

@Autowired
private ProductService productService;

@Override
public ProductVariant convert(PersistableProductVariant source, MerchantStore store, Language language) {
	ProductVariant productVariantModel = new ProductVariant();
	return this.merge(source, productVariantModel, store, language);
}

@Override
public ProductVariant merge(PersistableProductVariant source, ProductVariant destination, MerchantStore store,
                            Language language) {
	
	Long productVariation = source.getVariation();
	Long productVariationValue = source.getVariationValue();
	
	String productVariationCode = source.getVariationCode();
	String productVariationValueCode = source.getVariationValueCode();
	
	Optional<ProductVariation> variation = null;
	Optional<ProductVariation> variationValue = null;
	
	if(StringUtils.isEmpty(productVariationCode)) {
		
		variation = productVariationService.getById(store, productVariation);
		if(productVariationValue != null) {
			variationValue = productVariationService.getById(store, productVariationValue);
			if(variationValue.isEmpty()) {
				throw new ResourceNotFoundException("ProductVaritionValue [" + productVariationValue + "] + not found for store [" + store.getCode() + "]");
			}
			
		}
	} else {
		variation = productVariationService.getByCode(store, productVariationCode);
		if(productVariationValueCode != null) {
			variationValue = productVariationService.getByCode(store, productVariationValueCode);
			if(variationValue.isEmpty()) {
				throw new ResourceNotFoundException("ProductVaritionValue [" + productVariationValue + "] + not found for store [" + store.getCode() + "]");
			}
			
		}
	}
	
	
	if(variation.isEmpty()) {
		throw new ResourceNotFoundException("ProductVarition [" + productVariation + "] + not found for store [" + store.getCode() + "]");
	}
	
	destination.setVariation(variation.get());
	
	
	if(productVariationValue != null) {
		destination.setVariationValue(variationValue.get());
	}
	
	StringBuilder instanceCode = new StringBuilder();
	instanceCode.append(variation.get().getCode());
	if(productVariationValue != null && variationValue.get()!=null) {
		instanceCode.append(":").append(variationValue.get().getCode());
	}
	
	destination.setCode(instanceCode.toString());
	
	destination.setAvailable(source.isAvailable());
	destination.setDefaultSelection(source.isDefaultSelection());
	destination.setSku(source.getSku());
	
	if(StringUtils.isBlank(source.getDateAvailable())) {
		source.setDateAvailable(DateUtil.formatDate(new Date()));
	}
	
	if(source.getDateAvailable()!=null) {
		try {
			destination.setDateAvailable(DateUtil.getDate(source.getDateAvailable()));
		} catch (Exception e) {
			throw new ServiceRuntimeException("Cant format date [" + source.getDateAvailable() + "]");
		}
	}
	
	destination.setSortOrder(source.getSortOrder());
	
	
	/**
	 * Inventory
	 */
	if(source.getInventory() != null) {
		ProductAvailability availability = persistableProductAvailabilityMapper.convert(source.getInventory(), store, language);
		availability.setProductVariant(destination);
		destination.getAvailabilities().add(availability);
	}
	
	
	Product product = null;
	
	if(source.getProductId() != null && source.getProductId().longValue() > 0) {
		product = productService.findOne(source.getProductId(), store);
		
		if(product == null) {
			throw new ResourceNotFoundException("Product [" + source.getId() + "] + not found for store [" + store.getCode() + "]");
		}
		
		if(product.getMerchantStore().getId() != store.getId()) {
			throw new ResourceNotFoundException("Product [" + source.getId() + "] + not found for store [" + store.getCode() + "]");
		}
		
		if(product.getSku() != null && product.getSku().equals(source.getSku())) {
			throw new OperationNotAllowedException("Product variant sku [" + source.getSku() + "] + must be different than product instance sku [" + product.getSku() + "]");
		}
		
		destination.setProduct(product);
		
		
	}
	
	
	
	return destination;
	
}

}
