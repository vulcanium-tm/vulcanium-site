package dev.vulcanium.site.tech.populator.catalog;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.Validate;

import dev.vulcanium.business.exception.ConversionException;
import dev.vulcanium.business.services.catalog.product.ProductService;
import dev.vulcanium.business.services.customer.CustomerService;
import dev.vulcanium.business.services.reference.language.LanguageService;
import dev.vulcanium.business.utils.AbstractDataPopulator;
import dev.vulcanium.business.model.catalog.product.Product;
import dev.vulcanium.business.model.catalog.product.review.ProductReview;
import dev.vulcanium.business.model.catalog.product.review.ProductReviewDescription;
import dev.vulcanium.business.model.customer.Customer;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.site.tech.model.catalog.product.PersistableProductReview;
import dev.vulcanium.site.tech.utils.DateUtil;

public class PersistableProductReviewPopulator extends
		AbstractDataPopulator<PersistableProductReview, ProductReview> {

private CustomerService customerService;

private ProductService productService;

private LanguageService languageService;

public LanguageService getLanguageService() {
	return languageService;
}

public void setLanguageService(LanguageService languageService) {
	this.languageService = languageService;
}

@Override
public ProductReview populate(PersistableProductReview source,
                              ProductReview target, MerchantStore store, Language language)
		throws ConversionException {
	
	
	Validate.notNull(customerService,"customerService cannot be null");
	Validate.notNull(productService,"productService cannot be null");
	Validate.notNull(languageService,"languageService cannot be null");
	Validate.notNull(source.getRating(),"Rating cannot bot be null");
	
	try {
		
		if(target==null) {
			target = new ProductReview();
		}
		
		Customer customer = customerService.getById(source.getCustomerId());
		
		if(customer ==null || customer.getMerchantStore().getId().intValue()!=store.getId().intValue()) {
			throw new ConversionException("Invalid customer id for the given store");
		}
		
		if(source.getDate() == null) {
			String date = DateUtil.formatDate(new Date());
			source.setDate(date);
		}
		target.setReviewDate(DateUtil.getDate(source.getDate()));
		target.setCustomer(customer);
		target.setReviewRating(source.getRating());
		
		Product product = productService.getById(source.getProductId());
		
		if(product ==null || product.getMerchantStore().getId().intValue()!=store.getId().intValue()) {
			throw new ConversionException("Invalid product id for the given store");
		}
		
		target.setProduct(product);
		
		Language lang = languageService.getByCode(language.getCode());
		if(lang ==null) {
			throw new ConversionException("Invalid language code, use iso codes (en, fr ...)");
		}
		
		ProductReviewDescription description = new ProductReviewDescription();
		description.setDescription(source.getDescription());
		description.setLanguage(lang);
		description.setName("-");
		description.setProductReview(target);
		
		Set<ProductReviewDescription> descriptions = new HashSet<ProductReviewDescription>();
		descriptions.add(description);
		
		target.setDescriptions(descriptions);
		
		
		
		
		
		return target;
		
	} catch (Exception e) {
		throw new ConversionException("Cannot populate ProductReview", e);
	}
	
}

@Override
protected ProductReview createTarget() {
	return null;
}

public CustomerService getCustomerService() {
	return customerService;
}

public void setCustomerService(CustomerService customerService) {
	this.customerService = customerService;
}

public ProductService getProductService() {
	return productService;
}

public void setProductService(ProductService productService) {
	this.productService = productService;
}


}
