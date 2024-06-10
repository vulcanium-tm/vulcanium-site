package dev.vulcanium.site.tech.populator.customer;

import org.apache.commons.lang3.StringUtils;

import dev.vulcanium.business.exception.ConversionException;
import dev.vulcanium.business.utils.AbstractDataPopulator;
import dev.vulcanium.business.model.common.Delivery;
import dev.vulcanium.business.model.customer.Customer;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.site.tech.model.customer.address.Address;

public class PersistableCustomerShippingAddressPopulator extends AbstractDataPopulator<Address, Customer>
{

@Override
public Customer populate( Address source, Customer target, MerchantStore store, Language language )
		throws ConversionException
{
	
	
	if( target.getDelivery() == null){
		
		Delivery delivery=new Delivery();
		delivery.setFirstName( source.getFirstName()) ;
		delivery.setLastName( source.getLastName() );
		
		if(StringUtils.isNotBlank( source.getAddress())){
			delivery.setAddress( source.getAddress() );
		}
		
		if(StringUtils.isNotBlank( source.getCity())){
			delivery.setCity( source.getCity() );
		}
		
		if(StringUtils.isNotBlank( source.getCompany())){
			delivery.setCompany( source.getCompany() );
		}
		
		if(StringUtils.isNotBlank( source.getPhone())){
			delivery.setTelephone( source.getPhone());
		}
		
		if(StringUtils.isNotBlank( source.getPostalCode())){
			delivery.setPostalCode( source.getPostalCode());
		}
		
		if(StringUtils.isNotBlank( source.getStateProvince())){
			delivery.setPostalCode( source.getStateProvince());
		}
		
		target.setDelivery( delivery );
	}
	else{
		target.getDelivery().setFirstName( source.getFirstName() );
		target.getDelivery().setLastName( source.getLastName() );
		
		if(StringUtils.isNotBlank( source.getAddress())){
			target.getDelivery().setAddress( source.getAddress() );
		}
		
		if(StringUtils.isNotBlank( source.getCity())){
			target.getDelivery().setCity( source.getCity() );
		}
		
		if(StringUtils.isNotBlank( source.getCompany())){
			target.getDelivery().setCompany( source.getCompany() );
		}
		
		if(StringUtils.isNotBlank( source.getPhone())){
			target.getDelivery().setTelephone( source.getPhone());
		}
		
		if(StringUtils.isNotBlank( source.getPostalCode())){
			target.getDelivery().setPostalCode( source.getPostalCode());
		}
		
		if(StringUtils.isNotBlank( source.getStateProvince())){
			target.getDelivery().setPostalCode( source.getStateProvince());
		}
	}
	
	return target;
	
}

@Override
protected Customer createTarget()
{
	return null;
}
}
