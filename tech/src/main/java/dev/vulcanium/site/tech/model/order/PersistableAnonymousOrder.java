package dev.vulcanium.site.tech.model.order;

import dev.vulcanium.site.tech.model.customer.PersistableCustomer;

public class PersistableAnonymousOrder extends PersistableOrder {

private static final long serialVersionUID = 1L;

private PersistableCustomer customer;

public PersistableCustomer getCustomer() {
	return customer;
}

public void setCustomer(PersistableCustomer customer) {
	this.customer = customer;
}

}
