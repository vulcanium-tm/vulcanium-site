package dev.vulcanium.site.tech.populator.store;

import java.util.Date;
import java.util.List;

import jakarta.inject.Inject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Component;

import dev.vulcanium.business.constants.Constants;
import dev.vulcanium.business.exception.ConversionException;
import dev.vulcanium.business.exception.ServiceException;
import dev.vulcanium.business.services.merchant.MerchantStoreService;
import dev.vulcanium.business.services.reference.country.CountryService;
import dev.vulcanium.business.services.reference.currency.CurrencyService;
import dev.vulcanium.business.services.reference.language.LanguageService;
import dev.vulcanium.business.services.reference.zone.ZoneService;
import dev.vulcanium.business.utils.AbstractDataPopulator;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.country.Country;
import dev.vulcanium.business.model.reference.currency.Currency;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.business.model.reference.zone.Zone;
import dev.vulcanium.site.tech.model.references.PersistableAddress;
import dev.vulcanium.site.tech.model.store.PersistableMerchantStore;
import dev.vulcanium.site.tech.utils.DateUtil;

@Component
public class PersistableMerchantStorePopulator extends AbstractDataPopulator<PersistableMerchantStore, MerchantStore> {

@Inject
private CountryService countryService;
@Inject
private ZoneService zoneService;
@Inject
private LanguageService languageService;
@Inject
private CurrencyService currencyService;
@Inject
private MerchantStoreService merchantStoreService;


@Override
public MerchantStore populate(PersistableMerchantStore source, MerchantStore target, MerchantStore store,
                              Language language) throws ConversionException {
	
	Validate.notNull(source, "PersistableMerchantStore mst not be null");
	
	if(target == null) {
		target = new MerchantStore();
	}
	
	target.setCode(source.getCode());
	if(source.getId()!=0) {
		target.setId(source.getId());
	}
	
	if(store.getStoreLogo()!=null) {
		target.setStoreLogo(store.getStoreLogo());
	}
	
	if(!StringUtils.isEmpty(source.getInBusinessSince())) {
		try {
			Date dt = DateUtil.getDate(source.getInBusinessSince());
			target.setInBusinessSince(dt);
		} catch(Exception e) {
			throw new ConversionException("Cannot parse date [" + source.getInBusinessSince() + "]",e);
		}
	}
	
	if(source.getDimension()!=null) {
		target.setSeizeunitcode(source.getDimension().name());
	}
	if(source.getWeight()!=null) {
		target.setWeightunitcode(source.getWeight().name());
	}
	target.setCurrencyFormatNational(source.isCurrencyFormatNational());
	target.setStorename(source.getName());
	target.setStorephone(source.getPhone());
	target.setStoreEmailAddress(source.getEmail());
	target.setUseCache(source.isUseCache());
	target.setRetailer(source.isRetailer());
	
	if(!StringUtils.isBlank(source.getRetailerStore())) {
		if(source.getRetailerStore().equals(source.getCode())) {
			throw new ConversionException("Parent store [" + source.getRetailerStore() + "] cannot be parent of current store");
		}
		try {
			MerchantStore parent = merchantStoreService.getByCode(source.getRetailerStore());
			if(parent == null) {
				throw new ConversionException("Parent store [" + source.getRetailerStore() + "] does not exist");
			}
			target.setParent(parent);
		} catch (ServiceException e) {
			throw new ConversionException(e);
		}
	}
	
	
	try {
		
		if(!StringUtils.isEmpty(source.getDefaultLanguage())) {
			Language l = languageService.getByCode(source.getDefaultLanguage());
			target.setDefaultLanguage(l);
		}
		
		if(!StringUtils.isEmpty(source.getCurrency())) {
			Currency c = currencyService.getByCode(source.getCurrency());
			target.setCurrency(c);
		} else {
			target.setCurrency(currencyService.getByCode(Constants.DEFAULT_CURRENCY.getCurrencyCode()));
		}
		
		List<String> languages = source.getSupportedLanguages();
		if(!CollectionUtils.isEmpty(languages)) {
			for(String lang : languages) {
				Language ll = languageService.getByCode(lang);
				target.getLanguages().add(ll);
			}
		}
		
	} catch(Exception e) {
		throw new ConversionException(e);
	}
	
	PersistableAddress address = source.getAddress();
	if(address != null) {
		Country country;
		try {
			country = countryService.getByCode(address.getCountry());
			
			Zone zone = zoneService.getByCode(address.getStateProvince());
			if(zone != null) {
				target.setZone(zone);
			} else {
				target.setStorestateprovince(address.getStateProvince());
			}
			
			target.setStoreaddress(address.getAddress());
			target.setStorecity(address.getCity());
			target.setCountry(country);
			target.setStorepostalcode(address.getPostalCode());
			
		} catch (ServiceException e) {
			throw new ConversionException(e);
		}
	}
	
	if (StringUtils.isNotEmpty(source.getTemplate()))
		target.setStoreTemplate(source.getTemplate());
	
	return target;
}

@Override
protected MerchantStore createTarget() {
	return null;
}

public ZoneService getZoneService() {
	return zoneService;
}

public void setZoneService(ZoneService zoneService) {
	this.zoneService = zoneService;
}
public CountryService getCountryService() {
	return countryService;
}

public void setCountryService(CountryService countryService) {
	this.countryService = countryService;
}

public LanguageService getLanguageService() {
	return languageService;
}

public void setLanguageService(LanguageService languageService) {
	this.languageService = languageService;
}

public CurrencyService getCurrencyService() {
	return currencyService;
}

public void setCurrencyService(CurrencyService currencyService) {
	this.currencyService = currencyService;
}


}
