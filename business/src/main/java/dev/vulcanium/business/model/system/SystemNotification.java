package dev.vulcanium.business.model.system;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
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

import dev.vulcanium.business.constants.SchemaConstant;
import dev.vulcanium.business.model.common.audit.AuditListener;
import dev.vulcanium.business.model.common.audit.AuditSection;
import dev.vulcanium.business.model.common.audit.Auditable;
import dev.vulcanium.business.model.generic.SalesManagerEntity;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.user.User;

@Entity
@EntityListeners(value = AuditListener.class)

@Table(name = "SYSTEM_NOTIFICATION",uniqueConstraints=
@UniqueConstraint(columnNames = {"MERCHANT_ID", "CONFIG_KEY"}) )
public class SystemNotification extends SalesManagerEntity<Long, SystemNotification> implements Serializable, Auditable {
private static final long serialVersionUID = -6269172313628887000L;

@Id
@Column(name = "SYSTEM_NOTIF_ID")
@TableGenerator(name = "TABLE_GEN", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "SYST_NOTIF_SEQ_NEXT_VAL")
@GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
private Long id;

@Column(name="CONFIG_KEY")
private String key;

@Column(name="VALUE")
private String value;

@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name="MERCHANT_ID", nullable=true)
private MerchantStore merchantStore;

@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name="USER_ID", nullable=true)
private User user;

@Temporal(TemporalType.DATE)
@Column(name = "START_DATE")
private Date startDate;

@Temporal(TemporalType.DATE)
@Column(name = "END_DATE")
private Date endDate;

@Embedded
private AuditSection auditSection = new AuditSection();

public AuditSection getAuditSection() {
	return auditSection;
}

public void setAuditSection(AuditSection auditSection) {
	this.auditSection = auditSection;
}

@Override
public Long getId() {
	return id;
}

@Override
public void setId(Long id) {
	this.id = id;
}

public String getKey() {
	return key;
}

public void setKey(String key) {
	this.key = key;
}

public String getValue() {
	return value;
}

public void setValue(String value) {
	this.value = value;
}

public void setStartDate(Date startDate) {
	this.startDate = startDate;
}

public Date getStartDate() {
	return startDate;
}

public void setMerchantStore(MerchantStore merchantStore) {
	this.merchantStore = merchantStore;
}

public MerchantStore getMerchantStore() {
	return merchantStore;
}

public void setEndDate(Date endDate) {
	this.endDate = endDate;
}

public Date getEndDate() {
	return endDate;
}

public void setUser(User user) {
	this.user = user;
}

public User getUser() {
	return user;
}
}
