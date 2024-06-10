package dev.vulcanium.site.tech.populator.order;

import dev.vulcanium.business.exception.ConversionException;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.order.orderproduct.OrderProductDownload;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.business.utils.AbstractDataPopulator;
import dev.vulcanium.site.tech.model.order.ReadableOrderProductDownload;

public class ReadableOrderProductDownloadPopulator extends
		AbstractDataPopulator<OrderProductDownload, ReadableOrderProductDownload> {

@Override
public ReadableOrderProductDownload populate(OrderProductDownload source,
                                             ReadableOrderProductDownload target, MerchantStore store,
                                             Language language) throws ConversionException {
	try {
		
		target.setProductName(source.getOrderProduct().getProductName());
		target.setDownloadCount(source.getDownloadCount());
		target.setDownloadExpiryDays(source.getMaxdays());
		target.setId(source.getId());
		target.setFileName(source.getOrderProductFilename());
		target.setOrderId(source.getOrderProduct().getOrder().getId());
		
		return target;
		
	} catch(Exception e) {
		throw new ConversionException(e);
	}
}

@Override
protected ReadableOrderProductDownload createTarget() {
	return new ReadableOrderProductDownload();
}


}
