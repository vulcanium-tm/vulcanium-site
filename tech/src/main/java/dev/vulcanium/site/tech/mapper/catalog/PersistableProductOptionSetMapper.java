package dev.vulcanium.site.tech.mapper.catalog;

import dev.vulcanium.business.exception.ServiceException;
import dev.vulcanium.business.model.catalog.product.attribute.ProductOption;
import dev.vulcanium.business.model.catalog.product.attribute.ProductOptionSet;
import dev.vulcanium.business.model.catalog.product.attribute.ProductOptionValue;
import dev.vulcanium.business.model.catalog.product.type.ProductType;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.business.services.catalog.product.attribute.ProductOptionService;
import dev.vulcanium.business.services.catalog.product.attribute.ProductOptionValueService;
import dev.vulcanium.business.services.catalog.product.type.ProductTypeService;
import dev.vulcanium.business.store.api.exception.ConversionRuntimeException;
import dev.vulcanium.site.tech.mapper.Mapper;
import dev.vulcanium.site.tech.model.catalog.product.attribute.optionset.PersistableProductOptionSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PersistableProductOptionSetMapper implements Mapper<PersistableProductOptionSet, ProductOptionSet> {

@Autowired
private ProductOptionService productOptionService;

@Autowired
private ProductOptionValueService productOptionValueService;

@Autowired
private ProductTypeService productTypeService;

@Override
public ProductOptionSet convert(PersistableProductOptionSet source, MerchantStore store, Language language) {
	
	
	ProductOptionSet optionSet = new ProductOptionSet();
	return this.merge(source, optionSet, store, language);
	
}

private ProductOptionValue value(Long productOptionValue, MerchantStore store) {
	return productOptionValueService.getById(store, productOptionValue);
}

@Override
public ProductOptionSet merge(PersistableProductOptionSet source, ProductOptionSet destination,
                              MerchantStore store, Language language) {
	Validate.notNull(destination, "ProductOptionSet must not be null");
	
	destination.setId(source.getId());
	destination.setCode(source.getCode());
	destination.setOptionDisplayOnly(source.isReadOnly());
	
	ProductOption option = productOptionService.getById(store, source.getOption());
	destination.setOption(option);
	
	if(!CollectionUtils.isEmpty(source.getOptionValues())) {
		List<ProductOptionValue> values = source.getOptionValues().stream().map(id -> value(id, store)).collect(Collectors.toList());
		destination.setValues(values);
	}
	
	if(!CollectionUtils.isEmpty(source.getProductTypes())) {
		try {
			List<ProductType> types = productTypeService.listProductTypes(source.getProductTypes(), store, language);
			Set<ProductType> typesSet = new HashSet<ProductType>(types);
			destination.setProductTypes(typesSet);
		} catch (ServiceException e) {
			throw new ConversionRuntimeException("Error while mpping ProductOptionSet", e);
		}
	}
	
	return destination;
}

}
