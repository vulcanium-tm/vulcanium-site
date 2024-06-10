package dev.vulcanium.site.tech.store.controller.optin.facade;

import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.site.tech.model.system.PersistableOptin;
import dev.vulcanium.site.tech.model.system.ReadableOptin;

public interface OptinFacade {

ReadableOptin create(PersistableOptin persistableOptin, MerchantStore merchantStore, Language language);
}
