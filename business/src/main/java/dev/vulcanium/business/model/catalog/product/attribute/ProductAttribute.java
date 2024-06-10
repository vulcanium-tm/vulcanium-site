package dev.vulcanium.business.model.catalog.product.attribute;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.TableGenerator;
import jakarta.persistence.Transient;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.Index;

import dev.vulcanium.business.model.catalog.product.Product;
import dev.vulcanium.business.model.generic.SalesManagerEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="PRODUCT_ATTRIBUTE",
		indexes = @Index(columnList = "PRODUCT_ID"),
		uniqueConstraints={
				@UniqueConstraint(columnNames={
						"OPTION_ID",
						"OPTION_VALUE_ID",
						"PRODUCT_ID"
				})
		}
)

/**
 * Attribute Size - Small and product
 */

public class ProductAttribute extends SalesManagerEntity<Long, ProductAttribute> implements Optionable {
private static final long serialVersionUID = 1L;

@Id
@Column(name = "PRODUCT_ATTRIBUTE_ID", unique=true, nullable=false)
@TableGenerator(name = "TABLE_GEN", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "PRODUCT_ATTR_SEQ_NEXT_VAL")
@GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
private Long id;


@Setter
@Getter
@Column(name="PRODUCT_ATRIBUTE_PRICE")
private BigDecimal productAttributePrice;


@Setter
@Getter
@Column(name="PRODUCT_ATTRIBUTE_SORT_ORD")
private Integer productOptionSortOrder;

@Setter
@Column(name="PRODUCT_ATTRIBUTE_FREE")
private boolean productAttributeIsFree;


@Setter
@Getter
@Column(name="PRODUCT_ATTRIBUTE_WEIGHT")
private BigDecimal productAttributeWeight;

@Setter
@Column(name="PRODUCT_ATTRIBUTE_DEFAULT")
private boolean attributeDefault=false;

@Setter
@Column(name="PRODUCT_ATTRIBUTE_REQUIRED")
private boolean attributeRequired=false;

/**
 * a read only attribute is considered as a core attribute addition
 */
@Setter
@Column(name="PRODUCT_ATTRIBUTE_FOR_DISP")
private boolean attributeDisplayOnly=false;


@Setter
@Column(name="PRODUCT_ATTRIBUTE_DISCOUNTED")
private boolean attributeDiscounted=false;


@Setter
@Getter
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name="OPTION_ID", nullable=false)
private ProductOption productOption;


@Setter
@Getter
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name="OPTION_VALUE_ID", nullable=false)
private ProductOptionValue productOptionValue;


/**
 * This transient object property
 * is a utility used only to submit from a free text
 */
@Setter
@Getter
@Transient
private String attributePrice = "0";


/**
 * This transient object property
 * is a utility used only to submit from a free text
 */
@Setter
@Getter
@Transient
private String attributeSortOrder = "0";



/**
 * This transient object property
 * is a utility used only to submit from a free text
 */
@Setter
@Getter
@Transient
private String attributeAdditionalWeight = "0";

@Setter
@Getter
@ManyToOne(targetEntity = Product.class)
@JoinColumn(name = "PRODUCT_ID", nullable = false)
private Product product;

public ProductAttribute() {
}

@Override
public Long getId() {
	return id;
}

@Override
public void setId(Long id) {
	this.id = id;
}

public boolean getProductAttributeIsFree() {
	return productAttributeIsFree;
}

public boolean getAttributeDefault() {
	return attributeDefault;
}

public boolean getAttributeRequired() {
	return attributeRequired;
}

public boolean getAttributeDisplayOnly() {
	return attributeDisplayOnly;
}

public boolean getAttributeDiscounted() {
	return attributeDiscounted;
}


}
