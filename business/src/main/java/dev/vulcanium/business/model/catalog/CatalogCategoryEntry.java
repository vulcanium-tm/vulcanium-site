package dev.vulcanium.business.model.catalog;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.TableGenerator;
import jakarta.persistence.UniqueConstraint;

import dev.vulcanium.business.constants.SchemaConstant;
import dev.vulcanium.business.model.catalog.category.Category;
import dev.vulcanium.business.model.common.audit.AuditSection;
import dev.vulcanium.business.model.common.audit.Auditable;
import dev.vulcanium.business.model.generic.SalesManagerEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@EntityListeners(value = dev.vulcanium.business.model.common.audit.AuditListener.class)
@Table(name = "CATALOG_ENTRY",uniqueConstraints=
@UniqueConstraint(columnNames = {"CATEGORY_ID", "CATALOG_ID"}) )
public class CatalogCategoryEntry extends SalesManagerEntity<Long, CatalogCategoryEntry> implements Auditable {


@Embedded
private AuditSection auditSection = new AuditSection();

private static final long serialVersionUID = 1L;

@Getter
@Id
@GeneratedValue(strategy = GenerationType.TABLE,
		generator = "TABLE_GEN")

@TableGenerator(name = "TABLE_GEN",
		table = "SM_SEQUENCER",
		pkColumnName = "SEQ_NAME",
		valueColumnName = "SEQ_COUNT",
		allocationSize = SchemaConstant.DESCRIPTION_ID_ALLOCATION_SIZE,
		initialValue = SchemaConstant.DESCRIPTION_ID_START_VALUE,
		pkColumnValue = "CATALOG_ENT_SEQ_NEXT_VAL")
private Long id;

@Setter
@Getter
@ManyToOne
@JoinColumn(name = "CATEGORY_ID", nullable = false)
Category category;

@Setter
@Getter
@ManyToOne
@JoinColumn(name = "CATALOG_ID", nullable = false)
private Catalog catalog;

@Setter
@Getter
@Column(name = "VISIBLE")
private boolean visible;

@Override
public void setId(Long id) {
	this.id = id;
	
}

@Override
public AuditSection getAuditSection() {
	return auditSection;
}

@Override
public void setAuditSection(AuditSection audit) {
	auditSection = audit;
	
}

}
