package dev.vulcanium.site.tech.populator.order.transaction;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.Validate;

import dev.vulcanium.business.exception.ConversionException;
import dev.vulcanium.business.services.catalog.pricing.PricingService;
import dev.vulcanium.business.utils.AbstractDataPopulator;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.payments.Payment;
import dev.vulcanium.business.model.payments.PaymentType;
import dev.vulcanium.business.model.payments.TransactionType;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.site.tech.model.order.transaction.PersistablePayment;

public class PersistablePaymentPopulator extends AbstractDataPopulator<PersistablePayment, Payment> {


PricingService pricingService;



@Override
public Payment populate(PersistablePayment source, Payment target, MerchantStore store, Language language)
		throws ConversionException {
	
	Validate.notNull(source,"PersistablePayment cannot be null");
	Validate.notNull(pricingService,"pricingService must be set");
	if(target == null) {
		target = new Payment();
	}
	
	try {
		
		target.setAmount(pricingService.getAmount(source.getAmount()));
		target.setModuleName(source.getPaymentModule());
		target.setPaymentType(PaymentType.valueOf(source.getPaymentType()));
		target.setTransactionType(TransactionType.valueOf(source.getTransactionType()));
		
		Map<String,String> metadata = new HashMap<String,String>();
		metadata.put("paymentToken", source.getPaymentToken());
		target.setPaymentMetaData(metadata);
		
		return target;
		
	} catch(Exception e) {
		throw new ConversionException(e);
	}
}

@Override
protected Payment createTarget() {
	return null;
}

public PricingService getPricingService() {
	return pricingService;
}

public void setPricingService(PricingService pricingService) {
	this.pricingService = pricingService;
}

}
