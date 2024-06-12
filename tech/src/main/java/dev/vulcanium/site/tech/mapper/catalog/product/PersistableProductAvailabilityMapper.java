package dev.vulcanium.site.tech.mapper.catalog.product;

import dev.vulcanium.business.constants.Constants;
import dev.vulcanium.business.model.catalog.product.availability.ProductAvailability;
import dev.vulcanium.business.model.catalog.product.price.ProductPrice;
import dev.vulcanium.business.model.catalog.product.price.ProductPriceDescription;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.business.utils.DateUtil;
import dev.vulcanium.site.tech.mapper.Mapper;
import dev.vulcanium.site.tech.model.catalog.product.PersistableProductInventory;
import dev.vulcanium.site.tech.store.api.exception.ServiceRuntimeException;
import java.util.Date;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class PersistableProductAvailabilityMapper implements Mapper<PersistableProductInventory, ProductAvailability> {

@Override
public ProductAvailability convert(PersistableProductInventory source, MerchantStore store, Language language) {
	return this.merge(source, new ProductAvailability(), store, language);
}

@Override
public ProductAvailability merge(PersistableProductInventory source, ProductAvailability destination,
                                 MerchantStore store, Language language) {
	
	try {
		
		destination.setRegion(Constants.ALL_REGIONS);
		
		destination.setProductQuantity(source.getQuantity());
		destination.setProductQuantityOrderMin(1);
		destination.setProductQuantityOrderMax(1);
		
		if (source.getPrice() != null) {
			
			ProductPrice price = new ProductPrice();
			price.setProductAvailability(destination);
			price.setDefaultPrice(source.getPrice().isDefaultPrice());
			price.setProductPriceAmount(source.getPrice().getPrice());
			price.setCode(source.getPrice().getCode());
			price.setProductPriceSpecialAmount(source.getPrice().getDiscountedPrice());
			if (source.getPrice().getDiscountStartDate() != null) {
				Date startDate;
				
				startDate = DateUtil.getDate(source.getPrice().getDiscountStartDate());
				
				price.setProductPriceSpecialStartDate(startDate);
			}
			if (source.getPrice().getDiscountEndDate() != null) {
				Date endDate = DateUtil.getDate(source.getPrice().getDiscountEndDate());
				price.setProductPriceSpecialEndDate(endDate);
			}
			destination.getPrices().add(price);
			for (Language lang : store.getLanguages()) {
				ProductPriceDescription ppd = new ProductPriceDescription();
				ppd.setProductPrice(price);
				ppd.setLanguage(lang);
				ppd.setName(ProductPriceDescription.DEFAULT_PRICE_DESCRIPTION);
				
				Optional<dev.vulcanium.site.tech.model.catalog.product.ProductPriceDescription> description = source
						                                                                                            .getPrice().getDescriptions().stream()
						                                                                                            .filter(d -> d.getLanguage() != null && d.getLanguage().equals(lang.getCode())).findFirst();
				if (description.isPresent()) {
					ppd.setPriceAppender(description.get().getPriceAppender());
				}
				price.getDescriptions().add(ppd);
			}
			
		}
		
		
		
	} catch (Exception e) {
		throw new ServiceRuntimeException("An error occured while mapping product availability", e);
	}
	
	return destination;
}

}
