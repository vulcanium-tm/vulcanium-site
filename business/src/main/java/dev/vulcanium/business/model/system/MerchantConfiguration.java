package dev.vulcanium.business.model.system;

import java.io.Serializable;
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
import jakarta.persistence.UniqueConstraint;
import org.hibernate.annotations.Type;
import dev.vulcanium.business.constants.SchemaConstant;
import dev.vulcanium.business.model.common.audit.AuditListener;
import dev.vulcanium.business.model.common.audit.AuditSection;
import dev.vulcanium.business.model.common.audit.Auditable;
import dev.vulcanium.business.model.generic.SalesManagerEntity;
import dev.vulcanium.business.model.merchant.MerchantStore;

/**
 * Merchant configuration information
 */
@Entity
@EntityListeners(value = AuditListener.class)
@Table(name = "MERCHANT_CONFIGURATION",
		uniqueConstraints = @UniqueConstraint(columnNames = {"MERCHANT_ID", "CONFIG_KEY"}))
public class MerchantConfiguration extends SalesManagerEntity<Long, MerchantConfiguration>
		implements Serializable, Auditable {

private static final long serialVersionUID = 4246917986731953459L;

@Id
@Column(name = "MERCHANT_CONFIG_ID")
@TableGenerator(name = "TABLE_GEN", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME",
		valueColumnName = "SEQ_COUNT", pkColumnValue = "MERCH_CONF_SEQ_NEXT_VAL")
@GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
private Long id;

@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "MERCHANT_ID", nullable = true)
private MerchantStore merchantStore;

@Embedded
private AuditSection auditSection = new AuditSection();

@Column(name = "CONFIG_KEY")
private String key;

/**
 * activate and deactivate configuration
 */
@Column(name = "ACTIVE", nullable = true)
private Boolean active = new Boolean(false);


@Column(name = "VALUE")
@Type(type = "org.hibernate.type.TextType")
private String value;

@Column(name = "TYPE")
@Enumerated(value = EnumType.STRING)
private MerchantConfigurationType merchantConfigurationType =
		MerchantConfigurationType.INTEGRATION;

public void setKey(String key) {
	this.key = key;
}

public String getKey() {
	return key;
}

public void setValue(String value) {
	this.value = value;
}

public String getValue() {
	return value;
}

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



public MerchantStore getMerchantStore() {
	return merchantStore;
}

public void setMerchantStore(MerchantStore merchantStore) {
	this.merchantStore = merchantStore;
}

public void setMerchantConfigurationType(MerchantConfigurationType merchantConfigurationType) {
	this.merchantConfigurationType = merchantConfigurationType;
}

public MerchantConfigurationType getMerchantConfigurationType() {
	return merchantConfigurationType;
}

public Boolean getActive() {
	return active;
}

public void setActive(Boolean active) {
	this.active = active;
}


}
