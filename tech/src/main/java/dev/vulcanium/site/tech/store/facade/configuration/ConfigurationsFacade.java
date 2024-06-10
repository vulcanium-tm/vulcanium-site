package dev.vulcanium.site.tech.store.facade.configuration;

import java.util.List;

import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.site.tech.model.configuration.PersistableConfiguration;
import dev.vulcanium.site.tech.model.configuration.ReadableConfiguration;

public interface ConfigurationsFacade {

List<ReadableConfiguration> configurations(MerchantStore store);

ReadableConfiguration configuration(String module, MerchantStore store);

void saveConfiguration(PersistableConfiguration configuration, MerchantStore store);

void deleteConfiguration(String module, MerchantStore store);

}
