package dev.vulcanium.business.model.customer;

import java.util.ArrayList;
import java.util.List;

import dev.vulcanium.business.model.common.EntityList;

public class CustomerList extends EntityList {

private static final long serialVersionUID = -3108842276158069739L;
private List<Customer> customers = new ArrayList<>();
public void setCustomers(List<Customer> customers) {
	this.customers = customers;
}
public List<Customer> getCustomers() {
	return customers;
}

}
