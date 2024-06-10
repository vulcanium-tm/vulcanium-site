package dev.vulcanium.business.model.catalog.product.relationship;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.TableGenerator;

import dev.vulcanium.business.model.catalog.product.Product;
import dev.vulcanium.business.model.generic.SalesManagerEntity;
import dev.vulcanium.business.model.merchant.MerchantStore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "PRODUCT_RELATIONSHIP")
public class ProductRelationship extends SalesManagerEntity<Long, ProductRelationship> implements Serializable {
private static final long serialVersionUID = 1L;

@Id
@Column(name = "PRODUCT_RELATIONSHIP_ID", unique=true, nullable=false)
@TableGenerator(name = "TABLE_GEN", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "PRODUCT_RELATION_SEQ_NEXT_VAL")
@GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
private Long id;

@ManyToOne(targetEntity = MerchantStore.class)
@JoinColumn(name="MERCHANT_ID",nullable=false)
private MerchantStore store;

@ManyToOne(targetEntity = Product.class)
@JoinColumn(name="PRODUCT_ID",updatable=false,nullable=true)
private Product product = null;

@ManyToOne(targetEntity = Product.class)
@JoinColumn(name="RELATED_PRODUCT_ID",updatable=false,nullable=true)
private Product relatedProduct = null;

@Column(name="CODE")
private String code;

@Column(name="ACTIVE")
private boolean active = true;

public ProductRelationship() {
}


}
