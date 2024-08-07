package dev.vulcanium.business.model.catalog.product.variant;

import java.util.Date;
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
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.TableGenerator;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

import dev.vulcanium.business.model.catalog.product.Product;
import dev.vulcanium.business.model.catalog.product.availability.ProductAvailability;
import dev.vulcanium.business.model.catalog.product.variation.ProductVariation;
import dev.vulcanium.business.model.common.audit.AuditListener;
import dev.vulcanium.business.model.common.audit.AuditSection;
import dev.vulcanium.business.model.common.audit.Auditable;
import dev.vulcanium.business.model.generic.SalesManagerEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@EntityListeners(value = AuditListener.class)
@Table(name = "PRODUCT_VARIANT",
		indexes = @Index(columnList = "PRODUCT_ID"),
		uniqueConstraints =
		@UniqueConstraint(columnNames = {
				"PRODUCT_ID",
				"SKU" }))
public class ProductVariant extends SalesManagerEntity<Long, ProductVariant> implements Auditable {
private static final long serialVersionUID = 1L;

@Id
@Column(name = "PRODUCT_VARIANT_ID", unique = true, nullable = false)
@TableGenerator(name = "TABLE_GEN",
		table = "SM_SEQUENCER",
		pkColumnName = "SEQ_NAME",
		valueColumnName = "SEQ_COUNT",
		pkColumnValue = "PRODUCT_VAR_SEQ_NEXT_VAL")
@GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
private Long id;


@Embedded
private AuditSection auditSection = new AuditSection();

@Setter
@Getter
@Column(name = "DATE_AVAILABLE")
@Temporal(TemporalType.TIMESTAMP)
private Date dateAvailable = new Date();

@Setter
@Getter
@Column(name = "AVAILABLE")
private boolean available = true;

@Setter
@Getter
@Column(name = "DEFAULT_SELECTION")
private boolean defaultSelection = true;

@Setter
@Getter
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "PRODUCT_VARIATION_ID", nullable = true)
private ProductVariation variation;

@Setter
@Getter
@ManyToOne(targetEntity = Product.class)
@JoinColumn(name = "PRODUCT_ID", nullable = false)
private Product product;

@Setter
@Getter
@Column(name = "CODE", nullable = true)
private String code;

@Setter
@Getter
@Column(name="SORT_ORDER")
private Integer sortOrder = 0;

@Setter
@Getter
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "PRODUCT_VARIATION_VALUE_ID", nullable = true)
private ProductVariation variationValue;

@Setter
@Getter
@NotEmpty
@Pattern(regexp="^[a-zA-Z0-9_]*$")
@Column(name = "SKU")
private String sku;

@Setter
@Getter
@ManyToOne(targetEntity = ProductVariantGroup.class)
@JoinColumn(name = "PRODUCT_VARIANT_GROUP_ID", nullable = true)
private ProductVariantGroup productVariantGroup;

@Setter
@Getter
@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy="productVariant")
private Set<ProductAvailability> availabilities = new HashSet<ProductAvailability>();


@Override
public AuditSection getAuditSection() {
	return auditSection;
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


}
