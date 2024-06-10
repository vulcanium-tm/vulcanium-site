package dev.vulcanium.business.model.payments;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.TableGenerator;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;

import org.hibernate.annotations.Type;
import org.json.simple.JSONAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.vulcanium.business.model.common.audit.AuditListener;
import dev.vulcanium.business.model.common.audit.AuditSection;
import dev.vulcanium.business.model.common.audit.Auditable;
import dev.vulcanium.business.model.generic.SalesManagerEntity;
import dev.vulcanium.business.model.order.Order;


@Entity
@EntityListeners(value = AuditListener.class)
@Table(name = "SM_TRANSACTION")
public class Transaction extends SalesManagerEntity<Long, Transaction> implements Serializable, Auditable, JSONAware {


private static final Logger LOGGER = LoggerFactory.getLogger(Transaction.class);

private static final long serialVersionUID = 1L;

@Id
@Column(name = "TRANSACTION_ID")
@TableGenerator(name = "TABLE_GEN", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "TRANSACT_SEQ_NEXT_VAL")
@GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
private Long id;

@Embedded
private AuditSection auditSection = new AuditSection();


@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name="ORDER_ID", nullable=true)
private Order order;

@Column(name="AMOUNT")
private BigDecimal amount;

@Column(name="TRANSACTION_DATE")
@Temporal(TemporalType.TIMESTAMP)
private Date transactionDate;

@Column(name="TRANSACTION_TYPE")
@Enumerated(value = EnumType.STRING)
private TransactionType transactionType;

@Column(name="PAYMENT_TYPE")
@Enumerated(value = EnumType.STRING)
private PaymentType paymentType;

@Column(name="DETAILS")
@Type(type = "org.hibernate.type.TextType")
private String details;

@Transient
private Map<String,String> transactionDetails= new HashMap<String,String>();

@Override
public AuditSection getAuditSection() {
	return this.auditSection;
}

@Override
public void setAuditSection(AuditSection audit) {
	this.auditSection = audit;
	
}

@Override
public Long getId() {
	return this.id;
}

@Override
public void setId(Long id) {
	this.id = id;
	
}

public Order getOrder() {
	return order;
}

public void setOrder(Order order) {
	this.order = order;
}

public BigDecimal getAmount() {
	return amount;
}

public void setAmount(BigDecimal amount) {
	this.amount = amount;
}

public Date getTransactionDate() {
	return transactionDate;
}

public void setTransactionDate(Date transactionDate) {
	this.transactionDate = transactionDate;
}

public TransactionType getTransactionType() {
	return transactionType;
}

public String getTransactionTypeName() {
	return this.getTransactionType()!=null?this.getTransactionType().name():"";
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

public String getDetails() {
	return details;
}

public void setDetails(String details) {
	this.details = details;
}

public Map<String, String> getTransactionDetails() {
	return transactionDetails;
}

public void setTransactionDetails(Map<String, String> transactionDetails) {
	this.transactionDetails = transactionDetails;
}

@Override
public String toJSONString() {
	
	if(this.getTransactionDetails()!=null && this.getTransactionDetails().size()>0) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(this.getTransactionDetails());
		} catch (Exception e) {
			LOGGER.error("Cannot parse transactions map",e);
		}
		
	}
	
	return null;
}

}
