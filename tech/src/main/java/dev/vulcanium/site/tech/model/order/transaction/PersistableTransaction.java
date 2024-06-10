package dev.vulcanium.site.tech.model.order.transaction;

import java.io.Serializable;
import dev.vulcanium.business.model.payments.PaymentType;
import dev.vulcanium.business.model.payments.TransactionType;

/**
 * This class is used for writing a transaction in the System
 */
public class PersistableTransaction extends TransactionEntity implements Serializable {


private static final long serialVersionUID = 1L;

@dev.vulcanium.site.tech.validation.Enum(enumClass=PaymentType.class, ignoreCase=true)
private String paymentType;

@dev.vulcanium.site.tech.validation.Enum(enumClass=TransactionType.class, ignoreCase=true)
private String transactionType;

public String getPaymentType() {
	return paymentType;
}

public void setPaymentType(String paymentType) {
	this.paymentType = paymentType;
}

public String getTransactionType() {
	return transactionType;
}

public void setTransactionType(String transactionType) {
	this.transactionType = transactionType;
}
}
