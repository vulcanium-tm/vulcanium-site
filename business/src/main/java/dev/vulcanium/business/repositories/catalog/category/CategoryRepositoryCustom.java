package dev.vulcanium.business.repositories.catalog.category;

import dev.vulcanium.business.model.catalog.category.Category;
import dev.vulcanium.business.model.merchant.MerchantStore;
import java.util.List;

public interface CategoryRepositoryCustom {

	List<Object[]> countProductsByCategories(MerchantStore store,
			List<Long> categoryIds);

	List<Category> listByStoreAndParent(MerchantStore store, Category category);
	
	List<Category> listByProduct(MerchantStore store, Long product);

}
