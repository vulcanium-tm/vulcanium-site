package dev.vulcanium.site.tech.populator.customer;

import java.util.ArrayList;
import java.util.List;
import dev.vulcanium.site.tech.model.customer.ReadableCustomer;
import dev.vulcanium.site.tech.model.entity.ReadableList;

public class ReadableCustomerList extends ReadableList {

private static final long serialVersionUID = 1L;

private List<ReadableCustomer> customers = new ArrayList<ReadableCustomer>();

public List<ReadableCustomer> getCustomers() {
	return customers;
}

public void setCustomers(List<ReadableCustomer> customers) {
	this.customers = customers;
}

}
