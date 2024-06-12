package dev.vulcanium.site.tech.model.customer;

import dev.vulcanium.business.model.security.PersistableGroup;
import dev.vulcanium.site.tech.model.customer.attribute.PersistableCustomerAttribute;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;

@ApiModel(value="Customer", description="Customer model object")
public class PersistableCustomer extends CustomerEntity {

@ApiModelProperty(notes = "Customer password")
private String password = null;
private String repeatPassword = null;
private static final long serialVersionUID = 1L;
private List<PersistableCustomerAttribute> attributes;
private List<PersistableGroup> groups;


public void setAttributes(List<PersistableCustomerAttribute> attributes) {
	this.attributes = attributes;
}
public List<PersistableCustomerAttribute> getAttributes() {
	return attributes;
}

public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}
public List<PersistableGroup> getGroups() {
	return groups;
}
public void setGroups(List<PersistableGroup> groups) {
	this.groups = groups;
}
public String getRepeatPassword() {
	return repeatPassword;
}
public void setRepeatPassword(String repeatPassword) {
	this.repeatPassword = repeatPassword;
}
}