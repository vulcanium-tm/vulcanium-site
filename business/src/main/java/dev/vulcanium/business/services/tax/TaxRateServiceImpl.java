package dev.vulcanium.business.services.tax;

import dev.vulcanium.business.exception.ServiceException;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.country.Country;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.business.model.reference.zone.Zone;
import dev.vulcanium.business.model.tax.taxclass.TaxClass;
import dev.vulcanium.business.model.tax.taxrate.TaxRate;
import dev.vulcanium.business.repositories.tax.TaxRateRepository;
import dev.vulcanium.business.services.common.generic.SalesManagerEntityServiceImpl;
import java.util.List;
import jakarta.inject.Inject;
import org.springframework.stereotype.Service;

@Service("taxRateService")
public class TaxRateServiceImpl extends SalesManagerEntityServiceImpl<Long, TaxRate>
		implements TaxRateService {

	private TaxRateRepository taxRateRepository;
	
	@Inject
	public TaxRateServiceImpl(TaxRateRepository taxRateRepository) {
		super(taxRateRepository);
		this.taxRateRepository = taxRateRepository;
	}

	@Override
	public List<TaxRate> listByStore(MerchantStore store)
			throws ServiceException {
		return taxRateRepository.findByStore(store.getId());
	}
	
	@Override
	public List<TaxRate> listByStore(MerchantStore store, Language language)
			throws ServiceException {
		return taxRateRepository.findByStoreAndLanguage(store.getId(), language.getId());
	}
	
	
	@Override
	public TaxRate getByCode(String code, MerchantStore store)
			throws ServiceException {
		return taxRateRepository.findByStoreAndCode(store.getId(), code);
	}
	
	@Override
	public List<TaxRate> listByCountryZoneAndTaxClass(Country country, Zone zone, TaxClass taxClass, MerchantStore store, Language language) throws ServiceException {
		return taxRateRepository.findByMerchantAndZoneAndCountryAndLanguage(store.getId(), zone.getId(), country.getId(), language.getId());
	}
	
	@Override
	public List<TaxRate> listByCountryStateProvinceAndTaxClass(Country country, String stateProvince, TaxClass taxClass, MerchantStore store, Language language) throws ServiceException {
		return taxRateRepository.findByMerchantAndProvinceAndCountryAndLanguage(store.getId(), stateProvince, country.getId(), language.getId());
	}
	
	@Override
	public void delete(TaxRate taxRate) throws ServiceException {
		
		taxRateRepository.delete(taxRate);
		
	}
	
	@Override
	public TaxRate saveOrUpdate(TaxRate taxRate) throws ServiceException {
		if(taxRate.getId()!=null && taxRate.getId() > 0) {
			this.update(taxRate);
		} else {
			taxRate = super.saveAndFlush(taxRate);
		}
		return taxRate;
	}

	@Override
	public TaxRate getById(Long id, MerchantStore store) throws ServiceException {
		return taxRateRepository.findByStoreAndId(store.getId(), id);
	}
		

	
}
