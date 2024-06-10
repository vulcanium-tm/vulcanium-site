package dev.vulcanium.business.services.reference.zone;

import dev.vulcanium.business.exception.ServiceException;
import dev.vulcanium.business.model.reference.country.Country;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.business.model.reference.zone.Zone;
import dev.vulcanium.business.model.reference.zone.ZoneDescription;
import dev.vulcanium.business.services.common.generic.SalesManagerEntityService;
import java.util.List;
import java.util.Map;

public interface ZoneService extends SalesManagerEntityService<Long, Zone> {
	
	Zone getByCode(String code);

	void addDescription(Zone zone, ZoneDescription description) throws ServiceException;

	List<Zone> getZones(Country country, Language language)
			throws ServiceException;

	Map<String, Zone> getZones(Language language) throws ServiceException;

	List<Zone> getZones(String countryCode, Language language) throws ServiceException;


}
