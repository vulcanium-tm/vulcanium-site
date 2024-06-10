package dev.vulcanium.site.tech.store.facade.category;

import java.util.List;
import dev.vulcanium.business.model.catalog.category.Category;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.site.tech.model.catalog.category.PersistableCategory;
import dev.vulcanium.site.tech.model.catalog.category.ReadableCategory;
import dev.vulcanium.site.tech.model.catalog.category.ReadableCategoryList;
import dev.vulcanium.site.tech.model.catalog.product.attribute.ReadableProductVariant;
import dev.vulcanium.site.tech.model.entity.ListCriteria;

public interface CategoryFacade {


/**
 * Returns a list of ReadableCategory ordered and built according to a given depth
 * @param store
 * @param depth
 * @param language
 * @param filter
 * @param page
 * @param count
 * @return ReadableCategoryList
 */
ReadableCategoryList getCategoryHierarchy(MerchantStore store, ListCriteria criteria, int depth, Language language, List<String> filter, int page, int count);

/**
 *
 * @param store
 * @param category
 * @return PersistableCategory
 */
PersistableCategory saveCategory(MerchantStore store, PersistableCategory category);

/**
 *
 * @param store
 * @param id
 * @param language
 * @return ReadableCategory
 */
ReadableCategory getById(MerchantStore store, Long id, Language language);

/**
 *
 * @param store
 * @param code
 * @param language
 * @return ReadableCategory
 * @throws Exception
 */
ReadableCategory getByCode(MerchantStore store, String code, Language language) throws Exception;

/**
 * Get a Category by the Search Engine friendly URL slug
 *
 * @param merchantStore
 * @param friendlyUrl
 * @param language
 * @return
 */
ReadableCategory getCategoryByFriendlyUrl(MerchantStore merchantStore, String friendlyUrl, Language language) throws Exception;

Category getByCode(String code, MerchantStore store);

void deleteCategory(Long categoryId, MerchantStore store);

void deleteCategory(Category category);


/**
 * List product options variations for a given category
 * @param categoryId
 * @param store
 * @param language
 * @return
 */
List<ReadableProductVariant> categoryProductVariants(Long categoryId, MerchantStore store, Language language);

/**
 * Check if category code already exist
 * @param store
 * @param code
 * @return
 * @throws Exception
 */
boolean existByCode(MerchantStore store, String code);

/**
 * Move a Category from a node to another node
 * @param child
 * @param parent
 * @param store
 */
void move(Long child, Long parent, MerchantStore store);

/**
 * Set category visible or not
 * @param category
 * @param store
 */
void setVisible(PersistableCategory category, MerchantStore store);


/**
 * List category by product
 * @param store
 * @param product
 * @return
 */
ReadableCategoryList listByProduct(MerchantStore store, Long product, Language language);
}
