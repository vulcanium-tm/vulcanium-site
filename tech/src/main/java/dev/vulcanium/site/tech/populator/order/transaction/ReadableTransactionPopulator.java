package dev.vulcanium.site.tech.populator.order.transaction;

import dev.vulcanium.business.exception.ConversionException;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.payments.Transaction;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.business.services.catalog.pricing.PricingService;
import dev.vulcanium.business.services.order.OrderService;
import dev.vulcanium.business.utils.AbstractDataPopulator;
import dev.vulcanium.business.utils.DateUtil;
import dev.vulcanium.site.tech.model.order.transaction.ReadableTransaction;
import org.apache.commons.lang3.Validate;

public class ReadableTransactionPopulator extends AbstractDataPopulator<Transaction, ReadableTransaction> {


private OrderService orderService;
private PricingService pricingService;

@Override
public ReadableTransaction populate(Transaction source, ReadableTransaction target, MerchantStore store,
                                    Language language) throws ConversionException {
	
	
	Validate.notNull(source,"PersistableTransaction must not be null");
	Validate.notNull(orderService,"OrderService must not be null");
	Validate.notNull(pricingService,"OrderService must not be null");
	
	if(target == null) {
		target = new ReadableTransaction();
	}
	
	
	try {
		
		
		target.setAmount(pricingService.getDisplayAmount(source.getAmount(), store));
		target.setDetails(source.getDetails());
		target.setPaymentType(source.getPaymentType());
		target.setTransactionType(source.getTransactionType());
		target.setTransactionDate(DateUtil.formatDate(source.getTransactionDate()));
		target.setId(source.getId());
		
		if(source.getOrder() != null) {
			target.setOrderId(source.getOrder().getId());
			
		}
		
		return target;
		
		
		
	} catch(Exception e) {
		throw new ConversionException(e);
	}
	
}

@Override
protected ReadableTransaction createTarget() {
	return null;
}

public OrderService getOrderService() {
	return orderService;
}

public void setOrderService(OrderService orderService) {
	this.orderService = orderService;
}

public PricingService getPricingService() {
	return pricingService;
}

public void setPricingService(PricingService pricingService) {
	this.pricingService = pricingService;
}

}
