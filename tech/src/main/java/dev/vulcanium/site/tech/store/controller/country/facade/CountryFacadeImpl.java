package dev.vulcanium.site.tech.store.controller.country.facade;

import dev.vulcanium.business.exception.ConversionException;
import dev.vulcanium.business.exception.ServiceException;
import dev.vulcanium.business.services.reference.country.CountryService;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.country.Country;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.site.tech.model.references.ReadableCountry;
import dev.vulcanium.site.tech.populator.references.ReadableCountryPopulator;
import dev.vulcanium.site.tech.store.api.exception.ConversionRuntimeException;
import dev.vulcanium.site.tech.store.api.exception.ServiceRuntimeException;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.inject.Inject;
import org.springframework.stereotype.Service;

@Service("countryFacade")
public class CountryFacadeImpl implements CountryFacade {

@Inject
private CountryService countryService;

@Override
public List<ReadableCountry> getListCountryZones(Language language, MerchantStore merchantStore) {
	return getListOfCountryZones(language)
			       .stream()
			       .map(country -> convertToReadableCountry(country, language, merchantStore))
			       .collect(Collectors.toList());
}

private ReadableCountry convertToReadableCountry(Country country, Language language, MerchantStore merchantStore) {
	try{
		ReadableCountryPopulator populator = new ReadableCountryPopulator();
		return populator.populate(country, new ReadableCountry(), merchantStore, language);
	} catch (ConversionException e) {
		throw new ConversionRuntimeException(e);
	}
}

private List<Country> getListOfCountryZones(Language language) {
	try{
		return countryService.listCountryZones(language);
	} catch (ServiceException e) {
		throw new ServiceRuntimeException(e);
	}
}
}