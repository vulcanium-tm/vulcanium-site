package dev.vulcanium.site.tech.mapper.catalog;

import dev.vulcanium.site.tech.model.catalog.ReadableCatalogCategoryEntry;
import dev.vulcanium.site.tech.model.catalog.category.ReadableCategory;
import dev.vulcanium.site.tech.store.api.exception.ConversionRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import dev.vulcanium.business.model.catalog.CatalogCategoryEntry;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.site.tech.mapper.Mapper;
import dev.vulcanium.site.tech.utils.ImageFilePath;

import java.util.Optional;

@Component
public class ReadableCatalogCategoryEntryMapper implements Mapper<CatalogCategoryEntry, ReadableCatalogCategoryEntry> {


@Autowired
private ReadableCategoryMapper readableCategoryMapper;

@Autowired
@Qualifier("img")
private ImageFilePath imageUtils;

@Override
public ReadableCatalogCategoryEntry convert(CatalogCategoryEntry source, MerchantStore store, Language language) {
	ReadableCatalogCategoryEntry destination = new ReadableCatalogCategoryEntry();
	return merge(source, destination, store, language);
}

@Override
public ReadableCatalogCategoryEntry merge(CatalogCategoryEntry source, ReadableCatalogCategoryEntry destination, MerchantStore store,
                                          Language language) {
	ReadableCatalogCategoryEntry convertedDestination = Optional.ofNullable(destination)
			                                                    .orElse(new ReadableCatalogCategoryEntry());
	
	try {
		ReadableCategory readableCategory = readableCategoryMapper.convert(source.getCategory(), store, language);
		
		convertedDestination.setCatalog(source.getCatalog().getCode());
		
		convertedDestination.setId(source.getId());
		convertedDestination.setVisible(source.isVisible());
		convertedDestination.setCategory(readableCategory);
		//destination.setProduct(readableProduct);
		return convertedDestination;
	} catch (Exception e) {
		throw new ConversionRuntimeException("Error while creating ReadableCatalogEntry", e);
	}
}
}
