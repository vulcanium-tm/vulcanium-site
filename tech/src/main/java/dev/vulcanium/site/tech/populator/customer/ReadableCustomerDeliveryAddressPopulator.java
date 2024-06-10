package dev.vulcanium.site.tech.populator.customer;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import dev.vulcanium.business.exception.ConversionException;
import dev.vulcanium.business.exception.ServiceException;
import dev.vulcanium.business.services.reference.country.CountryService;
import dev.vulcanium.business.services.reference.zone.ZoneService;
import dev.vulcanium.business.utils.AbstractDataPopulator;
import dev.vulcanium.business.model.common.Delivery;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.country.Country;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.business.model.reference.zone.Zone;
import dev.vulcanium.site.tech.model.customer.ReadableDelivery;

public class ReadableCustomerDeliveryAddressPopulator extends AbstractDataPopulator<Delivery, ReadableDelivery>
{


private CountryService countryService;
private ZoneService zoneService;

@Override
public ReadableDelivery populate( Delivery source, ReadableDelivery target, MerchantStore store, Language language )
		throws ConversionException
{
	
	
	if(countryService==null) {
		throw new ConversionException("countryService must be set");
	}
	
	if(zoneService==null) {
		throw new ConversionException("zoneService must be set");
	}
	
	
	target.setLatitude(source.getLatitude());
	target.setLongitude(source.getLongitude());
	
	
	if(StringUtils.isNotBlank( source.getCity() )){
		target.setCity(source.getCity());
	}
	
	if(StringUtils.isNotBlank( source.getCompany() )){
		target.setCompany(source.getCompany());
	}
	
	if(StringUtils.isNotBlank( source.getAddress() )){
		target.setAddress(source.getAddress());
	}
	
	if(StringUtils.isNotBlank( source.getFirstName() )){
		target.setFirstName(source.getFirstName());
	}
	
	if(StringUtils.isNotBlank( source.getLastName() )){
		target.setLastName(source.getLastName());
	}
	
	if(StringUtils.isNotBlank( source.getPostalCode() )){
		target.setPostalCode(source.getPostalCode());
	}
	
	if(StringUtils.isNotBlank( source.getTelephone() )){
		target.setPhone(source.getTelephone());
	}
	
	target.setStateProvince(source.getState());
	
	if(source.getTelephone()==null) {
		target.setPhone(source.getTelephone());
	}
	target.setAddress(source.getAddress());
	if(source.getCountry()!=null) {
		target.setCountry(source.getCountry().getIsoCode());
		
		try {
			Map<String,Country> countries = countryService.getCountriesMap(language);
			Country c =countries.get(source.getCountry().getIsoCode());
			if(c!=null) {
				target.setCountryName(c.getName());
			}
		} catch (ServiceException e) {
			throw new ConversionException(e);
		}
	}
	if(source.getZone()!=null) {
		target.setZone(source.getZone().getCode());
		
		try {
			Map<String,Zone> zones = zoneService.getZones(language);
			Zone z = zones.get(source.getZone().getCode());
			if(z!=null) {
				target.setProvinceName(z.getName());
			}
		} catch (ServiceException e) {
			throw new ConversionException(e);
		}
	}
	
	
	
	return target;
}

@Override
protected ReadableDelivery createTarget() {
	return null;
}

public CountryService getCountryService() {
	return countryService;
}

public void setCountryService(CountryService countryService) {
	this.countryService = countryService;
}

public ZoneService getZoneService() {
	return zoneService;
}

public void setZoneService(ZoneService zoneService) {
	this.zoneService = zoneService;
}


}
