package dev.vulcanium.business.repositories.catalog.category;

import dev.vulcanium.business.model.catalog.category.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PageableCategoryRepositoryCustom {
	
	Page<Category> listByStore(Integer storeId, Integer languageId, String name, Pageable pageable);

}
