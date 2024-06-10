package dev.vulcanium.business.model.catalog.product.variant;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.TableGenerator;

import dev.vulcanium.business.model.generic.SalesManagerEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "PRODUCT_VAR_IMAGE")
public class ProductVariantImage extends SalesManagerEntity<Long, ProductVariantImage> {


private static final long serialVersionUID = 1L;

@Id
@Column(name = "PRODUCT_VAR_IMAGE_ID")
@TableGenerator(name = "TABLE_GEN",
		table = "SM_SEQUENCER",
		pkColumnName = "SEQ_NAME",
		valueColumnName = "SEQ_COUNT",
		pkColumnValue = "PRD_VAR_IMG_SEQ_NEXT_VAL")
@GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
private Long id;

@Setter
@Getter
@Column(name = "PRODUCT_IMAGE")
private String productImage;

@Setter
@Getter
@Column(name = "DEFAULT_IMAGE")
private boolean defaultImage = true;

@Setter
@Getter
@ManyToOne(targetEntity = ProductVariantGroup.class)
@JoinColumn(name = "PRODUCT_VARIANT_GROUP_ID", nullable = false)
private ProductVariantGroup productVariantGroup;

@Setter
@Getter
@OneToMany(fetch = FetchType.LAZY, mappedBy = "productVariantImage", cascade = CascadeType.ALL)
private Set<ProductVariantImageDescription> descriptions = new HashSet<>();

public ProductVariantImage(){
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
