package dev.vulcanium.site.tech.populator.order;

import dev.vulcanium.business.exception.ConversionException;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.order.OrderTotal;
import dev.vulcanium.business.model.order.OrderTotalSummary;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.business.services.catalog.pricing.PricingService;
import dev.vulcanium.business.utils.AbstractDataPopulator;
import dev.vulcanium.business.utils.LabelUtils;
import dev.vulcanium.site.tech.model.order.ReadableOrderTotalSummary;
import dev.vulcanium.site.tech.model.order.total.ReadableOrderTotal;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

@Getter
@Setter
public class ReadableOrderSummaryPopulator extends AbstractDataPopulator<OrderTotalSummary, ReadableOrderTotalSummary> {

private static final Logger LOGGER = LoggerFactory
		                                     .getLogger(ReadableOrderSummaryPopulator.class);

private PricingService pricingService;

private LabelUtils messages;



@Override
public ReadableOrderTotalSummary populate(OrderTotalSummary source, ReadableOrderTotalSummary target,
                                          MerchantStore store, Language language) throws ConversionException {
	
	Validate.notNull(pricingService,"PricingService must be set");
	Validate.notNull(messages,"LabelUtils must be set");
	
	if(target==null) {
		target = new ReadableOrderTotalSummary();
	}
	
	try {
		
		if(source.getSubTotal() != null) {
			target.setSubTotal(pricingService.getDisplayAmount(source.getSubTotal(), store));
		}
		if(source.getTaxTotal()!=null) {
			target.setTaxTotal(pricingService.getDisplayAmount(source.getTaxTotal(), store));
		}
		if(source.getTotal() != null) {
			target.setTotal(pricingService.getDisplayAmount(source.getTotal(), store));
		}
		
		if(!CollectionUtils.isEmpty(source.getTotals())) {
			ReadableOrderTotalPopulator orderTotalPopulator = new ReadableOrderTotalPopulator();
			orderTotalPopulator.setMessages(messages);
			orderTotalPopulator.setPricingService(pricingService);
			for(OrderTotal orderTotal : source.getTotals()) {
				ReadableOrderTotal t = new ReadableOrderTotal();
				orderTotalPopulator.populate(orderTotal, t, store, language);
				target.getTotals().add(t);
			}
		}
		
		
	} catch(Exception e) {
		LOGGER.error("Error during amount formatting " + e.getMessage());
		throw new ConversionException(e);
	}
	
	return target;
	
}

@Override
protected ReadableOrderTotalSummary createTarget() {
	return null;
}


}
