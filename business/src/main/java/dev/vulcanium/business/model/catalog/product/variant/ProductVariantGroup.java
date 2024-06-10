package dev.vulcanium.business.model.catalog.product.variant;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
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
import dev.vulcanium.business.model.generic.SalesManagerEntity;
import dev.vulcanium.business.model.merchant.MerchantStore;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

/**
 * Extra properties on a group of variants
 */
@Entity
@EntityListeners(value = AuditListener.class)
@Table(name="PRODUCT_VARIANT_GROUP")
public class ProductVariantGroup extends SalesManagerEntity<Long, ProductVariantGroup> {

private static final long serialVersionUID = 1L;


@Id
@Column(name = "PRODUCT_VARIANT_GROUP_ID", unique=true, nullable=false)
@TableGenerator(name = "TABLE_GEN",
		table = "SM_SEQUENCER",
		pkColumnName = "SEQ_NAME",
		valueColumnName = "SEQ_COUNT", pkColumnValue = "PRODUCT_VAR_GROUP_SEQ_NEXT_VAL")
@GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
private Long id;


@Setter
@Getter
@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy="productVariantGroup")
private List<ProductVariantImage> images = new ArrayList<>();

@Setter
@Getter
@OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH }, mappedBy = "productVariantGroup")
private Set<ProductVariant> productVariants = new HashSet<>();

@Setter
@Getter
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name="MERCHANT_ID", nullable=false)
private MerchantStore merchantStore;

@Override
public Long getId() {
	return this.id;
}

@Override
public void setId(Long id) {
	this.id=id;
	
}


}
