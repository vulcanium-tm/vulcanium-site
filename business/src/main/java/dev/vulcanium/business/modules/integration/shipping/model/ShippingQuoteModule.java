package dev.vulcanium.business.modules.integration.shipping.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;

import dev.vulcanium.business.model.common.Delivery;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.shipping.PackageDetails;
import dev.vulcanium.business.model.shipping.ShippingConfiguration;
import dev.vulcanium.business.model.shipping.ShippingOption;
import dev.vulcanium.business.model.shipping.ShippingOrigin;
import dev.vulcanium.business.model.shipping.ShippingQuote;
import dev.vulcanium.business.model.system.CustomIntegrationConfiguration;
import dev.vulcanium.business.model.system.IntegrationConfiguration;
import dev.vulcanium.business.model.system.IntegrationModule;
import dev.vulcanium.business.modules.integration.IntegrationException;

public interface ShippingQuoteModule {

public void validateModuleConfiguration(IntegrationConfiguration integrationConfiguration, MerchantStore store) throws IntegrationException;
public CustomIntegrationConfiguration getCustomModuleConfiguration(MerchantStore store) throws IntegrationException;

public List<ShippingOption> getShippingQuotes(ShippingQuote quote, List<PackageDetails> packages, BigDecimal orderTotal, Delivery delivery, ShippingOrigin origin, MerchantStore store, IntegrationConfiguration configuration, IntegrationModule module, ShippingConfiguration shippingConfiguration, Locale locale) throws IntegrationException;

}
