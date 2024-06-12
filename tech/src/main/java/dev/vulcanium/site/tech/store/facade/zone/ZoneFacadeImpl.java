package dev.vulcanium.site.tech.store.facade.zone;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.inject.Inject;

import org.springframework.stereotype.Service;

import dev.vulcanium.business.exception.ConversionException;
import dev.vulcanium.business.exception.ServiceException;
import dev.vulcanium.business.services.reference.zone.ZoneService;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.business.model.reference.zone.Zone;
import dev.vulcanium.site.tech.model.references.ReadableZone;
import dev.vulcanium.site.tech.populator.references.ReadableZonePopulator;
import dev.vulcanium.business.store.api.exception.ConversionRuntimeException;
import dev.vulcanium.business.store.api.exception.ServiceRuntimeException;

@Service
public class ZoneFacadeImpl implements ZoneFacade{

@Inject
private ZoneService zoneService;

@Override
public List<ReadableZone> getZones(String countryCode, Language language, MerchantStore merchantStore) {
	List<Zone> listZones = getListZones(countryCode, language);
	if (listZones.isEmpty()){
		return Collections.emptyList();
	}
	return listZones.stream()
			       .map(zone -> convertToReadableZone(zone, language, merchantStore))
			       .collect(Collectors.toList());
}

private ReadableZone convertToReadableZone(Zone zone, Language language, MerchantStore merchantStore) {
	try{
		ReadableZonePopulator populator = new ReadableZonePopulator();
		return populator.populate(zone, new ReadableZone(), merchantStore, language);
	} catch (ConversionException e){
		throw new ConversionRuntimeException(e);
	}
}

private List<Zone> getListZones(String countryCode, Language language) {
	try{
		return zoneService.getZones(countryCode, language);
	} catch (ServiceException e){
		throw new ServiceRuntimeException(e);
	}
}
}
