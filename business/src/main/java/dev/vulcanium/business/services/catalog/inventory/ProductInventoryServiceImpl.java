package dev.vulcanium.business.services.catalog.inventory;

import dev.vulcanium.business.exception.ServiceException;
import dev.vulcanium.business.model.catalog.product.Product;
import dev.vulcanium.business.model.catalog.product.availability.ProductAvailability;
import dev.vulcanium.business.model.catalog.product.inventory.ProductInventory;
import dev.vulcanium.business.model.catalog.product.price.FinalPrice;
import dev.vulcanium.business.model.catalog.product.variant.ProductVariant;
import dev.vulcanium.business.constants.Constants;
import dev.vulcanium.business.services.catalog.pricing.PricingService;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.helper.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;


@Service("inventoryService")
public class ProductInventoryServiceImpl implements ProductInventoryService {
	
	
	@Autowired
	private PricingService pricingService;

	@Override
	public ProductInventory inventory(Product product) throws ServiceException {
		Validate.notNull(product.getAvailabilities());
		
		ProductAvailability availability = defaultAvailability(product.getAvailabilities());
		FinalPrice finalPrice = pricingService.calculateProductPrice(product);
		
		ProductInventory inventory = inventory(availability, finalPrice);
		inventory.setSku(product.getSku());
		return inventory;
	}

	@Override
	public ProductInventory inventory(ProductVariant variant) throws ServiceException {
		Validate.notNull(variant.getAvailabilities());
		Validate.notNull(variant.getProduct());
		
		ProductAvailability availability = null;
		if(!CollectionUtils.isEmpty(variant.getAvailabilities())) {
			availability = defaultAvailability(variant.getAvailabilities());
		} else {
			availability = defaultAvailability(variant.getProduct().getAvailabilities());
		}
		FinalPrice finalPrice = pricingService.calculateProductPrice(variant);
		
		if(finalPrice==null) {
			finalPrice = pricingService.calculateProductPrice(variant.getProduct());
		}
		
		ProductInventory inventory = inventory(availability, finalPrice);
		inventory.setSku(variant.getSku());
		return inventory;
	}
	
	private ProductAvailability defaultAvailability(Set<ProductAvailability> availabilities) {
		
		ProductAvailability defaultAvailability = availabilities.iterator().next();
		
		for (ProductAvailability availability : availabilities) {
			if (!StringUtils.isEmpty(availability.getRegion())
					&& availability.getRegion().equals(Constants.ALL_REGIONS)) {// TODO REL 2.1 accept a region
				defaultAvailability = availability;
			}
		}
		
		return defaultAvailability;
		
	}
	
	private ProductInventory inventory(ProductAvailability availability, FinalPrice price) {
		ProductInventory inventory = new ProductInventory();
		inventory.setQuantity(availability.getProductQuantity());
		inventory.setPrice(price);
		
		return inventory;
	}

}
