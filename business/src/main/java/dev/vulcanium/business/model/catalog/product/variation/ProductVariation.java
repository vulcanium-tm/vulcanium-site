package dev.vulcanium.business.model.catalog.product.variation;

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
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotEmpty;

import dev.vulcanium.business.model.catalog.product.attribute.Optionable;
import dev.vulcanium.business.model.catalog.product.attribute.ProductOption;
import dev.vulcanium.business.model.catalog.product.attribute.ProductOptionValue;
import dev.vulcanium.business.model.common.audit.AuditListener;
import dev.vulcanium.business.model.common.audit.AuditSection;
import dev.vulcanium.business.model.common.audit.Auditable;
import dev.vulcanium.business.model.generic.SalesManagerEntity;
import dev.vulcanium.business.model.merchant.MerchantStore;
import lombok.Getter;
import lombok.Setter;

/**
 * Product configuration
 * Contains possible product variations
 */
@Entity
@EntityListeners(value = AuditListener.class)
@Table(name = "PRODUCT_VARIATION", uniqueConstraints=
@UniqueConstraint(columnNames = {"MERCHANT_ID", "PRODUCT_OPTION_ID", "OPTION_VALUE_ID"}))
public class ProductVariation extends SalesManagerEntity<Long, ProductVariation> implements Optionable, Auditable {

private static final long serialVersionUID = 1L;

@Embedded
private AuditSection auditSection = new AuditSection();

@Id
@Column(name = "PRODUCT_VARIATION_ID", unique=true, nullable=false)
@TableGenerator(name = "TABLE_GEN", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "PRODUCT_VARIN_SEQ_NEXT_VAL")
@GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
private Long id;

/** can exist detached **/
@Getter
@Setter
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name="MERCHANT_ID", nullable=false)
private MerchantStore merchantStore;

@Getter
@Setter
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name="PRODUCT_OPTION_ID", nullable=false)
private ProductOption productOption;

@Getter
@Setter
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name="OPTION_VALUE_ID", nullable=false)
private ProductOptionValue productOptionValue;

@Getter
@Setter
@NotEmpty
@Column(name="CODE", length=100, nullable=false)
private String code;

@Getter
@Setter
@Column(name="SORT_ORDER")
private Integer sortOrder;

@Getter
@Setter
@Column(name="VARIANT_DEFAULT")
private boolean variantDefault=false;


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
	return id;
}

@Override
public void setId(Long id) {
	this.id = id;
	
}

}
