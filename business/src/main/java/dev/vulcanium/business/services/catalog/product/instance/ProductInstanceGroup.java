package dev.vulcanium.business.services.catalog.product.instance;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import lombok.Getter;
import lombok.Setter;

/**
 * Extra properties on a group of instances
 */
@Entity
@EntityListeners(value = AuditListener.class)
@Table(name="PRODUCT_INSTANCE_GROUP")
public class ProductInstanceGroup extends SalesManagerEntity<Long, ProductInstanceGroup> {

private static final long serialVersionUID = 1L;


@Id
@Column(name = "PRODUCT_INSTANCE_GROUP_ID", unique=true, nullable=false)
@TableGenerator(name = "TABLE_GEN",
		table = "SM_SEQUENCER",
		pkColumnName = "SEQ_NAME",
		valueColumnName = "SEQ_COUNT", pkColumnValue = "PRODUCT_INST_GROUP_SEQ_NEXT_VAL")
@GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
private Long id;


@Setter
@Getter
@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy="productInstanceGroup")
private List<ProductInstanceImage> images = new ArrayList<>();

@Setter
@Getter
@OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH }, mappedBy = "productInstanceGroup")
private Set<ProductInstance> productInstances = new HashSet<>();

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