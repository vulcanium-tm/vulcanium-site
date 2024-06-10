package dev.vulcanium.site.tech.mapper.catalog;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;
import dev.vulcanium.business.model.catalog.product.attribute.ProductOption;
import dev.vulcanium.business.model.catalog.product.attribute.ProductOptionDescription;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.site.tech.mapper.Mapper;
import dev.vulcanium.site.tech.model.catalog.product.attribute.api.ReadableProductOptionEntity;
import dev.vulcanium.site.tech.model.catalog.product.attribute.api.ReadableProductOptionFull;

@Component
public class ReadableProductOptionMapper implements Mapper<ProductOption, ReadableProductOptionEntity> {

@Override
public ReadableProductOptionEntity convert(ProductOption source, MerchantStore store,
                                           Language language) {
	ReadableProductOptionEntity destination = new ReadableProductOptionEntity();
	return merge(source, destination, store, language);
}


@Override
public ReadableProductOptionEntity merge(ProductOption source,
                                         ReadableProductOptionEntity destination, MerchantStore store, Language language) {
	ReadableProductOptionEntity readableProductOption = new ReadableProductOptionEntity();
	if(language == null) {
		readableProductOption = new ReadableProductOptionFull();
		List<dev.vulcanium.business.model.catalog.product.attribute.ProductOptionDescription> descriptions = new ArrayList<dev.vulcanium.business.model.catalog.product.attribute.ProductOptionDescription>();
		for(ProductOptionDescription desc : source.getDescriptions()) {
			dev.vulcanium.business.model.catalog.product.attribute.ProductOptionDescription d = this.description(desc);
			descriptions.add(d);
		}
		((ReadableProductOptionFull)readableProductOption).setDescriptions(descriptions);
	} else {
		readableProductOption = new ReadableProductOptionEntity();
		if(!CollectionUtils.isEmpty(source.getDescriptions())) {
			for(ProductOptionDescription desc : source.getDescriptions()) {
				if(desc != null && desc.getLanguage()!= null && desc.getLanguage().getId() == language.getId()) {
					dev.vulcanium.business.model.catalog.product.attribute.ProductOptionDescription d = this.description(desc);
					readableProductOption.setDescription(d);
				}
			}
		}
	}
	
	readableProductOption.setCode(source.getCode());
	readableProductOption.setId(source.getId());
	readableProductOption.setType(source.getProductOptionType());
	
	
	return readableProductOption;
}



dev.vulcanium.business.model.catalog.product.attribute.ProductOptionDescription description(ProductOptionDescription description) {
	dev.vulcanium.business.model.catalog.product.attribute.ProductOptionDescription desc = new dev.vulcanium.business.model.catalog.product.attribute.ProductOptionDescription();
	desc.setDescription(description.getDescription());
	desc.setName(description.getName());
	desc.setId(description.getId());
	desc.setLanguage(description.getLanguage().getCode());
	return desc;
}

}