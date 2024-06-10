package dev.vulcanium.business.model.system;

import java.io.Serializable;

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

import org.hibernate.annotations.Type;

import dev.vulcanium.business.constants.SchemaConstant;
import dev.vulcanium.business.model.common.audit.AuditListener;
import dev.vulcanium.business.model.generic.SalesManagerEntity;
import dev.vulcanium.business.model.merchant.MerchantStore;

@Entity
@EntityListeners(value = AuditListener.class)
@Table(name = "MERCHANT_LOG")
public class MerchantLog extends SalesManagerEntity<Long, MerchantLog> implements Serializable {

private static final long serialVersionUID = 1L;

@Id
@Column(name = "MERCHANT_LOG_ID")
@TableGenerator(name = "TABLE_GEN", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "MR_LOG_SEQ_NEXT_VAL")
@GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
private Long id;

@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name="MERCHANT_ID", nullable=false)
private MerchantStore store;

@Column(name="MODULE", length=25, nullable=true)
private String module;


@Column(name="LOG")
@Type(type = "org.hibernate.type.TextType")
private String log;

public MerchantLog(MerchantStore store, String log) {
	this.store = store;
	this.log = log;
}

public MerchantLog(MerchantStore store, String module, String log) {
	this.store = store;
	this.module = module;
	this.log = log;
}


public Long getId() {
	return id;
}


public void setId(Long id) {
	this.id = id;
}


public MerchantStore getStore() {
	return store;
}


public void setStore(MerchantStore store) {
	this.store = store;
}


public String getModule() {
	return module;
}


public void setModule(String module) {
	this.module = module;
}


public String getLog() {
	return log;
}


public void setLog(String log) {
	this.log = log;
}


}
