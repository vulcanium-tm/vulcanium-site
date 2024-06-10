package dev.vulcanium.site.tech.model.customer.attribute;

import java.io.Serializable;

public class ReadableCustomerOptionValue extends CustomerOptionValueEntity
		implements Serializable {

private static final long serialVersionUID = 1L;
private CustomerOptionValueDescription description;
public void setDescription(CustomerOptionValueDescription description) {
	this.description = description;
}
public CustomerOptionValueDescription getDescription() {
	return description;
}



}
