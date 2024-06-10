package dev.vulcanium.business.services.catalog.product.instance;

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
@Table(name = "PRODUCT_INST_IMAGE")
public class ProductInstanceImage extends SalesManagerEntity<Long, ProductInstanceImage> {

private static final long serialVersionUID = 1L;

@Id
@Column(name = "PRODUCT_INST_IMAGE_ID")
@TableGenerator(name = "TABLE_GEN",
		table = "SM_SEQUENCER",
		pkColumnName = "SEQ_NAME",
		valueColumnName = "SEQ_COUNT",
		pkColumnValue = "PRD_INST_IMG_SEQ_NEXT_VAL")
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
@ManyToOne(targetEntity = ProductInstanceGroup.class)
@JoinColumn(name = "PRODUCT_INSTANCE_GROUP_ID", nullable = false)
private ProductInstanceGroup productInstanceGroup;

@Setter
@Getter
@OneToMany(fetch = FetchType.LAZY, mappedBy = "productInstanceImage", cascade = CascadeType.ALL)
private Set<ProductInstanceImageDescription> descriptions = new HashSet<>();

public ProductInstanceImage(){
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