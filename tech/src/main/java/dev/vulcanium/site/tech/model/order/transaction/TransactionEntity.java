package dev.vulcanium.site.tech.model.order.transaction;

import java.io.Serializable;

import dev.vulcanium.site.tech.model.entity.Entity;

/**
 * Readable version of Transaction entity object
 */
public class TransactionEntity extends Entity implements Serializable {

private static final long serialVersionUID = 1L;
private Long orderId;
private String details;
private String transactionDate;
private String amount;


public String getTransactionDate() {
	return transactionDate;
}
public void setTransactionDate(String transactionDate) {
	this.transactionDate = transactionDate;
}
public Long getOrderId() {
	return orderId;
}
public void setOrderId(Long orderId) {
	this.orderId = orderId;
}
public String getDetails() {
	return details;
}
public void setDetails(String details) {
	this.details = details;
}
public String getAmount() {
	return amount;
}
public void setAmount(String amount) {
	this.amount = amount;
}


}