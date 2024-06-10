package dev.vulcanium.site.tech.populator.catalog;

import org.apache.commons.lang3.Validate;

import dev.vulcanium.business.exception.ConversionException;
import dev.vulcanium.business.services.catalog.pricing.PricingService;
import dev.vulcanium.business.utils.AbstractDataPopulator;
import dev.vulcanium.business.model.catalog.product.price.FinalPrice;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.site.tech.model.catalog.product.ReadableProductPrice;

public class ReadableFinalPricePopulator extends
		AbstractDataPopulator<FinalPrice, ReadableProductPrice> {


private PricingService pricingService;

public PricingService getPricingService() {
	return pricingService;
}

public void setPricingService(PricingService pricingService) {
	this.pricingService = pricingService;
}

@Override
public ReadableProductPrice populate(FinalPrice source,
                                     ReadableProductPrice target, MerchantStore store, Language language)
		throws ConversionException {
	Validate.notNull(pricingService,"pricingService must be set");
	
	try {
		
		target.setOriginalPrice(pricingService.getDisplayAmount(source.getOriginalPrice(), store));
		if(source.isDiscounted()) {
			target.setDiscounted(true);
			target.setFinalPrice(pricingService.getDisplayAmount(source.getDiscountedPrice(), store));
		} else {
			target.setFinalPrice(pricingService.getDisplayAmount(source.getFinalPrice(), store));
		}
		
	} catch(Exception e) {
		throw new ConversionException("Exception while converting to ReadableProductPrice",e);
	}
	
	
	
	return target;
}

@Override
protected ReadableProductPrice createTarget() {
	return null;
}

}
