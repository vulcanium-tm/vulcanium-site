package dev.vulcanium.site.tech.populator.customer;

import org.apache.commons.lang3.StringUtils;

import dev.vulcanium.business.exception.ConversionException;
import dev.vulcanium.business.utils.AbstractDataPopulator;
import dev.vulcanium.business.model.customer.Customer;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.site.tech.model.customer.address.Address;

public class CustomerDeliveryAddressPopulator extends AbstractDataPopulator<Customer, Address>
{

@Override
public Address populate( Customer source, Address target, MerchantStore store, Language language )
		throws ConversionException
{
	
	if(source.getDelivery()!=null){
		if(StringUtils.isNotBlank( source.getDelivery().getCity() )){
			target.setCity(source.getDelivery().getCity());
		}
		
		if(StringUtils.isNotBlank( source.getDelivery().getCompany() )){
			target.setCompany(source.getDelivery().getCompany());
		}
		
		if(StringUtils.isNotBlank( source.getDelivery().getAddress() )){
			target.setAddress(source.getDelivery().getAddress());
		}
		
		if(StringUtils.isNotBlank( source.getDelivery().getFirstName() )){
			target.setFirstName(source.getDelivery().getFirstName());
		}
		
		if(StringUtils.isNotBlank( source.getDelivery().getLastName() )){
			target.setLastName(source.getDelivery().getLastName());
		}
		
		if(StringUtils.isNotBlank( source.getDelivery().getPostalCode() )){
			target.setPostalCode(source.getDelivery().getPostalCode());
		}
		
		if(StringUtils.isNotBlank( source.getDelivery().getTelephone() )){
			target.setPhone(source.getDelivery().getTelephone());
		}
		
		target.setStateProvince(source.getDelivery().getState());
		
		if(source.getDelivery().getTelephone()==null) {
			target.setPhone(source.getDelivery().getTelephone());
		}
		target.setAddress(source.getDelivery().getAddress());
		if(source.getDelivery().getCountry()!=null) {
			target.setCountry(source.getDelivery().getCountry().getIsoCode());
		}
		if(source.getDelivery().getZone()!=null) {
			target.setZone(source.getDelivery().getZone().getCode());
		}
	}
	return target;
}

@Override
protected Address createTarget()
{
	return new Address();
}

}

