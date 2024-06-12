package dev.vulcanium.site.tech.populator.order.transaction;

import dev.vulcanium.business.exception.ConversionException;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.order.Order;
import dev.vulcanium.business.model.payments.PaymentType;
import dev.vulcanium.business.model.payments.Transaction;
import dev.vulcanium.business.model.payments.TransactionType;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.business.services.catalog.pricing.PricingService;
import dev.vulcanium.business.services.order.OrderService;
import dev.vulcanium.business.utils.AbstractDataPopulator;
import dev.vulcanium.business.utils.DateUtil;
import dev.vulcanium.site.tech.model.order.transaction.PersistableTransaction;
import org.apache.commons.lang3.Validate;

public class PersistableTransactionPopulator extends AbstractDataPopulator<PersistableTransaction, Transaction> {

private OrderService orderService;
private PricingService pricingService;

@Override
public Transaction populate(PersistableTransaction source, Transaction target, MerchantStore store,
                            Language language) throws ConversionException {
	
	Validate.notNull(source,"PersistableTransaction must not be null");
	Validate.notNull(orderService,"OrderService must not be null");
	Validate.notNull(pricingService,"OrderService must not be null");
	
	if(target == null) {
		target = new Transaction();
	}
	
	
	try {
		
		
		target.setAmount(pricingService.getAmount(source.getAmount()));
		target.setDetails(source.getDetails());
		target.setPaymentType(PaymentType.valueOf(source.getPaymentType()));
		target.setTransactionType(TransactionType.valueOf(source.getTransactionType()));
		target.setTransactionDate(DateUtil.getDate(source.getTransactionDate()));
		
		if(source.getOrderId()!=null && source.getOrderId().longValue() > 0) {
			Order order = orderService.getById(source.getOrderId());
			
			if(order == null) {
				throw new ConversionException("Order with id " + source.getOrderId() + "does not exist");
			}
			target.setOrder(order);
		}
		
		return target;
		
		
		
	} catch(Exception e) {
		throw new ConversionException(e);
	}
	
}

@Override
protected Transaction createTarget() {
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
