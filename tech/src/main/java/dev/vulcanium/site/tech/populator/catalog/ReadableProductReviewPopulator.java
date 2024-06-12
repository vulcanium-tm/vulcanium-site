package dev.vulcanium.site.tech.populator.catalog;

import dev.vulcanium.business.exception.ConversionException;
import dev.vulcanium.business.model.catalog.product.review.ProductReview;
import dev.vulcanium.business.model.catalog.product.review.ProductReviewDescription;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.business.utils.AbstractDataPopulator;
import dev.vulcanium.business.utils.DateUtil;
import dev.vulcanium.site.tech.model.catalog.product.ReadableProductReview;
import dev.vulcanium.site.tech.model.customer.ReadableCustomer;
import dev.vulcanium.site.tech.populator.customer.ReadableCustomerPopulator;
import java.util.Set;

public class ReadableProductReviewPopulator extends
		AbstractDataPopulator<ProductReview, ReadableProductReview> {

@Override
public ReadableProductReview populate(ProductReview source,
                                      ReadableProductReview target, MerchantStore store, Language language)
		throws ConversionException {
	
	
	try {
		ReadableCustomerPopulator populator = new ReadableCustomerPopulator();
		ReadableCustomer customer = new ReadableCustomer();
		populator.populate(source.getCustomer(), customer, store, language);
		
		target.setId(source.getId());
		target.setDate(DateUtil.formatDate(source.getReviewDate()));
		target.setCustomer(customer);
		target.setRating(source.getReviewRating());
		target.setProductId(source.getProduct().getId());
		
		Set<ProductReviewDescription> descriptions = source.getDescriptions();
		if(descriptions!=null) {
			for(ProductReviewDescription description : descriptions) {
				target.setDescription(description.getDescription());
				target.setLanguage(description.getLanguage().getCode());
				break;
			}
		}
		
		return target;
		
	} catch (Exception e) {
		throw new ConversionException("Cannot populate ProductReview", e);
	}
	
	
	
}

@Override
protected ReadableProductReview createTarget() {
	return null;
}

}
