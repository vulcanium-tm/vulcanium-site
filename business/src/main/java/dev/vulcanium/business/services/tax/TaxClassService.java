package dev.vulcanium.business.services.tax;

import dev.vulcanium.business.exception.ServiceException;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.tax.taxclass.TaxClass;
import dev.vulcanium.business.services.common.generic.SalesManagerEntityService;
import java.util.List;

public interface TaxClassService extends SalesManagerEntityService<Long, TaxClass> {

	List<TaxClass> listByStore(MerchantStore store) throws ServiceException;

	TaxClass getByCode(String code) throws ServiceException;

	TaxClass getByCode(String code, MerchantStore store)
			throws ServiceException;
	
	boolean exists(String code, MerchantStore store) throws ServiceException;
	
	TaxClass saveOrUpdate(TaxClass taxClass) throws ServiceException;
	

}
