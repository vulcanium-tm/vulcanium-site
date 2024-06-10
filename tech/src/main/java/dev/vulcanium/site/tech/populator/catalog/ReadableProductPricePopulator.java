package dev.vulcanium.site.tech.populator.catalog;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.Validate;

import dev.vulcanium.business.exception.ConversionException;
import dev.vulcanium.business.services.catalog.pricing.PricingService;
import dev.vulcanium.business.utils.AbstractDataPopulator;
import dev.vulcanium.business.model.catalog.product.price.FinalPrice;
import dev.vulcanium.business.model.catalog.product.price.ProductPrice;
import dev.vulcanium.business.model.catalog.product.price.ProductPriceDescription;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.site.tech.model.catalog.product.ReadableProductPrice;
import dev.vulcanium.site.tech.model.catalog.product.ReadableProductPriceFull;

public class ReadableProductPricePopulator extends
		AbstractDataPopulator<ProductPrice, ReadableProductPrice> {


private PricingService pricingService;

public PricingService getPricingService() {
	return pricingService;
}

public void setPricingService(PricingService pricingService) {
	this.pricingService = pricingService;
}

@Override
public ReadableProductPrice populate(ProductPrice source,
                                     ReadableProductPrice target, MerchantStore store, Language language)
		throws ConversionException {
	Validate.notNull(pricingService,"pricingService must be set");
	Validate.notNull(source.getProductAvailability(),"productPrice.availability cannot be null");
	Validate.notNull(source.getProductAvailability().getProduct(),"productPrice.availability.product cannot be null");
	
	try {
		
		if(language == null) {
			target = new ReadableProductPriceFull();
		}
		
		if(source.getId() != null && source.getId() > 0) {
			target.setId(source.getId());
		}
		
		target.setDefaultPrice(source.isDefaultPrice());
		
		FinalPrice finalPrice = pricingService.calculateProductPrice(source.getProductAvailability().getProduct());
		
		target.setOriginalPrice(pricingService.getDisplayAmount(source.getProductPriceAmount(), store));
		if(finalPrice.isDiscounted()) {
			target.setDiscounted(true);
			target.setFinalPrice(pricingService.getDisplayAmount(source.getProductPriceSpecialAmount(), store));
		} else {
			target.setFinalPrice(pricingService.getDisplayAmount(finalPrice.getOriginalPrice(), store));
		}
		
		if(source.getDescriptions()!=null && source.getDescriptions().size()>0) {
			List<dev.vulcanium.site.tech.model.catalog.product.ProductPriceDescription> fulldescriptions = new ArrayList<dev.vulcanium.business.model.catalog.product.ProductPriceDescription>();
			
			Set<ProductPriceDescription> descriptions = source.getDescriptions();
			ProductPriceDescription description = null;
			for(ProductPriceDescription desc : descriptions) {
				if(language != null && desc.getLanguage().getCode().equals(language.getCode())) {
					description = desc;
					break;
				} else {
					fulldescriptions.add(populateDescription(desc));
				}
			}
			
			
			if (description != null) {
				dev.vulcanium.site.tech.model.catalog.product.ProductPriceDescription d = populateDescription(description);
				target.setDescription(d);
			}
			
			if(target instanceof ReadableProductPriceFull) {
				((ReadableProductPriceFull)target).setDescriptions(fulldescriptions);
			}
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

dev.vulcanium.site.tech.model.catalog.product.ProductPriceDescription populateDescription(
		ProductPriceDescription description) {
	if (description == null) {
		return null;
	}
	dev.vulcanium.site.tech.model.catalog.product.ProductPriceDescription d =
			new dev.vulcanium.site.tech.model.catalog.product.ProductPriceDescription();
	d.setName(description.getName());
	d.setDescription(description.getDescription());
	d.setId(description.getId());
	d.setTitle(description.getTitle());
	if (description.getLanguage() != null) {
		d.setLanguage(description.getLanguage().getCode());
	}
	return d;
}

}
