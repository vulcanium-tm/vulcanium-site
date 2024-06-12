package dev.vulcanium.site.tech.mapper.order;

import dev.vulcanium.business.constants.Constants;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.order.OrderTotal;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.business.services.catalog.pricing.PricingService;
import dev.vulcanium.business.store.api.exception.ConversionRuntimeException;
import dev.vulcanium.business.utils.LabelUtils;
import dev.vulcanium.business.utils.LocaleUtils;
import dev.vulcanium.site.tech.mapper.Mapper;
import dev.vulcanium.site.tech.model.order.total.ReadableOrderTotal;
import java.util.Locale;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReadableOrderTotalMapper implements Mapper<OrderTotal, ReadableOrderTotal>{

@Autowired
private PricingService pricingService;

@Autowired
private LabelUtils messages;

@Override
public ReadableOrderTotal convert(OrderTotal source, MerchantStore store, Language language) {
	ReadableOrderTotal destination = new ReadableOrderTotal();
	return this.merge(source, destination, store, language);
}

@Override
public ReadableOrderTotal merge(OrderTotal source, ReadableOrderTotal target, MerchantStore store,
                                Language language) {
	
	Validate.notNull(source, "OrderTotal must not be null");
	Validate.notNull(target, "ReadableTotal must not be null");
	Validate.notNull(store, "MerchantStore must not be null");
	Validate.notNull(language, "Language must not be null");
	
	Locale locale = LocaleUtils.getLocale(language);
	
	try {
		
		target.setCode(source.getOrderTotalCode());
		target.setId(source.getId());
		target.setModule(source.getModule());
		target.setOrder(source.getSortOrder());
		
		target.setTitle(messages.getMessage(source.getOrderTotalCode(), locale, source.getOrderTotalCode()));
		target.setText(source.getText());
		
		target.setValue(source.getValue());
		target.setTotal(pricingService.getDisplayAmount(source.getValue(), store));
		
		if (!StringUtils.isBlank(source.getOrderTotalCode())) {
			if (Constants.OT_DISCOUNT_TITLE.equals(source.getOrderTotalCode())) {
				target.setDiscounted(true);
			}
		}
		
	} catch (Exception e) {
		throw new ConversionRuntimeException(e);
	}
	
	return target;
	
}

}
