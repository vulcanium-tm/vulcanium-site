package dev.vulcanium.site.tech.model.order.transaction;

import java.io.Serializable;

public class PaymentEntity implements Serializable {

private static final long serialVersionUID = 1L;

private String paymentModule;
private String amount;

public String getPaymentModule() {
	return paymentModule;
}
public void setPaymentModule(String paymentModule) {
	this.paymentModule = paymentModule;
}
public String getAmount() {
	return amount;
}
public void setAmount(String amount) {
	this.amount = amount;
}

}
