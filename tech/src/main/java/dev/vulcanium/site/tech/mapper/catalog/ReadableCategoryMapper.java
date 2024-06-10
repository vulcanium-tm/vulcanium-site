package dev.vulcanium.site.tech.mapper.catalog;

import dev.vulcanium.site.tech.model.catalog.category.ReadableCategory;
import java.util.*;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import dev.vulcanium.business.model.catalog.category.Category;
import dev.vulcanium.business.model.catalog.category.CategoryDescription;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.site.tech.mapper.Mapper;
import dev.vulcanium.site.tech.model.catalog.category.ReadableCategoryFull;

@Component
public class ReadableCategoryMapper implements Mapper<Category, ReadableCategory> {

private static final Logger LOGGER = LoggerFactory.getLogger(ReadableCategoryMapper.class);

public ReadableCategory convert(Category source, MerchantStore store, Language language) {
	
	if (Objects.isNull(language)) {
		ReadableCategoryFull target = new ReadableCategoryFull();
		List<dev.vulcanium.site.tech.model.catalog.category.CategoryDescription> descriptions = source.getDescriptions().stream()
				                                                                                      .map(this::convertDescription)
				                                                                                      .collect(Collectors.toList());
		target.setDescriptions(descriptions);
		fillReadableCategory(target, source);
		return target;
	} else {
		ReadableCategory target = new ReadableCategory();
		Optional<dev.vulcanium.site.tech.model.catalog.category.CategoryDescription> description = source.getDescriptions().stream()
				                                                                                         .filter(d -> language.getId().equals(d.getLanguage().getId()))
				                                                                                         .map(this::convertDescription)
				                                                                                         .findAny();
		description.ifPresent(target::setDescription);
		fillReadableCategory(target, source);
		return target;
	}
}

private void fillReadableCategory(ReadableCategory target, Category source) {
	Optional<dev.vulcanium.site.tech.model.catalog.category.Category> parentCategory =
			createParentCategory(source);
	parentCategory.ifPresent(target::setParent);
	
	Optional.ofNullable(source.getDepth()).ifPresent(target::setDepth);
	
	target.setLineage(source.getLineage());
	target.setStore(source.getMerchantStore().getCode());
	target.setCode(source.getCode());
	target.setId(source.getId());
	target.setSortOrder(source.getSortOrder());
	target.setVisible(source.isVisible());
	target.setFeatured(source.isFeatured());
}

private dev.vulcanium.site.tech.model.catalog.category.CategoryDescription convertDescription(
		CategoryDescription description) {
	final dev.vulcanium.site.tech.model.catalog.category.CategoryDescription desc =
			new dev.vulcanium.site.tech.model.catalog.category.CategoryDescription();
	
	desc.setFriendlyUrl(description.getSeUrl());
	desc.setName(description.getName());
	desc.setId(description.getId());
	desc.setDescription(description.getDescription());
	desc.setKeyWords(description.getMetatagKeywords());
	desc.setHighlights(description.getCategoryHighlight());
	desc.setLanguage(description.getLanguage().getCode());
	desc.setTitle(description.getMetatagTitle());
	desc.setMetaDescription(description.getMetatagDescription());
	return desc;
}


private Optional<dev.vulcanium.site.tech.model.catalog.category.Category> createParentCategory(
		Category source) {
	return Optional.ofNullable(source.getParent()).map(parentValue -> {
		final dev.vulcanium.site.tech.model.catalog.category.Category parent =
				new dev.vulcanium.site.tech.model.catalog.category.Category();
		parent.setCode(source.getParent().getCode());
		parent.setId(source.getParent().getId());
		return parent;
	});
}

@Override
public ReadableCategory merge(Category source, ReadableCategory destination,
                              MerchantStore store, Language language) {
	return destination;
}
}
