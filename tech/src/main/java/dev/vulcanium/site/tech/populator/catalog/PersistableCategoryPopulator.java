package dev.vulcanium.site.tech.populator.catalog;

import java.util.HashSet;
import java.util.Set;
import jakarta.inject.Inject;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Component;
import dev.vulcanium.business.exception.ConversionException;
import dev.vulcanium.business.services.catalog.category.CategoryService;
import dev.vulcanium.business.services.reference.language.LanguageService;
import dev.vulcanium.business.utils.AbstractDataPopulator;
import dev.vulcanium.business.model.catalog.category.Category;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.site.tech.model.catalog.category.CategoryDescription;
import dev.vulcanium.site.tech.model.catalog.category.PersistableCategory;

@Getter
@Setter
@Component
public class PersistableCategoryPopulator extends
		AbstractDataPopulator<PersistableCategory, Category> {

@Inject
private CategoryService categoryService;
@Inject
private LanguageService languageService;

@Override
public Category populate(PersistableCategory source, Category target,
                         MerchantStore store, Language language)
		throws ConversionException {
	
	try {
		
		Validate.notNull(target, "Category target cannot be null");
		target.setMerchantStore(store);
		target.setCode(source.getCode());
		target.setSortOrder(source.getSortOrder());
		target.setVisible(source.isVisible());
		target.setFeatured(source.isFeatured());
		
		if(!CollectionUtils.isEmpty(source.getChildren())) {
		} else {
			target.getCategories().clear();
		}
		
		if(source.getParent()==null || (StringUtils.isBlank(source.getParent().getCode())) || source.getParent().getId()==null) {
			target.setParent(null);
			target.setDepth(0);
			target.setLineage("/" + source.getId() + "/");
		} else {
			Category parent;
			if(!StringUtils.isBlank(source.getParent().getCode())) {
				parent = categoryService.getByCode(store.getCode(), source.getParent().getCode());
			} else if(source.getParent().getId()!=null) {
				parent = categoryService.getById(source.getParent().getId(), store.getId());
			} else {
				throw new ConversionException("Category parent needs at least an id or a code for reference");
			}
			if(parent !=null && parent.getMerchantStore().getId().intValue()!=store.getId().intValue()) {
				throw new ConversionException("Store id does not belong to specified parent id");
			}
			
			if(parent!=null) {
				target.setParent(parent);
				
				String lineage = parent.getLineage();
				int depth = parent.getDepth();
				
				target.setDepth(depth+1);
				target.setLineage(lineage + target.getId() + "/");
			}
			
		}
		
		
		if(!CollectionUtils.isEmpty(source.getChildren())) {
			
			for(PersistableCategory cat : source.getChildren()) {
				
				Category persistCategory = this.populate(cat, new Category(), store, language);
				target.getCategories().add(persistCategory);
				
			}
			
		}
		
		
		if(!CollectionUtils.isEmpty(source.getDescriptions())) {
			Set<dev.vulcanium.business.model.catalog.category.CategoryDescription> descriptions = new HashSet<>();
			if(CollectionUtils.isNotEmpty(target.getDescriptions())) {
				for(dev.vulcanium.business.model.catalog.category.CategoryDescription description : target.getDescriptions()) {
					for(CategoryDescription d : source.getDescriptions()) {
						if(StringUtils.isBlank(d.getLanguage())) {
							throw new ConversionException("Source category description has no language");
						}
						if(d.getLanguage().equals(description.getLanguage().getCode())) {
							description.setCategory(target);
							description = buildDescription(d, description);
							descriptions.add(description);
						}
					}
				}
				
			} else {
				for(CategoryDescription d : source.getDescriptions()) {
					dev.vulcanium.business.model.catalog.category.CategoryDescription t = new dev.vulcanium.business.model.catalog.category.CategoryDescription();
					
					this.buildDescription(d, t);
					t.setCategory(target);
					descriptions.add(t);
					
				}
				
			}
			target.setDescriptions(descriptions);
		}
		
		
		return target;
		
		
	} catch(Exception e) {
		throw new ConversionException(e);
	}
	
}

private dev.vulcanium.site.tech.model.catalog.category.CategoryDescription buildDescription(dev.vulcanium.site.tech.model.catalog.category.CategoryDescription source, dev.vulcanium.business.model.catalog.category.CategoryDescription target) throws Exception {
	target.setCategoryHighlight(source.getHighlights());
	target.setDescription(source.getDescription());
	target.setName(source.getName());
	target.setMetatagDescription(source.getMetaDescription());
	target.setMetatagTitle(source.getTitle());
	target.setSeUrl(source.getFriendlyUrl());
	Language lang = languageService.getByCode(source.getLanguage());
	if(lang==null) {
		throw new ConversionException("Language is null for code " + source.getLanguage() + " use language ISO code [en, fr ...]");
	}
	target.setLanguage(lang);
	return target;
}


@Override
protected Category createTarget() {
	return null;
}

}
