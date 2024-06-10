package dev.vulcanium.business.model.system.optin;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
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
import jakarta.persistence.UniqueConstraint;

import org.hibernate.annotations.Type;

import dev.vulcanium.business.constants.SchemaConstant;
import dev.vulcanium.business.model.common.audit.AuditListener;
import dev.vulcanium.business.model.generic.SalesManagerEntity;
import dev.vulcanium.business.model.merchant.MerchantStore;

/**
 * Optin defines optin campaigns for the system.
 */
@Entity
@EntityListeners(value = AuditListener.class)
@Table(name = "CUSTOMER_OPTIN",uniqueConstraints=
@UniqueConstraint(columnNames = {"EMAIL", "OPTIN_ID"}))
public class CustomerOptin extends SalesManagerEntity<Long, CustomerOptin> implements Serializable {

private static final long serialVersionUID = 1L;

@Id
@Column(name = "CUSTOMER_OPTIN_ID")
@TableGenerator(name = "TABLE_GEN", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "CUST_OPT_SEQ_NEXT_VAL")
@GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
private Long id;

@Temporal(TemporalType.TIMESTAMP)
@Column (name ="OPTIN_DATE")
private Date optinDate;


@ManyToOne(targetEntity = Optin.class)
@JoinColumn(name="OPTIN_ID")
private Optin optin;

@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name="MERCHANT_ID", nullable=false)
private MerchantStore merchantStore;

@Column(name="FIRST")
private String firstName;

@Column(name="LAST")
private String lastName;

@Column(name="EMAIL", nullable=false)
private String email;

@Column(name="VALUE")
@Type(type = "org.hibernate.type.TextType")
private String value;

@Override
public Long getId() {
	return id;
}

@Override
public void setId(Long id) {
	this.id = id;
}

public Date getOptinDate() {
	return optinDate;
}

public void setOptinDate(Date optinDate) {
	this.optinDate = optinDate;
}

public Optin getOptin() {
	return optin;
}

public void setOptin(Optin optin) {
	this.optin = optin;
}

public String getFirstName() {
	return firstName;
}

public void setFirstName(String firstName) {
	this.firstName = firstName;
}

public String getLastName() {
	return lastName;
}

public void setLastName(String lastName) {
	this.lastName = lastName;
}

public String getEmail() {
	return email;
}

public void setEmail(String email) {
	this.email = email;
}

public String getValue() {
	return value;
}

public void setValue(String value) {
	this.value = value;
}

public MerchantStore getMerchantStore() {
	return merchantStore;
}

public void setMerchantStore(MerchantStore merchantStore) {
	this.merchantStore = merchantStore;
}

}
