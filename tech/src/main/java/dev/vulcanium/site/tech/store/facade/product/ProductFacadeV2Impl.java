package dev.vulcanium.site.tech.store.facade.product;

import dev.vulcanium.business.exception.ServiceException;
import dev.vulcanium.business.model.catalog.product.Product;
import dev.vulcanium.business.model.catalog.product.ProductCriteria;
import dev.vulcanium.business.model.catalog.product.relationship.ProductRelationship;
import dev.vulcanium.business.model.catalog.product.relationship.ProductRelationshipType;
import dev.vulcanium.business.model.catalog.product.variant.ProductVariant;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.business.services.catalog.category.CategoryService;
import dev.vulcanium.business.services.catalog.pricing.PricingService;
import dev.vulcanium.business.services.catalog.product.ProductService;
import dev.vulcanium.business.services.catalog.product.attribute.ProductAttributeService;
import dev.vulcanium.business.services.catalog.product.availability.ProductAvailabilityService;
import dev.vulcanium.business.services.catalog.product.relationship.ProductRelationshipService;
import dev.vulcanium.business.services.catalog.product.variant.ProductVariantService;
import dev.vulcanium.business.store.api.exception.ResourceNotFoundException;
import dev.vulcanium.business.store.api.exception.ServiceRuntimeException;
import dev.vulcanium.business.utils.ImageFilePath;
import dev.vulcanium.business.utils.LocaleUtils;
import dev.vulcanium.site.tech.mapper.catalog.product.ReadableProductMapper;
import dev.vulcanium.site.tech.mapper.catalog.product.ReadableProductVariantMapper;
import dev.vulcanium.site.tech.model.catalog.product.ReadableProduct;
import dev.vulcanium.site.tech.model.catalog.product.ReadableProductList;
import dev.vulcanium.site.tech.model.catalog.product.variant.ReadableProductVariant;
import dev.vulcanium.site.tech.populator.catalog.ReadableProductPopulator;
import jakarta.inject.Inject;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service("productFacadeV2")
@Profile({ "default", "cloud", "gcp", "aws", "mysql" , "local" })
public class ProductFacadeV2Impl implements ProductFacade {


@Autowired
private ProductService productService;

@Inject
private CategoryService categoryService;

@Inject
private ProductRelationshipService productRelationshipService;

@Autowired
private ReadableProductMapper readableProductMapper;

@Autowired
private ProductVariantService productVariantService;

@Autowired
private ReadableProductVariantMapper readableProductVariantMapper;

@Autowired
private ProductAvailabilityService productAvailabilityService;

@Autowired
private ProductAttributeService productAttributeService;

@Inject
private PricingService pricingService;

@Inject
@Qualifier("img")
private ImageFilePath imageUtils;


@Override
public Product getProduct(Long id, MerchantStore store) {
	//same as v1
	return productService.findOne(id, store);
}

@Override
public ReadableProduct getProductByCode(MerchantStore store, String sku, Language language) {
	
	
	Product product = null;
	try {
		product = productService.getBySku(sku, store, language);
	} catch (ServiceException e) {
		throw new ServiceRuntimeException(e);
	}
	
	if (product == null) {
		throw new ResourceNotFoundException("Product [" + sku + "] not found for merchant [" + store.getCode() + "]");
	}
	
	if (product.getMerchantStore().getId() != store.getId()) {
		throw new ResourceNotFoundException("Product [" + sku + "] not found for merchant [" + store.getCode() + "]");
	}
	
	
	ReadableProduct readableProduct = readableProductMapper.convert(product, store, language);
	
	return readableProduct;
	
}

private ReadableProductVariant productVariant(ProductVariant instance, MerchantStore store, Language language) {
	
	return readableProductVariantMapper.convert(instance, store, language);
	
}

@Override
public ReadableProduct getProduct(MerchantStore store, String sku, Language language) throws Exception {
	return this.getProductByCode(store, sku, language);
}

@Override
public ReadableProduct getProductBySeUrl(MerchantStore store, String friendlyUrl, Language language)
		throws Exception {
	
	Product product = productService.getBySeUrl(store, friendlyUrl, LocaleUtils.getLocale(language));
	
	if (product == null) {
		throw new ResourceNotFoundException("Product [" + friendlyUrl + "] not found for merchant [" + store.getCode() + "]");
	}
	
	ReadableProduct readableProduct = readableProductMapper.convert(product, store, language);
	
	//get all instances for this product group by option
	//limit to 15 searches
	List<ProductVariant> instances = productVariantService.getByProductId(store, product, language);
	
	//the above get all possible images
	List<ReadableProductVariant> readableInstances = instances.stream().map(p -> this.productVariant(p, store, language)).collect(Collectors.toList());
	readableProduct.setVariants(readableInstances);
	
	return readableProduct;
	
}

/**
 * Filters on otion, optionValues and other criterias
 */

@Override
public ReadableProductList getProductListsByCriterias(MerchantStore store, Language language,
                                                      ProductCriteria criterias) throws Exception {
	Validate.notNull(criterias, "ProductCriteria must be set for this product");
	
	/** This is for category **/
	if (CollectionUtils.isNotEmpty(criterias.getCategoryIds())) {
		
		if (criterias.getCategoryIds().size() == 1) {
			
			dev.vulcanium.business.model.catalog.category.Category category = categoryService
					                                                                 .getById(criterias.getCategoryIds().get(0));
			
			if (category != null) {
				String lineage = new StringBuilder().append(category.getLineage())
						                 .toString();
				
				List<dev.vulcanium.business.model.catalog.category.Category> categories = categoryService
						                                                                         .getListByLineage(store, lineage);
				
				List<Long> ids = new ArrayList<Long>();
				if (categories != null && categories.size() > 0) {
					for (dev.vulcanium.business.model.catalog.category.Category c : categories) {
						ids.add(c.getId());
					}
				}
				ids.add(category.getId());
				criterias.setCategoryIds(ids);
			}
		}
	}
	
	
	Page<Product> modelProductList = productService.listByStore(store, language, criterias, criterias.getStartPage(), criterias.getMaxCount());
	
	List<Product> products = modelProductList.getContent();
	ReadableProductList productList = new ReadableProductList();
	
	
	/**
	 * ReadableProductMapper
	 */
	
	List<ReadableProduct> readableProducts = products.stream().map(p -> readableProductMapper.convert(p, store, language))
			                                         .sorted(Comparator.comparing(ReadableProduct::getSortOrder)).collect(Collectors.toList());
	
	
	productList.setRecordsTotal(modelProductList.getTotalElements());
	productList.setNumber(modelProductList.getNumberOfElements());
	productList.setProducts(readableProducts);
	productList.setTotalPages(modelProductList.getTotalPages());
	
	return productList;
}

@Override
public List<ReadableProduct> relatedItems(MerchantStore store, Product product, Language language)
		throws Exception {
	//same as v1
	ReadableProductPopulator populator = new ReadableProductPopulator();
	populator.setPricingService(pricingService);
	populator.setimageUtils(imageUtils);
	
	List<ProductRelationship> relatedItems = productRelationshipService.getByType(store, product,
			ProductRelationshipType.RELATED_ITEM);
	if (relatedItems != null && relatedItems.size() > 0) {
		List<ReadableProduct> items = new ArrayList<ReadableProduct>();
		for (ProductRelationship relationship : relatedItems) {
			Product relatedProduct = relationship.getRelatedProduct();
			ReadableProduct proxyProduct = populator.populate(relatedProduct, new ReadableProduct(), store,
					language);
			items.add(proxyProduct);
		}
		return items;
	}
	return null;
}
}