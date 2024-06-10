package dev.vulcanium.site.tech.mapper.catalog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.jsoup.helper.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dev.vulcanium.business.model.catalog.product.attribute.ProductOption;
import dev.vulcanium.business.model.catalog.product.attribute.ProductOptionDescription;
import dev.vulcanium.business.model.catalog.product.attribute.ProductOptionSet;
import dev.vulcanium.business.model.catalog.product.attribute.ProductOptionValue;
import dev.vulcanium.business.model.catalog.product.attribute.ProductOptionValueDescription;
import dev.vulcanium.business.model.catalog.product.type.ProductType;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.site.tech.mapper.Mapper;
import dev.vulcanium.site.tech.model.catalog.product.attribute.ReadableProductOption;
import dev.vulcanium.site.tech.model.catalog.product.attribute.ReadableProductOptionValue;
import dev.vulcanium.site.tech.model.catalog.product.attribute.optionset.ReadableProductOptionSet;
import dev.vulcanium.site.tech.model.catalog.product.type.ReadableProductType;

@Component
public class ReadableProductOptionSetMapper implements Mapper<ProductOptionSet, ReadableProductOptionSet> {


@Autowired
private ReadableProductTypeMapper readableProductTypeMapper;

@Override
public ReadableProductOptionSet convert(ProductOptionSet source, MerchantStore store, Language language) {
	ReadableProductOptionSet optionSource = new ReadableProductOptionSet();
	return merge(source, optionSource, store, language);
}

@Override
public ReadableProductOptionSet merge(ProductOptionSet source, ReadableProductOptionSet destination,
                                      MerchantStore store, Language language) {
	Validate.notNull(source,"ProductOptionSet must not be null");
	Validate.notNull(destination,"ReadableProductOptionSet must not be null");
	
	
	destination.setId(source.getId());
	destination.setCode(source.getCode());
	destination.setReadOnly(source.isOptionDisplayOnly());
	
	destination.setOption(this.option(source.getOption(), store, language));
	
	List<Long> ids = new ArrayList<Long>();
	
	if(!CollectionUtils.isEmpty(source.getValues())) {
		List<ReadableProductOptionValue> values = source.getValues().stream().map(val -> optionValue(ids, val, store, language)).collect(Collectors.toList());
		destination.setValues(values);
		destination.getValues().removeAll(Collections.singleton(null));
	}
	
	if(!CollectionUtils.isEmpty(source.getProductTypes())) {
		List<ReadableProductType> types = source.getProductTypes().stream().map( t -> this.productType(t, store, language)).collect(Collectors.toList());
		destination.setProductTypes(types);
	}
	
	
	return destination;
}

private ReadableProductOption option (ProductOption option, MerchantStore store, Language lang) {
	
	ReadableProductOption opt = new ReadableProductOption();
	opt.setCode(option.getCode());
	opt.setId(option.getId());
	opt.setLang(lang.getCode());
	opt.setReadOnly(option.isReadOnly());
	opt.setType(option.getProductOptionType());
	ProductOptionDescription desc = this.optionDescription(option.getDescriptions(), lang);
	if(desc != null) {
		opt.setName(desc.getName());
	}
	
	return opt;
}

private ReadableProductOptionValue optionValue (List<Long> ids, ProductOptionValue optionValue, MerchantStore store, Language language) {
	
	if(!ids.contains(optionValue.getId())) {
		ReadableProductOptionValue value = new ReadableProductOptionValue();
		value.setCode(optionValue.getCode());
		value.setId(optionValue.getId());
		ProductOptionValueDescription desc = optionValueDescription(optionValue.getDescriptions(), language);
		if(desc!=null) {
			value.setName(desc.getName());
		}
		ids.add(optionValue.getId());
		return value;
	} else {
		return null;
	}
}

private ProductOptionDescription optionDescription(Set<ProductOptionDescription> descriptions, Language lang) {
	return descriptions.stream().filter(desc-> desc.getLanguage().getCode().equals(lang.getCode())).findAny().orElse(null);
}

private ProductOptionValueDescription optionValueDescription(Set<ProductOptionValueDescription> descriptions, Language lang) {
	return descriptions.stream().filter(desc-> desc.getLanguage().getCode().equals(lang.getCode())).findAny().orElse(null);
}

private ReadableProductType productType(ProductType type, MerchantStore store, Language language) {
	return readableProductTypeMapper.convert(type, store, language);
}

}
