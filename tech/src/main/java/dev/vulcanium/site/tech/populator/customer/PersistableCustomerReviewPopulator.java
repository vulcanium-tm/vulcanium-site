package dev.vulcanium.site.tech.populator.customer;

import dev.vulcanium.business.exception.ConversionException;
import dev.vulcanium.business.model.customer.Customer;
import dev.vulcanium.business.model.customer.review.CustomerReview;
import dev.vulcanium.business.model.customer.review.CustomerReviewDescription;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.business.services.customer.CustomerService;
import dev.vulcanium.business.services.reference.language.LanguageService;
import dev.vulcanium.business.utils.AbstractDataPopulator;
import dev.vulcanium.business.utils.DateUtil;
import dev.vulcanium.site.tech.model.customer.PersistableCustomerReview;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.lang3.Validate;

public class PersistableCustomerReviewPopulator extends AbstractDataPopulator<PersistableCustomerReview, CustomerReview> {

private CustomerService customerService;

private LanguageService languageService;

public LanguageService getLanguageService() {
	return languageService;
}

public void setLanguageService(LanguageService languageService) {
	this.languageService = languageService;
}

@Override
public CustomerReview populate(PersistableCustomerReview source, CustomerReview target, MerchantStore store,
                               Language language) throws ConversionException {
	
	Validate.notNull(customerService,"customerService cannot be null");
	Validate.notNull(languageService,"languageService cannot be null");
	Validate.notNull(source.getRating(),"Rating cannot bot be null");
	
	try {
		
		if(target==null) {
			target = new CustomerReview();
		}
		
		if(source.getDate() == null) {
			String date = DateUtil.formatDate(new Date());
			source.setDate(date);
		}
		target.setReviewDate(DateUtil.getDate(source.getDate()));
		
		if(source.getId() != null && source.getId().longValue()==0) {
			source.setId(null);
		} else {
			target.setId(source.getId());
		}
		
		
		Customer reviewer = customerService.getById(source.getCustomerId());
		Customer reviewed = customerService.getById(source.getReviewedCustomer());
		
		target.setReviewRating(source.getRating());
		
		target.setCustomer(reviewer);
		target.setReviewedCustomer(reviewed);
		
		Language lang = languageService.getByCode(language.getCode());
		if(lang ==null) {
			throw new ConversionException("Invalid language code, use iso codes (en, fr ...)");
		}
		
		CustomerReviewDescription description = new CustomerReviewDescription();
		description.setDescription(source.getDescription());
		description.setLanguage(lang);
		description.setName("-");
		description.setCustomerReview(target);
		
		Set<CustomerReviewDescription> descriptions = new HashSet<CustomerReviewDescription>();
		descriptions.add(description);
		
		target.setDescriptions(descriptions);
		
	} catch (Exception e) {
		throw new ConversionException("Cannot populate CustomerReview", e);
	}
	
	
	return target;
}

@Override
protected CustomerReview createTarget() {
	return null;
}

public CustomerService getCustomerService() {
	return customerService;
}

public void setCustomerService(CustomerService customerService) {
	this.customerService = customerService;
}

}
