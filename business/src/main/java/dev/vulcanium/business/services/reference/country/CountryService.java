package dev.vulcanium.business.services.reference.country;

import dev.vulcanium.business.exception.ServiceException;
import dev.vulcanium.business.model.reference.country.Country;
import dev.vulcanium.business.model.reference.country.CountryDescription;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.business.services.common.generic.SalesManagerEntityService;
import java.util.List;
import java.util.Map;

public interface CountryService extends SalesManagerEntityService<Integer, Country> {

	Country getByCode(String code) throws ServiceException;
	
	void addCountryDescription(Country country, CountryDescription description) throws ServiceException;

	List<Country> getCountries(Language language) throws ServiceException;

	Map<String, Country> getCountriesMap(Language language)
			throws ServiceException;

	List<Country> getCountries(List<String> isoCodes, Language language)
			throws ServiceException;
	
	
	/**
	 * List country - zone objects by language
	 * @param language
	 * @return
	 * @throws ServiceException
	 */
	List<Country> listCountryZones(Language language) throws ServiceException;
}
