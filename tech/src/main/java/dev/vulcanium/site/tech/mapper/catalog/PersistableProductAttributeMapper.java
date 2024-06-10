package dev.vulcanium.site.tech.mapper.catalog;

import java.util.UUID;

import jakarta.inject.Inject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Component;

import dev.vulcanium.business.exception.ServiceException;
import dev.vulcanium.business.services.catalog.product.ProductService;
import dev.vulcanium.business.services.catalog.product.attribute.ProductOptionService;
import dev.vulcanium.business.services.catalog.product.attribute.ProductOptionValueService;
import dev.vulcanium.business.model.catalog.product.Product;
import dev.vulcanium.business.model.catalog.product.attribute.ProductAttribute;
import dev.vulcanium.business.model.catalog.product.attribute.ProductOption;
import dev.vulcanium.business.model.catalog.product.attribute.ProductOptionValue;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.site.tech.mapper.Mapper;
import dev.vulcanium.site.tech.model.catalog.product.attribute.PersistableProductAttribute;
import dev.vulcanium.site.tech.store.api.exception.ConversionRuntimeException;

@Component
public class PersistableProductAttributeMapper implements Mapper<PersistableProductAttribute, ProductAttribute> {

@Inject
private ProductOptionService productOptionService;
@Inject
private ProductOptionValueService productOptionValueService;
@Inject
private ProductService productService;
@Inject
private PersistableProductOptionValueMapper persistableProductOptionValueMapper;

@Override
public ProductAttribute convert(PersistableProductAttribute source, MerchantStore store, Language language) {
	ProductAttribute attribute = new ProductAttribute();
	return merge(source,attribute,store,language);
}

@Override
public ProductAttribute merge(PersistableProductAttribute source, ProductAttribute destination,
                              MerchantStore store, Language language) {
	
	
	ProductOption productOption = null;
	
	if(!StringUtils.isBlank(source.getOption().getCode())) {
		productOption = productOptionService.getByCode(store, source.getOption().getCode());
	} else {
		Validate.notNull(source.getOption().getId(),"Product option id is null");
		productOption = productOptionService.getById(source.getOption().getId());
	}
	
	if(productOption==null) {
		throw new ConversionRuntimeException("Product option id " + source.getOption().getId() + " does not exist");
	}
	
	ProductOptionValue productOptionValue = null;
	
	if(!StringUtils.isBlank(source.getOptionValue().getCode())) {
		productOptionValue = productOptionValueService.getByCode(store, source.getOptionValue().getCode());
	} else if(source.getProductId() != null && source.getOptionValue().getId().longValue()>0) {
		productOptionValue = productOptionValueService.getById(source.getOptionValue().getId());
	} else {
		productOptionValue = new ProductOptionValue();
		productOptionValue.setProductOptionDisplayOnly(true);
		productOptionValue.setCode(UUID.randomUUID().toString());
		productOptionValue.setMerchantStore(store);
	}
	
	if(!CollectionUtils.isEmpty((source.getOptionValue().getDescriptions()))) {
		productOptionValue =  persistableProductOptionValueMapper.merge(source.getOptionValue(),productOptionValue, store, language);
		try {
			productOptionValueService.saveOrUpdate(productOptionValue);
		} catch (ServiceException e) {
			throw new ConversionRuntimeException("Error converting ProductOptionValue",e);
		}
	}
	
	if(productOptionValue==null && ! source.isAttributeDisplayOnly()) {
		throw new ConversionRuntimeException("Product option value id " + source.getOptionValue().getId() + " does not exist");
	}
	
	
	
	/**
	 productOptionValue
	 .getDescriptions().stream()
	 .map(val -> this.persistableProductOptionValueMapper.convert(val, store, language)).collect(Collectors.toList());
		
	 }**/
	
	if(productOption.getMerchantStore().getId().intValue()!=store.getId().intValue()) {
		throw new ConversionRuntimeException("Invalid product option id ");
	}
	
	if(productOptionValue!=null && productOptionValue.getMerchantStore().getId().intValue()!=store.getId().intValue()) {
		throw new ConversionRuntimeException("Invalid product option value id ");
	}
	
	if(source.getProductId() != null && source.getProductId().longValue() >0 ) {
		Product p = productService.getById(source.getProductId());
		if(p == null) {
			throw new ConversionRuntimeException("Invalid product id ");
		}
		destination.setProduct(p);
	}
	
	
	if(destination.getId()!=null && destination.getId().longValue()>0) {
		destination.setId(destination.getId());
	} else {
		destination.setId(null);
	}
	
	destination.setProductOption(productOption);
	destination.setProductOptionValue(productOptionValue);
	destination.setProductAttributePrice(source.getProductAttributePrice());
	destination.setProductAttributeWeight(source.getProductAttributeWeight());
	destination.setProductAttributePrice(source.getProductAttributePrice());
	destination.setAttributeDisplayOnly(source.isAttributeDisplayOnly());
	
	
	return destination;
}

}