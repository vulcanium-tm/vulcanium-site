package dev.vulcanium.business.modules.integration.shipping.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.vulcanium.business.exception.ServiceException;
import dev.vulcanium.business.model.common.Delivery;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.shipping.*;
import dev.vulcanium.business.model.system.CustomIntegrationConfiguration;
import dev.vulcanium.business.model.system.IntegrationConfiguration;
import dev.vulcanium.business.model.system.IntegrationModule;
import dev.vulcanium.business.model.system.MerchantConfiguration;
import dev.vulcanium.business.modules.integration.IntegrationException;
import dev.vulcanium.business.modules.integration.shipping.model.CustomShippingQuoteWeightItem;
import dev.vulcanium.business.modules.integration.shipping.model.CustomShippingQuotesConfiguration;
import dev.vulcanium.business.modules.integration.shipping.model.CustomShippingQuotesRegion;
import dev.vulcanium.business.modules.integration.shipping.model.ShippingQuoteModule;
import dev.vulcanium.business.services.system.MerchantConfigurationService;
import dev.vulcanium.business.utils.ProductPriceUtils;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import jakarta.inject.Inject;
import org.apache.commons.lang3.StringUtils;


public class CustomWeightBasedShippingQuote implements ShippingQuoteModule {
	
	public final static String MODULE_CODE = "weightBased";
	private final static String CUSTOM_WEIGHT = "CUSTOM_WEIGHT";
	
	@Inject
	private MerchantConfigurationService merchantConfigurationService;
	
	@Inject
	private ProductPriceUtils productPriceUtils;


	@Override
	public void validateModuleConfiguration(
			IntegrationConfiguration integrationConfiguration,
			MerchantStore store) throws IntegrationException {
		
		
		//not used, it has its own controller with complex validators

	}
	

	@Override
	public CustomIntegrationConfiguration getCustomModuleConfiguration(
			MerchantStore store) throws IntegrationException {

		try {

			MerchantConfiguration configuration = merchantConfigurationService.getMerchantConfiguration(MODULE_CODE, store);
	
			if(configuration!=null) {
				String value = configuration.getValue();
				ObjectMapper mapper = new ObjectMapper();
				try {
					return mapper.readValue(value, CustomShippingQuotesConfiguration.class);
				} catch(Exception e) {
					throw new ServiceException("Cannot parse json string " + value);
				}
	
			} else {
				CustomShippingQuotesConfiguration custom = new CustomShippingQuotesConfiguration();
				custom.setModuleCode(MODULE_CODE);
				return custom;
			}
		
		} catch (Exception e) {
			throw new IntegrationException(e);
		}
		
		
	}

	@Override
	public List<ShippingOption> getShippingQuotes(
			ShippingQuote shippingQuote,
			List<PackageDetails> packages, BigDecimal orderTotal,
			Delivery delivery, ShippingOrigin origin, MerchantStore store,
			IntegrationConfiguration configuration, IntegrationModule module,
			ShippingConfiguration shippingConfiguration, Locale locale)
			throws IntegrationException {

		if(StringUtils.isBlank(delivery.getPostalCode())) {
			return null;
		}
		
		//get configuration
		CustomShippingQuotesConfiguration customConfiguration = (CustomShippingQuotesConfiguration)this.getCustomModuleConfiguration(store);
		
		
		List<CustomShippingQuotesRegion> regions = customConfiguration.getRegions();
		
		ShippingBasisType shippingType =  shippingConfiguration.getShippingBasisType();
		ShippingOption shippingOption = null;
		try {
			

			for(CustomShippingQuotesRegion region : customConfiguration.getRegions()) {
	
				for(String countryCode : region.getCountries()) {
					if(countryCode.equals(delivery.getCountry().getIsoCode())) {
						
						
						//determine shipping weight
						double weight = 0;
						for(PackageDetails packageDetail : packages) {
							weight = weight + packageDetail.getShippingWeight();
						}
						
						//see the price associated with the width
						List<CustomShippingQuoteWeightItem> quoteItems = region.getQuoteItems();
						for(CustomShippingQuoteWeightItem quoteItem : quoteItems) {
							if(weight<= quoteItem.getMaximumWeight()) {
								shippingOption = new ShippingOption();
								shippingOption.setOptionCode(new StringBuilder().append(CUSTOM_WEIGHT).toString());
								shippingOption.setOptionId(new StringBuilder().append(CUSTOM_WEIGHT).append("_").append(region.getCustomRegionName()).toString());
								shippingOption.setOptionPrice(quoteItem.getPrice());
								shippingOption.setOptionPriceText(productPriceUtils.getStoreFormatedAmountWithCurrency(store, quoteItem.getPrice()));
								break;
							}
						}
						
					}
					
					
				}
				
			}
			
			if(shippingOption!=null) {
				List<ShippingOption> options = new ArrayList<ShippingOption>();
				options.add(shippingOption);
				return options;
			}
			
			return null;
		
		} catch (Exception e) {
			throw new IntegrationException(e);
		}

	}



}
