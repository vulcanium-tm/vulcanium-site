package dev.vulcanium.business.model.system;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.TableGenerator;

import dev.vulcanium.business.constants.SchemaConstant;
import dev.vulcanium.business.model.common.audit.AuditListener;
import dev.vulcanium.business.model.common.audit.AuditSection;
import dev.vulcanium.business.model.common.audit.Auditable;
import dev.vulcanium.business.model.generic.SalesManagerEntity;

/**
 * Global system configuration information
 */
@Entity
@EntityListeners(value = AuditListener.class)
@Table(name = "SYSTEM_CONFIGURATION")
public class SystemConfiguration extends SalesManagerEntity<Long, SystemConfiguration> implements Serializable, Auditable {
private static final long serialVersionUID = 1L;

@Id
@Column(name = "SYSTEM_CONFIG_ID")
@TableGenerator(name = "TABLE_GEN", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "SYST_CONF_SEQ_NEXT_VAL")
@GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
private Long id;

@Column(name="CONFIG_KEY")
private String key;

@Column(name="VALUE")
private String value;

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
}
