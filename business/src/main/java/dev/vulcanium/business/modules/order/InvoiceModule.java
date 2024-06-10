package dev.vulcanium.business.modules.order;

import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.order.Order;
import dev.vulcanium.business.model.reference.language.Language;
import java.io.ByteArrayOutputStream;


public interface InvoiceModule {
	
	ByteArrayOutputStream createInvoice(MerchantStore store, Order order, Language language) throws Exception;

}
