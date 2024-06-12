package dev.vulcanium.site.tech.mapper.catalog;

import dev.vulcanium.business.model.catalog.product.attribute.ProductOptionValue;
import dev.vulcanium.business.model.catalog.product.attribute.ProductOptionValueDescription;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.business.utils.ImageFilePath;
import dev.vulcanium.site.tech.mapper.Mapper;
import dev.vulcanium.site.tech.model.catalog.product.attribute.api.ReadableProductOptionValue;
import dev.vulcanium.site.tech.model.catalog.product.attribute.api.ReadableProductOptionValueFull;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class ReadableProductOptionValueMapper implements Mapper<ProductOptionValue, ReadableProductOptionValue> {

@Autowired
@Qualifier("img")
private ImageFilePath imageUtils;

@Override
public ReadableProductOptionValue merge(ProductOptionValue source, ReadableProductOptionValue destination,
                                        MerchantStore store, Language language) {
	ReadableProductOptionValue readableProductOptionValue = new ReadableProductOptionValue();
	if(language == null) {
		readableProductOptionValue = new ReadableProductOptionValueFull();
		List<dev.vulcanium.business.model.catalog.product.attribute.ProductOptionValueDescription> descriptions = new ArrayList<dev.vulcanium.business.model.catalog.product.attribute.ProductOptionValueDescription>();
		for(ProductOptionValueDescription desc : source.getDescriptions()) {
			dev.vulcanium.business.model.catalog.product.attribute.ProductOptionValueDescription d = this.description(desc);
			descriptions.add(d);
		}
		((ReadableProductOptionValueFull)readableProductOptionValue).setDescriptions(descriptions);
	} else {
		readableProductOptionValue = new ReadableProductOptionValue();
		if(!CollectionUtils.isEmpty(source.getDescriptions())) {
			for(ProductOptionValueDescription desc : source.getDescriptions()) {
				if(desc != null && desc.getLanguage()!= null && desc.getLanguage().getId() == language.getId()) {
					dev.vulcanium.business.model.catalog.product.attribute.ProductOptionValueDescription d = this.description(desc);
					readableProductOptionValue.setDescription(d);
				}
			}
		}
	}
	
	readableProductOptionValue.setCode(source.getCode());
	if(source.getId()!=null) {
		readableProductOptionValue.setId(source.getId().longValue());
	}
	if(source.getProductOptionValueSortOrder()!=null) {
		readableProductOptionValue.setOrder(source.getProductOptionValueSortOrder().intValue());
	}
	if(!StringUtils.isBlank(source.getProductOptionValueImage())) {
		readableProductOptionValue.setImage(imageUtils.buildProductPropertyImageUtils(store, source.getProductOptionValueImage()));
	}
	
	return readableProductOptionValue;
}



dev.vulcanium.business.model.catalog.product.attribute.ProductOptionValueDescription description(ProductOptionValueDescription description) {
	dev.vulcanium.business.model.catalog.product.attribute.ProductOptionValueDescription desc = new dev.vulcanium.business.model.catalog.product.attribute.ProductOptionValueDescription();
	desc.setDescription(description.getDescription());
	desc.setName(description.getName());
	desc.setId(description.getId());
	desc.setLanguage(description.getLanguage().getCode());
	return desc;
}


@Override
public ReadableProductOptionValue convert(ProductOptionValue source, MerchantStore store, Language language) {
	ReadableProductOptionValue destination = new ReadableProductOptionValue();
	return merge(source, destination, store, language);
}

}