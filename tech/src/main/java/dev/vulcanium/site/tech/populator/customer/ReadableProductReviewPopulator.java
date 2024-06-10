package dev.vulcanium.site.tech.populator.customer;

import dev.vulcanium.business.exception.ConversionException;
import dev.vulcanium.business.utils.AbstractDataPopulator;
import dev.vulcanium.business.model.customer.review.CustomerReview;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.site.tech.model.customer.ReadableCustomerReview;

public class ReadableProductReviewPopulator extends AbstractDataPopulator<CustomerReview, ReadableCustomerReview> {

@Override
public ReadableCustomerReview populate(CustomerReview source, ReadableCustomerReview target, MerchantStore store,
                                       Language language) throws ConversionException {
	return null;
}

@Override
protected ReadableCustomerReview createTarget() {
	
	return null;
}

}
