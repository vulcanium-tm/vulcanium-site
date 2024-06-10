package dev.vulcanium.site.tech.model.customer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import dev.vulcanium.site.tech.model.customer.attribute.ReadableCustomerAttribute;
import dev.vulcanium.site.tech.model.security.ReadableGroup;

public class ReadableCustomer extends CustomerEntity implements Serializable {

/**
 *
 */
private static final long serialVersionUID = 1L;
private List<ReadableCustomerAttribute> attributes = new ArrayList<ReadableCustomerAttribute>();
private List<ReadableGroup> groups = new ArrayList<ReadableGroup>();

public void setAttributes(List<ReadableCustomerAttribute> attributes) {
	this.attributes = attributes;
}
public List<ReadableCustomerAttribute> getAttributes() {
	return attributes;
}
public List<ReadableGroup> getGroups() {
	return groups;
}
public void setGroups(List<ReadableGroup> groups) {
	this.groups = groups;
}

}
