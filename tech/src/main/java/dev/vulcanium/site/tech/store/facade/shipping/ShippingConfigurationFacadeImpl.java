package dev.vulcanium.site.tech.store.facade.shipping;

import java.util.List;

import org.springframework.stereotype.Service;

import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.site.tech.model.configuration.PersistableConfiguration;
import dev.vulcanium.site.tech.model.configuration.ReadableConfiguration;
import dev.vulcanium.site.tech.store.facade.configuration.ConfigurationsFacade;

@Service("shippingConfigurationFacade")
public class ShippingConfigurationFacadeImpl implements ConfigurationsFacade {

@Override
public List<ReadableConfiguration> configurations(MerchantStore store) {
	return null;
}

@Override
public ReadableConfiguration configuration(String module, MerchantStore store) {
	return null;
}

@Override
public void saveConfiguration(PersistableConfiguration configuration, MerchantStore store) {

}

@Override
public void deleteConfiguration(String module, MerchantStore store) {

}

}
