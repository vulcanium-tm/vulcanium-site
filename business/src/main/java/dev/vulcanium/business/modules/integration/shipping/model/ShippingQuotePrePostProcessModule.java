package dev.vulcanium.business.modules.integration.shipping.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;

import dev.vulcanium.business.model.common.Delivery;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.shipping.PackageDetails;
import dev.vulcanium.business.model.shipping.ShippingConfiguration;
import dev.vulcanium.business.model.shipping.ShippingOrigin;
import dev.vulcanium.business.model.shipping.ShippingQuote;
import dev.vulcanium.business.model.system.IntegrationConfiguration;
import dev.vulcanium.business.model.system.IntegrationModule;
import dev.vulcanium.business.modules.integration.IntegrationException;

/**
 * Invoked before or after quote processing
 */
public interface ShippingQuotePrePostProcessModule {
	
	String getModuleCode();
	
	void prePostProcessShippingQuotes(
		ShippingQuote quote,
		List<PackageDetails> packages,
		BigDecimal orderTotal,
		Delivery delivery,
		ShippingOrigin origin,
		MerchantStore store,
		IntegrationConfiguration globalShippingConfiguration,
		IntegrationModule currentModule,
		ShippingConfiguration shippingConfiguration,
		List<IntegrationModule> allModules,
		Locale locale) throws IntegrationException;
}
