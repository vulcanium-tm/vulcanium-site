package dev.vulcanium.business.model.catalog.product.image;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

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
import jakarta.persistence.Transient;

import dev.vulcanium.business.model.catalog.product.Product;
import dev.vulcanium.business.model.generic.SalesManagerEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "PRODUCT_IMAGE")
public class ProductImage extends SalesManagerEntity<Long, ProductImage> {
private static final long serialVersionUID = 1L;

@Id
@Column(name = "PRODUCT_IMAGE_ID")
@TableGenerator(name = "TABLE_GEN", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "PRODUCT_IMG_SEQ_NEXT_VAL")
@GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
private Long id;

@Setter
@Getter
@OneToMany(fetch = FetchType.LAZY, mappedBy = "productImage", cascade = CascadeType.ALL)
private List<ProductImageDescription> descriptions = new ArrayList<>();


@Setter
@Getter
@Column(name = "PRODUCT_IMAGE")
private String productImage;

@Setter
@Getter
@Column(name = "DEFAULT_IMAGE")
private boolean defaultImage = true;

/**
 * default to 0 for images managed by the system
 */
@Setter
@Getter
@Column(name = "IMAGE_TYPE")
private int imageType;

/**
 * Refers to images not accessible through the system. It may also be a video.
 */
@Setter
@Getter
@Column(name = "PRODUCT_IMAGE_URL")
private String productImageUrl;


@Setter
@Getter
@Column(name = "IMAGE_CROP")
private boolean imageCrop;

@Setter
@Getter
@ManyToOne(targetEntity = Product.class)
@JoinColumn(name = "PRODUCT_ID", nullable = false)
private Product product;

@Setter
@Getter
@Column(name = "SORT_ORDER")
private Integer sortOrder = 0;


@Setter
@Getter
@Transient
private InputStream image = null;

//private MultiPartFile image

public ProductImage(){
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