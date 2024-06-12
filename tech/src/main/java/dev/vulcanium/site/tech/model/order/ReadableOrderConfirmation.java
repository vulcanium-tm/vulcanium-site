package dev.vulcanium.site.tech.model.order;

import dev.vulcanium.business.model.entity.Entity;
import dev.vulcanium.site.tech.model.customer.address.Address;
import dev.vulcanium.site.tech.model.order.total.ReadableTotal;
import java.util.List;

public class ReadableOrderConfirmation extends Entity {

private static final long serialVersionUID = 1L;
private Address billing;
private Address delivery;
private String shipping;
private String payment;
private ReadableTotal total;
private List<ReadableOrderProduct> products;

public String getShipping() {
	return shipping;
}
public void setShipping(String shipping) {
	this.shipping = shipping;
}
public String getPayment() {
	return payment;
}
public void setPayment(String payment) {
	this.payment = payment;
}
public ReadableTotal getTotal() {
	return total;
}
public void setTotal(ReadableTotal total) {
	this.total = total;
}
public List<ReadableOrderProduct> getProducts() {
	return products;
}
public void setProducts(List<ReadableOrderProduct> products) {
	this.products = products;
}
public Address getBilling() {
	return billing;
}
public void setBilling(Address billing) {
	this.billing = billing;
}
public Address getDelivery() {
	return delivery;
}
public void setDelivery(Address delivery) {
	this.delivery = delivery;
}

}
