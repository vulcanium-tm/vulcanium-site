package dev.vulcanium.site.tech.populator.customer;

import org.apache.commons.lang3.StringUtils;

import dev.vulcanium.business.exception.ConversionException;
import dev.vulcanium.business.utils.AbstractDataPopulator;
import dev.vulcanium.business.model.customer.Customer;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.site.tech.model.customer.address.Address;

public class PersistableCustomerBillingAddressPopulator extends AbstractDataPopulator<Address, Customer>
{

@Override
public Customer populate( Address source, Customer target, MerchantStore store, Language language )
		throws ConversionException
{
	
	
	target.getBilling().setFirstName( source.getFirstName() );
	target.getBilling().setLastName( source.getLastName() );
	
	if(StringUtils.isNotBlank( source.getAddress())){
		target.getBilling().setAddress( source.getAddress() );
	}
	
	if(StringUtils.isNotBlank( source.getCity())){
		target.getBilling().setCity( source.getCity() );
	}
	
	if(StringUtils.isNotBlank( source.getCompany())){
		target.getBilling().setCompany( source.getCompany() );
	}
	
	if(StringUtils.isNotBlank( source.getPhone())){
		target.getBilling().setTelephone( source.getPhone());
	}
	
	if(StringUtils.isNotBlank( source.getPostalCode())){
		target.getBilling().setPostalCode( source.getPostalCode());
	}
	
	if(StringUtils.isNotBlank( source.getStateProvince())){
		target.getBilling().setState(source.getStateProvince());
	}
	
	return target;
	
}

@Override
protected Customer createTarget()
{
	return null;
}



}
