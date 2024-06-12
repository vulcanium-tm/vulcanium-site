package dev.vulcanium.site.tech.model.order;

import dev.vulcanium.business.model.reference.currency.Currency;
import dev.vulcanium.business.model.shipping.ShippingOption;
import dev.vulcanium.site.tech.model.customer.ReadableBilling;
import dev.vulcanium.site.tech.model.customer.ReadableCustomer;
import dev.vulcanium.site.tech.model.customer.ReadableDelivery;
import dev.vulcanium.site.tech.model.customer.address.Address;
import dev.vulcanium.site.tech.model.order.total.OrderTotal;
import dev.vulcanium.site.tech.model.order.total.ReadableTotal;
import dev.vulcanium.site.tech.model.order.transaction.ReadablePayment;
import dev.vulcanium.site.tech.model.store.ReadableMerchantStore;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Setter
public class ReadableOrder extends Order {

private static final long serialVersionUID = 1L;

@Getter
private ReadableBilling billing;
private ReadableDelivery delivery;
@Getter
private ShippingOption shippingOption;
@Getter
private ReadablePayment payment;
@Getter
private ReadableTotal total;
@Getter
private List<ReadableOrderProduct> products;

@Getter
private ReadableCustomer customer;
private Currency currencyModel;

@Getter
private ReadableMerchantStore store;
@Getter
private OrderTotal tax;
@Getter
private OrderTotal shipping;

public Address getDelivery(){
	return delivery;
}
}