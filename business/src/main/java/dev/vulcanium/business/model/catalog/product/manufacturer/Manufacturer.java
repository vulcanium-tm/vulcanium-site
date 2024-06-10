package dev.vulcanium.business.model.catalog.product.manufacturer;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.TableGenerator;
import jakarta.persistence.UniqueConstraint;

import jakarta.validation.constraints.NotEmpty;

import dev.vulcanium.business.constants.SchemaConstant;
import dev.vulcanium.business.model.common.audit.AuditListener;
import dev.vulcanium.business.model.common.audit.AuditSection;
import dev.vulcanium.business.model.common.audit.Auditable;
import dev.vulcanium.business.model.generic.SalesManagerEntity;
import dev.vulcanium.business.model.merchant.MerchantStore;
import lombok.Getter;
import lombok.Setter;

@Entity
@EntityListeners(value = AuditListener.class)
@Table(name = "MANUFACTURER", uniqueConstraints=
@UniqueConstraint(columnNames = {"MERCHANT_ID", "CODE"}) )
public class Manufacturer extends SalesManagerEntity<Long, Manufacturer> implements Auditable {
private static final long serialVersionUID = 1L;

public static final String DEFAULT_MANUFACTURER = "DEFAULT";

@Id
@Column(name = "MANUFACTURER_ID", unique=true, nullable=false)
@TableGenerator(name = "TABLE_GEN", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "MANUFACT_SEQ_NEXT_VAL")
@GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
private Long id;

@Embedded
private AuditSection auditSection = new AuditSection();

@Getter
@Setter
@OneToMany(mappedBy = "manufacturer", cascade = CascadeType.ALL , fetch = FetchType.EAGER)
private Set<ManufacturerDescription> descriptions = new HashSet<>();

@Getter
@Setter
@Column(name = "MANUFACTURER_IMAGE")
private String image;

@Getter
@Setter
@Column(name="SORT_ORDER")
private Integer order = 0;

@Getter
@Setter
@ManyToOne(fetch = FetchType.EAGER)
@JoinColumn(name="MERCHANT_ID", nullable=false)
private MerchantStore merchantStore;

@Getter
@Setter
@NotEmpty
@Column(name="CODE", length=100, nullable=false)
private String code;

public Manufacturer() {
}

@Override
public Long getId() {
	return id;
}

@Override
public void setId(Long id) {
	this.id = id;
}

@Override
public AuditSection getAuditSection() {
	return auditSection;
}

@Override
public void setAuditSection(AuditSection auditSection) {
	this.auditSection = auditSection;
}


}
