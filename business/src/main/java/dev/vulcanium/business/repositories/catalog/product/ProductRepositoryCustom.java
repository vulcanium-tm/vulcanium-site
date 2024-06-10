package dev.vulcanium.business.repositories.catalog.product;

import dev.vulcanium.business.model.catalog.product.Product;
import dev.vulcanium.business.model.catalog.product.ProductCriteria;
import dev.vulcanium.business.model.catalog.product.ProductList;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.business.model.tax.taxclass.TaxClass;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public interface ProductRepositoryCustom {


		ProductList listByStore(MerchantStore store, Language language,
			ProductCriteria criteria);

		Product getProductWithOnlyMerchantStoreById(Long productId);

		 Product getByFriendlyUrl(MerchantStore store,String seUrl, Locale locale);

		List<Product> getProductsListByCategories(@SuppressWarnings("rawtypes") Set categoryIds);

		List<Product> getProductsListByCategories(Set<Long> categoryIds,
				Language language);

		List<Product> getProductsListByIds(Set<Long> productIds);

		List<Product> listByTaxClass(TaxClass taxClass);

		List<Product> listByStore(MerchantStore store);

		Product getProductForLocale(long productId, Language language,
				Locale locale);

		Product getById(Long productId);
		Product getById(Long productId, MerchantStore merchant);

	    /**
	     * Get product by code
	     * @deprecated
	     * This method is no longer acceptable to get product by code.
	     * <p> Use {@link ProductService#getBySku(sku, store)} instead.
	     */
		@Deprecated
		Product getByCode(String productCode, Language language);
		
	    /**
	     * Get product by code
	     * @deprecated
	     * This method is no longer acceptable to get product by code.
	     * <p> Use {@link ProductService#getBySku(sku, store)} instead.
	     */
		@Deprecated
		Product getByCode(String productCode, MerchantStore store);
		
		Product getById(Long productId, MerchantStore store, Language language);

		List<Product> getProductsForLocale(MerchantStore store,
				Set<Long> categoryIds, Language language, Locale locale);

}
