package dev.vulcanium.site.tech.populator.order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dev.vulcanium.business.exception.ConversionException;
import dev.vulcanium.business.services.reference.country.CountryService;
import dev.vulcanium.business.services.reference.zone.ZoneService;
import dev.vulcanium.business.utils.AbstractDataPopulator;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.order.Order;
import dev.vulcanium.business.model.order.OrderTotal;
import dev.vulcanium.business.model.order.OrderTotalType;
import dev.vulcanium.business.model.order.attributes.OrderAttribute;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.site.tech.model.customer.ReadableBilling;
import dev.vulcanium.site.tech.model.customer.ReadableDelivery;
import dev.vulcanium.site.tech.model.customer.address.Address;
import dev.vulcanium.site.tech.model.order.ReadableOrder;
import dev.vulcanium.site.tech.model.store.ReadableMerchantStore;

import org.springframework.beans.factory.annotation.Qualifier;
import dev.vulcanium.site.tech.populator.store.ReadableMerchantStorePopulator;
import dev.vulcanium.site.tech.utils.ImageFilePath;

@Component
public class ReadableOrderPopulator extends
		AbstractDataPopulator<Order, ReadableOrder> {

@Autowired
private CountryService countryService;
@Autowired
private ZoneService zoneService;

@Autowired
@Qualifier("img")
private ImageFilePath filePath;

@Autowired
private ReadableMerchantStorePopulator readableMerchantStorePopulator;


@Override
public ReadableOrder populate(Order source, ReadableOrder target,
                              MerchantStore store, Language language) throws ConversionException {
	
	
	
	target.setId(source.getId());
	target.setDatePurchased(source.getDatePurchased());
	target.setOrderStatus(source.getStatus());
	target.setCurrency(source.getCurrency().getCode());
	target.setPaymentType(source.getPaymentType());
	target.setPaymentModule(source.getPaymentModuleCode());
	target.setShippingModule(source.getShippingModuleCode());
	
	if(source.getMerchant()!=null) {
		ReadableMerchantStore readableStore =
				readableMerchantStorePopulator.populate(source.getMerchant(), null, store, source.getMerchant().getDefaultLanguage());
		target.setStore(readableStore);
	}
	
	
	if(source.getCustomerAgreement()!=null) {
		target.setCustomerAgreed(source.getCustomerAgreement());
	}
	if(source.getConfirmedAddress()!=null) {
		target.setConfirmedAddress(source.getConfirmedAddress());
	}
	
	dev.vulcanium.site.tech.model.order.total.OrderTotal taxTotal = null;
	dev.vulcanium.site.tech.model.order.total.OrderTotal shippingTotal = null;
	
	
	if(source.getBilling()!=null) {
		ReadableBilling address = new ReadableBilling();
		address.setEmail(source.getCustomerEmailAddress());
		address.setCity(source.getBilling().getCity());
		address.setAddress(source.getBilling().getAddress());
		address.setCompany(source.getBilling().getCompany());
		address.setFirstName(source.getBilling().getFirstName());
		address.setLastName(source.getBilling().getLastName());
		address.setPostalCode(source.getBilling().getPostalCode());
		address.setPhone(source.getBilling().getTelephone());
		if(source.getBilling().getCountry()!=null) {
			address.setCountry(source.getBilling().getCountry().getIsoCode());
		}
		if(source.getBilling().getZone()!=null) {
			address.setZone(source.getBilling().getZone().getCode());
		}
		
		target.setBilling(address);
	}
	
	if(source.getOrderAttributes()!=null && source.getOrderAttributes().size()>0) {
		for(OrderAttribute attr : source.getOrderAttributes()) {
			dev.vulcanium.site.tech.model.order.OrderAttribute a = new dev.vulcanium.site.tech.model.order.OrderAttribute();
			a.setKey(attr.getKey());
			a.setValue(attr.getValue());
			target.getAttributes().add(a);
		}
	}
	
	if(source.getDelivery()!=null) {
		ReadableDelivery address = new ReadableDelivery();
		address.setCity(source.getDelivery().getCity());
		address.setAddress(source.getDelivery().getAddress());
		address.setCompany(source.getDelivery().getCompany());
		address.setFirstName(source.getDelivery().getFirstName());
		address.setLastName(source.getDelivery().getLastName());
		address.setPostalCode(source.getDelivery().getPostalCode());
		address.setPhone(source.getDelivery().getTelephone());
		if(source.getDelivery().getCountry()!=null) {
			address.setCountry(source.getDelivery().getCountry().getIsoCode());
		}
		if(source.getDelivery().getZone()!=null) {
			address.setZone(source.getDelivery().getZone().getCode());
		}
		
		target.setDelivery(address);
	}
	
	List<dev.vulcanium.site.tech.model.order.total.OrderTotal> totals = new ArrayList<>();
	for(OrderTotal t : source.getOrderTotal()) {
		if(t.getOrderTotalType()==null) {
			continue;
		}
		if(t.getOrderTotalType().name().equals(OrderTotalType.TOTAL.name())) {
			dev.vulcanium.site.tech.model.order.total.OrderTotal totalTotal = createTotal(t);
			target.setTotal(totalTotal);
			totals.add(totalTotal);
		}
		else if(t.getOrderTotalType().name().equals(OrderTotalType.TAX.name())) {
			dev.vulcanium.site.tech.model.order.total.OrderTotal totalTotal = createTotal(t);
			if(taxTotal==null) {
				taxTotal = totalTotal;
			} else {
				BigDecimal v = taxTotal.getValue();
				v = v.add(totalTotal.getValue());
				taxTotal.setValue(v);
			}
			target.setTax(totalTotal);
			totals.add(totalTotal);
		}
		else if(t.getOrderTotalType().name().equals(OrderTotalType.SHIPPING.name())) {
			dev.vulcanium.site.tech.model.order.total.OrderTotal totalTotal = createTotal(t);
			if(shippingTotal==null) {
				shippingTotal = totalTotal;
			} else {
				BigDecimal v = shippingTotal.getValue();
				v = v.add(totalTotal.getValue());
				shippingTotal.setValue(v);
			}
			target.setShipping(totalTotal);
			totals.add(totalTotal);
		}
		else if(t.getOrderTotalType().name().equals(OrderTotalType.HANDLING.name())) {
			dev.vulcanium.site.tech.model.order.total.OrderTotal totalTotal = createTotal(t);
			if(shippingTotal==null) {
				shippingTotal = totalTotal;
			} else {
				BigDecimal v = shippingTotal.getValue();
				v = v.add(totalTotal.getValue());
				shippingTotal.setValue(v);
			}
			target.setShipping(totalTotal);
			totals.add(totalTotal);
		}
		else if(t.getOrderTotalType().name().equals(OrderTotalType.SUBTOTAL.name())) {
			dev.vulcanium.site.tech.model.order.total.OrderTotal subTotal = createTotal(t);
			totals.add(subTotal);
			
		}
		else {
			dev.vulcanium.site.tech.model.order.total.OrderTotal otherTotal = createTotal(t);
			totals.add(otherTotal);
		}
	}
	
	target.setTotals(totals);
	
	return target;
}

private dev.vulcanium.site.tech.model.order.total.OrderTotal createTotal(OrderTotal t) {
	dev.vulcanium.site.tech.model.order.total.OrderTotal totalTotal = new dev.vulcanium.site.tech.model.order.total.OrderTotal();
	totalTotal.setCode(t.getOrderTotalCode());
	totalTotal.setId(t.getId());
	totalTotal.setModule(t.getModule());
	totalTotal.setOrder(t.getSortOrder());
	totalTotal.setValue(t.getValue());
	return totalTotal;
}

@Override
protected ReadableOrder createTarget() {
	
	return null;
}

}
