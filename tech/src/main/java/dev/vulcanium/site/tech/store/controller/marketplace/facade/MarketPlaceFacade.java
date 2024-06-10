package dev.vulcanium.site.tech.store.controller.marketplace.facade;

import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.business.model.system.optin.OptinType;
import dev.vulcanium.site.tech.model.marketplace.ReadableMarketPlace;
import dev.vulcanium.site.tech.model.system.ReadableOptin;

/**
 * Builds market places objects for shop and REST api
 */
public interface MarketPlaceFacade {


/**
 * Get a MarketPlace from store code
 * @param store
 * @param lang
 * @return
 * @throws Exception
 */
ReadableMarketPlace get(String store, Language lang) ;

/**
 * Finds an optin by merchant and type
 * @param store
 * @param type
 * @return
 * @throws Exception
 */
ReadableOptin findByMerchantAndType(MerchantStore store, OptinType type);

}
