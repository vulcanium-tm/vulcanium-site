package dev.vulcanium.business.services.catalog.marketplace;

import dev.vulcanium.business.exception.ServiceException;
import dev.vulcanium.business.model.catalog.marketplace.MarketPlace;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.services.common.generic.SalesManagerEntityService;

public interface MarketPlaceService extends SalesManagerEntityService<Long, MarketPlace> {
	
	/**
	 * Creates a MarketPlace
	 * @param store
	 * @param code
	 * @return MarketPlace
	 * @throws ServiceException
	 */
	MarketPlace create(MerchantStore store, String code) throws ServiceException;
	
	/**
	 * Fetch a specific marketplace
	 * @param store
	 * @param code
	 * @return MarketPlace
	 * @throws ServiceException
	 */
	MarketPlace getByCode(MerchantStore store, String code) throws ServiceException;
	
	void delete(MarketPlace marketPlace) throws ServiceException;

}
