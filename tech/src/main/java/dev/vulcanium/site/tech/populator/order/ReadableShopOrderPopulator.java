package dev.vulcanium.site.tech.populator.order;

import dev.vulcanium.business.exception.ConversionException;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.business.utils.AbstractDataPopulator;
import dev.vulcanium.site.tech.model.customer.PersistableCustomer;
import dev.vulcanium.site.tech.model.customer.ReadableCustomer;
import dev.vulcanium.site.tech.model.customer.address.Address;
import dev.vulcanium.site.tech.model.order.ReadableShopOrder;
import dev.vulcanium.site.tech.model.order.ShopOrder;

public class ReadableShopOrderPopulator extends
		AbstractDataPopulator<ShopOrder, ReadableShopOrder> {

@Override
public ReadableShopOrder populate(ShopOrder source,
                                  ReadableShopOrder target, MerchantStore store, Language language)
		throws ConversionException {
	
	try {
		
		ReadableCustomer customer = new ReadableCustomer();
		PersistableCustomer persistableCustomer = source.getCustomer();
		
		
		customer.setEmailAddress(persistableCustomer.getEmailAddress());
		if(persistableCustomer.getBilling()!=null) {
			Address address = new Address();
			address.setCity(persistableCustomer.getBilling().getCity());
			address.setCompany(persistableCustomer.getBilling().getCompany());
			address.setFirstName(persistableCustomer.getBilling().getFirstName());
			address.setLastName(persistableCustomer.getBilling().getLastName());
			address.setPostalCode(persistableCustomer.getBilling().getPostalCode());
			address.setPhone(persistableCustomer.getBilling().getPhone());
			if(persistableCustomer.getBilling().getCountry()!=null) {
				address.setCountry(persistableCustomer.getBilling().getCountry());
			}
			if(persistableCustomer.getBilling().getZone()!=null) {
				address.setZone(persistableCustomer.getBilling().getZone());
			}
			
			customer.setBilling(address);
		}
		
		if(persistableCustomer.getDelivery()!=null) {
			Address address = new Address();
			address.setCity(persistableCustomer.getDelivery().getCity());
			address.setCompany(persistableCustomer.getDelivery().getCompany());
			address.setFirstName(persistableCustomer.getDelivery().getFirstName());
			address.setLastName(persistableCustomer.getDelivery().getLastName());
			address.setPostalCode(persistableCustomer.getDelivery().getPostalCode());
			address.setPhone(persistableCustomer.getDelivery().getPhone());
			if(persistableCustomer.getDelivery().getCountry()!=null) {
				address.setCountry(persistableCustomer.getDelivery().getCountry());
			}
			if(persistableCustomer.getDelivery().getZone()!=null) {
				address.setZone(persistableCustomer.getDelivery().getZone());
			}
			
			customer.setDelivery(address);
		}
		target.setCustomer(customer);
		
	} catch (Exception e) {
		throw new ConversionException(e);
	}
	
	
	
	return target;
}

@Override
protected ReadableShopOrder createTarget() {
	return null;
}

}
