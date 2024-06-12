package dev.vulcanium.site.tech.populator.order;

import dev.vulcanium.business.exception.ConversionException;
import dev.vulcanium.business.model.common.Billing;
import dev.vulcanium.business.model.common.Delivery;
import dev.vulcanium.business.model.customer.Customer;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.order.Order;
import dev.vulcanium.business.model.order.OrderChannel;
import dev.vulcanium.business.model.order.attributes.OrderAttribute;
import dev.vulcanium.business.model.order.orderstatus.OrderStatus;
import dev.vulcanium.business.model.order.orderstatus.OrderStatusHistory;
import dev.vulcanium.business.model.payments.PaymentType;
import dev.vulcanium.business.model.reference.currency.Currency;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.business.services.customer.CustomerService;
import dev.vulcanium.business.services.reference.currency.CurrencyService;
import dev.vulcanium.business.utils.AbstractDataPopulator;
import dev.vulcanium.business.utils.LocaleUtils;
import dev.vulcanium.site.tech.model.customer.PersistableCustomer;
import dev.vulcanium.site.tech.model.order.PersistableAnonymousOrder;
import dev.vulcanium.site.tech.model.order.PersistableOrder;
import dev.vulcanium.site.tech.populator.customer.CustomerPopulator;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PersistableOrderApiPopulator extends AbstractDataPopulator<PersistableOrder, Order> {

@Autowired
private CurrencyService currencyService;
@Autowired
private CustomerService customerService;
@Autowired
private CustomerPopulator customerPopulator;






@Override
public Order populate(PersistableOrder source, Order target, MerchantStore store, Language language)
		throws ConversionException {
	Validate.notNull(source.getPayment(),"Payment cannot be null");
	
	try {
		
		if(target == null) {
			target = new Order();
		}
		target.setLocale(LocaleUtils.getLocale(store));
		
		
		Currency currency = null;
		try {
			currency = currencyService.getByCode(source.getCurrency());
		} catch(Exception e) {
			throw new ConversionException("Currency not found for code " + source.getCurrency());
		}
		
		if(currency==null) {
			throw new ConversionException("Currency not found for code " + source.getCurrency());
		}
		
		Customer customer;
		if(source.getCustomerId() != null && source.getCustomerId().longValue() >0) {
			Long customerId = source.getCustomerId();
			customer = customerService.getById(customerId);
			
			if(customer == null) {
				throw new ConversionException("Curstomer with id " + source.getCustomerId() + " does not exist");
			}
			target.setCustomerId(customerId);
			
		} else {
			if(source instanceof PersistableAnonymousOrder) {
				PersistableCustomer persistableCustomer = ((PersistableAnonymousOrder)source).getCustomer();
				customer = new Customer();
				customer = customerPopulator.populate(persistableCustomer, customer, store, language);
			} else {
				throw new ConversionException("Curstomer details or id not set in request");
			}
		}
		
		
		target.setCustomerEmailAddress(customer.getEmailAddress());
		
		Delivery delivery = customer.getDelivery();
		target.setDelivery(delivery);
		
		Billing billing = customer.getBilling();
		target.setBilling(billing);
		
		if(source.getAttributes() != null && source.getAttributes().size() > 0) {
			Set<OrderAttribute> attrs = new HashSet<OrderAttribute>();
			for(dev.vulcanium.site.tech.model.order.OrderAttribute attribute : source.getAttributes()) {
				OrderAttribute attr = new OrderAttribute();
				attr.setKey(attribute.getKey());
				attr.setValue(attribute.getValue());
				attr.setOrder(target);
				attrs.add(attr);
			}
			target.setOrderAttributes(attrs);
		}
		
		target.setDatePurchased(new Date());
		target.setCurrency(currency);
		target.setCurrencyValue(new BigDecimal(0));
		target.setMerchant(store);
		target.setChannel(OrderChannel.API);
		target.setStatus(OrderStatus.ORDERED);
		target.setPaymentModuleCode(source.getPayment().getPaymentModule());
		target.setPaymentType(PaymentType.valueOf(source.getPayment().getPaymentType()));
		
		target.setCustomerAgreement(source.getCustomerAgreement());
		target.setConfirmedAddress(true);
		
		
		if(!StringUtils.isBlank(source.getComments())) {
			OrderStatusHistory statusHistory = new OrderStatusHistory();
			statusHistory.setStatus(null);
			statusHistory.setOrder(target);
			statusHistory.setComments(source.getComments());
			target.getOrderHistory().add(statusHistory);
		}
		
		return target;
		
	} catch(Exception e) {
		throw new ConversionException(e);
	}
}

@Override
protected Order createTarget() {
	return null;
}

}
