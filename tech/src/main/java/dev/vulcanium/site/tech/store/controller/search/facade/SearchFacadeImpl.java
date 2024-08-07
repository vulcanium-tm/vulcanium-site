package dev.vulcanium.site.tech.store.controller.search.facade;

import dev.vulcanium.business.exception.ConversionException;
import dev.vulcanium.business.exception.ServiceException;
import dev.vulcanium.business.model.catalog.category.Category;
import dev.vulcanium.business.model.catalog.product.Product;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.business.services.catalog.category.CategoryService;
import dev.vulcanium.business.services.catalog.pricing.PricingService;
import dev.vulcanium.business.services.catalog.product.ProductService;
import dev.vulcanium.business.services.search.SearchService;
import dev.vulcanium.business.store.api.exception.ConversionRuntimeException;
import dev.vulcanium.business.store.api.exception.ServiceRuntimeException;
import dev.vulcanium.business.utils.ImageFilePath;
import dev.vulcanium.site.tech.model.catalog.SearchProductRequest;
import dev.vulcanium.site.tech.model.catalog.category.ReadableCategory;
import dev.vulcanium.site.tech.model.catalog.product.ReadableProduct;
import dev.vulcanium.site.tech.model.entity.ValueList;
import dev.vulcanium.site.tech.populator.catalog.ReadableCategoryPopulator;
import dev.vulcanium.site.tech.populator.catalog.ReadableProductPopulator;
import jakarta.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import modules.commons.search.request.Aggregation;
import modules.commons.search.request.SearchItem;
import modules.commons.search.request.SearchRequest;
import modules.commons.search.request.SearchResponse;
import org.jsoup.helper.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service("searchFacade")
public class SearchFacadeImpl implements SearchFacade {

private static final Logger LOGGER = LoggerFactory.getLogger(SearchFacadeImpl.class);

@Inject
private SearchService searchService;

@Inject
private ProductService productService;

@Inject
private CategoryService categoryService;

@Inject
private PricingService pricingService;

@Inject
@Qualifier("img")
private ImageFilePath imageUtils;

private final static String CATEGORY_FACET_NAME = "categories";
private final static String MANUFACTURER_FACET_NAME = "manufacturer";
private final static int AUTOCOMPLETE_ENTRIES_COUNT = 15;

/**
 * Index all products from the catalogue Better stop the system, remove ES
 * indexex manually restart ES and run this query
 */
@Override
@Async
public void indexAllData(MerchantStore store) throws Exception {
	List<Product> products = productService.listByStore(store);
	
	products.stream().forEach(p -> {
		try {
			searchService.index(store, p);
		} catch (ServiceException e) {
			throw new RuntimeException("Exception while indexing products", e);
		}
	});
	
}

@Override
public List<SearchItem> search(MerchantStore store, Language language, SearchProductRequest searchRequest) {
	SearchResponse response = search(store, language.getCode(), searchRequest.getQuery(), searchRequest.getCount(),
			searchRequest.getStart());
	return response.getItems();
}

private SearchResponse search(MerchantStore store, String languageCode, String query, Integer count,
                              Integer start) {
	
	Validate.notNull(query,"Search Keyword must not be null");
	Validate.notNull(languageCode, "Language cannot be null");
	Validate.notNull(store,"MerchantStore cannot be null");
	
	
	try {
		LOGGER.debug("Search " + query);
		SearchRequest searchRequest = new SearchRequest();
		searchRequest.setLanguage(languageCode);
		searchRequest.setSearchString(query);
		searchRequest.setStore(store.getCode().toLowerCase());
		return searchService.search(store, languageCode, searchRequest, count, start);
		
	} catch (ServiceException e) {
		throw new ServiceRuntimeException(e);
	}
}

private List<ReadableCategory> getCategoryFacets(MerchantStore merchantStore, Language language, List<Aggregation> facets) {
	return null;
}

private ReadableCategory convertCategoryToReadableCategory(MerchantStore merchantStore, Language language,
                                                           Map<String, Long> productCategoryCount, Category category) {
	ReadableCategoryPopulator populator = new ReadableCategoryPopulator();
	try {
		ReadableCategory categoryProxy = populator.populate(category, new ReadableCategory(), merchantStore,
				language);
		Long total = productCategoryCount.get(categoryProxy.getCode());
		if (total != null) {
			categoryProxy.setProductCount(total.intValue());
		}
		return categoryProxy;
	} catch (ConversionException e) {
		throw new ConversionRuntimeException(e);
	}
}

private ReadableProduct convertProductToReadableProduct(Product product, MerchantStore merchantStore,
                                                        Language language) {
	
	ReadableProductPopulator populator = new ReadableProductPopulator();
	populator.setPricingService(pricingService);
	populator.setimageUtils(imageUtils);
	
	try {
		return populator.populate(product, new ReadableProduct(), merchantStore, language);
	} catch (ConversionException e) {
		throw new ConversionRuntimeException(e);
	}
}

@Override
public ValueList autocompleteRequest(String word, MerchantStore store, Language language) {
	Validate.notNull(word,"Search Keyword must not be null");
	Validate.notNull(language, "Language cannot be null");
	Validate.notNull(store,"MerchantStore cannot be null");
	
	SearchRequest req = new SearchRequest();
	req.setLanguage(language.getCode());
	req.setStore(store.getCode().toLowerCase());
	req.setSearchString(word);
	req.setLanguage(language.getCode());
	
	SearchResponse response;
	try {
		response = searchService.searchKeywords(store, language.getCode(), req, AUTOCOMPLETE_ENTRIES_COUNT);
	} catch (ServiceException e) {
		throw new RuntimeException(e);
	}
	
	List<String> keywords = response.getItems().stream().map(i -> i.getSuggestions()).collect(Collectors.toList());
	
	ValueList valueList = new ValueList();
	valueList.setValues(keywords);
	
	return valueList;
}
}
