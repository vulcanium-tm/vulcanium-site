package dev.vulcanium.site.tech.model.order.transaction;

import dev.vulcanium.business.model.payments.PaymentType;
import dev.vulcanium.business.model.payments.TransactionType;

public class ReadablePayment extends PaymentEntity {

/**
 *
 */
private static final long serialVersionUID = 1L;
private PaymentType paymentType;
private TransactionType transactionType;
public TransactionType getTransactionType() {
	return transactionType;
}
public void setTransactionType(TransactionType transactionType) {
	this.transactionType = transactionType;
}
public PaymentType getPaymentType() {
	return paymentType;
}
public void setPaymentType(PaymentType paymentType) {
	this.paymentType = paymentType;
}

}
