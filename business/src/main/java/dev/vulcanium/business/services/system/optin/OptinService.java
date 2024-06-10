package dev.vulcanium.business.services.system.optin;

import dev.vulcanium.business.exception.ServiceException;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.system.optin.Optin;
import dev.vulcanium.business.model.system.optin.OptinType;
import dev.vulcanium.business.services.common.generic.SalesManagerEntityService;

/**
 * Registers Optin events
 * @author carlsamson
 *
 */
public interface OptinService extends SalesManagerEntityService<Long, Optin> {
	
	
	Optin getOptinByMerchantAndType(MerchantStore store, OptinType type) throws ServiceException;
	Optin getOptinByCode(MerchantStore store, String code) throws ServiceException;

}
