package dev.vulcanium.business.model.catalog;

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
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import dev.vulcanium.business.constants.SchemaConstant;
import dev.vulcanium.business.model.common.audit.AuditSection;
import dev.vulcanium.business.model.common.audit.Auditable;
import dev.vulcanium.business.model.generic.SalesManagerEntity;
import dev.vulcanium.business.model.merchant.MerchantStore;
import lombok.Getter;
import lombok.Setter;

/**
 * Allows grouping products and category
 * Catalog
 *      - category 1
 *      - category 2
 * <p>
 *      - product 1
 *      - product 2
 *      - product 3
 *      - product 4
 */


@Entity
@EntityListeners(value = dev.vulcanium.business.model.common.audit.AuditListener.class)
@Table(name = "CATALOG",
		uniqueConstraints=@UniqueConstraint(columnNames = {"MERCHANT_ID", "CODE"}))
public class Catalog extends SalesManagerEntity<Long, Catalog> implements Auditable {
private static final long serialVersionUID = 1L;

@Id
@GeneratedValue(strategy = GenerationType.TABLE,
		generator = "TABLE_GEN")
@TableGenerator(name = "TABLE_GEN",
		table = "SM_SEQUENCER",
		pkColumnName = "SEQ_NAME",
		valueColumnName = "SEQ_COUNT",
		pkColumnValue = "CATALOG_SEQ_NEXT_VAL",
		allocationSize = SchemaConstant.DESCRIPTION_ID_ALLOCATION_SIZE,
		initialValue = SchemaConstant.DESCRIPTION_ID_START_VALUE
)
private Long id;

@Embedded
private AuditSection auditSection = new AuditSection();

@Setter
@Getter
@Valid
@OneToMany(mappedBy="catalog", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
private Set<CatalogCategoryEntry> entry = new HashSet<CatalogCategoryEntry>();

@Setter
@Getter
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name="MERCHANT_ID", nullable=false)
private MerchantStore merchantStore;


@Setter
@Getter
@Column(name = "VISIBLE")
private boolean visible;

@Setter
@Getter
@Column(name="DEFAULT_CATALOG")
private boolean defaultCatalog;

@Setter
@Getter
@NotEmpty
@Column(name="CODE", length=100, nullable=false)
private String code;

@Setter
@Getter
@Column(name = "SORT_ORDER")
private Integer sortOrder = 0;

public Catalog() {
}

public Catalog(MerchantStore store) {
	this.merchantStore = store;
	this.id = 0L;
}

@Override
public Long getId() {
	return this.id;
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