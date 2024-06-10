package dev.vulcanium.business.model.catalog.category;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.TableGenerator;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import dev.vulcanium.business.model.common.audit.AuditSection;
import dev.vulcanium.business.model.common.audit.Auditable;
import dev.vulcanium.business.model.generic.SalesManagerEntity;
import dev.vulcanium.business.model.merchant.MerchantStore;
import lombok.Getter;
import lombok.Setter;

@Entity
@EntityListeners(value = dev.vulcanium.business.model.common.audit.AuditListener.class)
@Table(name = "CATEGORY",
		indexes = @Index(columnList = "LINEAGE"),
		uniqueConstraints=
		@UniqueConstraint(columnNames = {"MERCHANT_ID", "CODE"}) )


public class Category extends SalesManagerEntity<Long, Category> implements Auditable {
private static final long serialVersionUID = 1L;

@Id
@Column(name = "CATEGORY_ID", unique=true, nullable=false)
@TableGenerator(name = "TABLE_GEN", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "CATEGORY_SEQ_NEXT_VAL")
@GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
private Long id;

@Embedded
private AuditSection auditSection = new AuditSection();

@Setter
@Getter
@Valid
@OneToMany(mappedBy="category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
private Set<CategoryDescription> descriptions = new HashSet<>();

@Setter
@Getter
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name="MERCHANT_ID", nullable=false)
private MerchantStore merchantStore;

@Setter
@Getter
@ManyToOne
@JoinColumn(name = "PARENT_ID")
private Category parent;

@Setter
@Getter
@OneToMany(mappedBy = "parent", cascade = CascadeType.REMOVE)
private List<Category> categories = new ArrayList<>();

@Setter
@Getter
@Column(name = "CATEGORY_IMAGE", length=100)
private String categoryImage;

@Setter
@Getter
@Column(name = "SORT_ORDER")
private Integer sortOrder = 0;

@Setter
@Getter
@Column(name = "CATEGORY_STATUS")
private boolean categoryStatus;

@Setter
@Getter
@Column(name = "VISIBLE")
private boolean visible;

@Setter
@Getter
@Column(name = "DEPTH")
private Integer depth;

@Setter
@Getter
@Column(name = "LINEAGE")
private String lineage;

@Setter
@Getter
@Column(name="FEATURED")
private boolean featured;

@Setter
@Getter
@NotEmpty
@Column(name="CODE", length=100, nullable=false)
private String code;

public Category() {
}

public Category(MerchantStore store) {
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

public CategoryDescription getDescription() {
	if(descriptions!=null && !descriptions.isEmpty()) {
		return descriptions.iterator().next();
	}
	
	return null;
}

}