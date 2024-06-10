package dev.vulcanium.site.tech.mapper.inventory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.helper.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dev.vulcanium.business.exception.ConversionException;
import dev.vulcanium.business.exception.ServiceException;
import dev.vulcanium.business.services.catalog.pricing.PricingService;
import dev.vulcanium.business.model.catalog.product.availability.ProductAvailability;
import dev.vulcanium.business.model.catalog.product.price.FinalPrice;
import dev.vulcanium.business.model.catalog.product.price.ProductPrice;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.site.tech.mapper.Mapper;
import dev.vulcanium.site.tech.model.catalog.product.ReadableProductPrice;
import dev.vulcanium.site.tech.model.catalog.product.inventory.ReadableInventory;
import dev.vulcanium.site.tech.model.store.ReadableMerchantStore;
import dev.vulcanium.site.tech.populator.catalog.ReadableProductPricePopulator;
import dev.vulcanium.site.tech.populator.store.ReadableMerchantStorePopulator;
import dev.vulcanium.site.tech.store.api.exception.ConversionRuntimeException;
import dev.vulcanium.site.tech.utils.DateUtil;

@Component
public class ReadableInventoryMapper implements Mapper<ProductAvailability, ReadableInventory> {

@Autowired
private PricingService pricingService;

@Autowired
private ReadableMerchantStorePopulator readableMerchantStorePopulator;

@Override
public ReadableInventory convert(ProductAvailability source, MerchantStore store, Language language) {
	ReadableInventory availability = new ReadableInventory();
	return merge(source, availability, store, language);
}

@Override
public ReadableInventory merge(ProductAvailability source, ReadableInventory destination, MerchantStore store,
                               Language language) {
	Validate.notNull(destination, "Destination Product availability cannot be null");
	Validate.notNull(source, "Source Product availability cannot be null");
	
	try {
		destination.setQuantity(source.getProductQuantity() != null ? source.getProductQuantity().intValue() : 0);
		destination.setProductQuantityOrderMax(
				source.getProductQuantityOrderMax() != null ? source.getProductQuantityOrderMax().intValue() : 0);
		destination.setProductQuantityOrderMin(
				source.getProductQuantityOrderMin() != null ? source.getProductQuantityOrderMin().intValue() : 0);
		destination.setOwner(source.getOwner());
		destination.setId(source.getId());
		destination.setRegion(source.getRegion());
		destination.setRegionVariant(source.getRegionVariant());
		destination.setStore(store(store, language));
		if (source.getAvailable() != null) {
			if (source.getProductDateAvailable() != null) {
				boolean isAfter = LocalDate.parse(DateUtil.getPresentDate())
						                  .isAfter(LocalDate.parse(DateUtil.formatDate(source.getProductDateAvailable())));
				if (isAfter && source.getAvailable().booleanValue()) {
					destination.setAvailable(true);
				}
				destination.setDateAvailable(DateUtil.formatDate(source.getProductDateAvailable()));
			} else {
				destination.setAvailable(source.getAvailable().booleanValue());
			}
		}
		
		if (source.getAuditSection() != null) {
			if (source.getAuditSection().getDateCreated() != null) {
				destination.setCreationDate(DateUtil.formatDate(source.getAuditSection().getDateCreated()));
			}
		}
		
		List<ReadableProductPrice> prices = prices(source, store, language);
		destination.setPrices(prices);
		
		if(!StringUtils.isEmpty(source.getSku())) {
			destination.setSku(source.getSku());
		} else {
			destination.setSku(source.getProduct().getSku());
		}
		
		FinalPrice price = null;
		//try {
		price = pricingService.calculateProductPrice(source);
		destination.setPrice(price.getStringPrice());
		//} catch (ServiceException e) {
		//	throw new ConversionRuntimeException("Unable to get product price", e);
		//}
		
	} catch (Exception e) {
		throw new ConversionRuntimeException("Error while converting Inventory", e);
	}
	
	return destination;
}

private ReadableMerchantStore store(MerchantStore store, Language language) throws ConversionException {
	if (language == null) {
		language = store.getDefaultLanguage();
	}
	/*
	 * ReadableMerchantStorePopulator populator = new
	 * ReadableMerchantStorePopulator();
	 * populator.setCountryService(countryService);
	 * populator.setZoneService(zoneService);
	 */
	return readableMerchantStorePopulator.populate(store, new ReadableMerchantStore(), store, language);
}

private List<ReadableProductPrice> prices(ProductAvailability source, MerchantStore store, Language language)
		throws ConversionException {
	
	ReadableProductPricePopulator populator = null;
	List<ReadableProductPrice> prices = new ArrayList<ReadableProductPrice>();
	
	for (ProductPrice price : source.getPrices()) {
		
		populator = new ReadableProductPricePopulator();
		populator.setPricingService(pricingService);
		ReadableProductPrice p = populator.populate(price, new ReadableProductPrice(), store, language);
		prices.add(p);
		
	}
	return prices;
}

}
