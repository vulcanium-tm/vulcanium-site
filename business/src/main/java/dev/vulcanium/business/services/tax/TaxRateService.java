package dev.vulcanium.business.services.tax;

import dev.vulcanium.business.exception.ServiceException;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.country.Country;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.business.model.reference.zone.Zone;
import dev.vulcanium.business.model.tax.taxclass.TaxClass;
import dev.vulcanium.business.model.tax.taxrate.TaxRate;
import dev.vulcanium.business.services.common.generic.SalesManagerEntityService;
import java.util.List;

public interface TaxRateService extends SalesManagerEntityService<Long, TaxRate> {

	List<TaxRate> listByStore(MerchantStore store) throws ServiceException;

	List<TaxRate> listByCountryZoneAndTaxClass(Country country, Zone zone,
			TaxClass taxClass, MerchantStore store, Language language)
			throws ServiceException;

	List<TaxRate> listByCountryStateProvinceAndTaxClass(Country country,
			String stateProvince, TaxClass taxClass, MerchantStore store,
			Language language) throws ServiceException;

	 TaxRate getByCode(String code, MerchantStore store)
			throws ServiceException;
	 
	 TaxRate getById(Long id, MerchantStore store)
				throws ServiceException;

	List<TaxRate> listByStore(MerchantStore store, Language language)
			throws ServiceException;

	TaxRate saveOrUpdate(TaxRate taxRate) throws ServiceException;
	
	

}
