package dev.vulcanium.site.tech.store.controller.system.facade;

import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.site.tech.model.system.Configs;

public interface MerchantConfigurationFacade {

Configs getMerchantConfig(MerchantStore merchantStore, Language language);

}
