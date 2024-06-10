package dev.vulcanium.business.model.catalog.product.type;

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

import dev.vulcanium.business.model.common.audit.AuditListener;
import dev.vulcanium.business.model.common.audit.AuditSection;
import dev.vulcanium.business.model.common.audit.Auditable;
import dev.vulcanium.business.model.generic.SalesManagerEntity;
import dev.vulcanium.business.model.merchant.MerchantStore;
import lombok.Getter;
import lombok.Setter;

@Entity
@EntityListeners(value = AuditListener.class)
@Table(name = "PRODUCT_TYPE")
public class ProductType extends SalesManagerEntity<Long, ProductType> implements Auditable {
private static final long serialVersionUID = 1L;

public final static String GENERAL_TYPE = "GENERAL";

@Id
@Column(name = "PRODUCT_TYPE_ID", unique = true, nullable = false)
@TableGenerator(name = "TABLE_GEN", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME",
		valueColumnName = "SEQ_COUNT", pkColumnValue = "PRD_TYPE_SEQ_NEXT_VAL")
@GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
private Long id;

@Embedded
private AuditSection auditSection = new AuditSection();

@Setter
@Getter
@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "productType")
private Set<ProductTypeDescription> descriptions = new HashSet<>();

@Setter
@Getter
@Column(name = "PRD_TYPE_CODE")
private String code;

@Setter
@Getter
@Column(name = "PRD_TYPE_ADD_TO_CART")
private Boolean allowAddToCart;

@Setter
@Getter
@Column(name = "PRD_TYPE_VISIBLE")
private Boolean visible;

@Setter
@Getter
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "MERCHANT_ID", nullable = true)
private MerchantStore merchantStore;

public ProductType() {}

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

public boolean isAllowAddToCart() {
	return allowAddToCart;
}

public void setAllowAddToCart(boolean allowAddToCart) {
	this.allowAddToCart = allowAddToCart;
}


}
