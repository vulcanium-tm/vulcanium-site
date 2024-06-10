package dev.vulcanium.business.model.catalog.product;

import dev.vulcanium.business.services.catalog.product.instance.ProductInstance;
import java.math.BigDecimal;
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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.TableGenerator;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import dev.vulcanium.business.model.catalog.category.Category;
import dev.vulcanium.business.model.catalog.product.attribute.ProductAttribute;
import dev.vulcanium.business.model.catalog.product.availability.ProductAvailability;
import dev.vulcanium.business.model.catalog.product.description.ProductDescription;
import dev.vulcanium.business.model.catalog.product.image.ProductImage;
import dev.vulcanium.business.model.catalog.product.manufacturer.Manufacturer;
import dev.vulcanium.business.model.catalog.product.relationship.ProductRelationship;
import dev.vulcanium.business.model.catalog.product.type.ProductType;
import dev.vulcanium.business.model.catalog.product.variant.ProductVariant;
import dev.vulcanium.business.model.common.audit.AuditListener;
import dev.vulcanium.business.model.common.audit.AuditSection;
import dev.vulcanium.business.model.common.audit.Auditable;
import dev.vulcanium.business.model.customer.Customer;
import dev.vulcanium.business.model.generic.SalesManagerEntity;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.tax.taxclass.TaxClass;


@SuppressWarnings("ALL")
@Entity
@EntityListeners(value = AuditListener.class)
@Table(name = "PRODUCT", uniqueConstraints=
@UniqueConstraint(columnNames = {"MERCHANT_ID", "SKU"}))
public class Product extends SalesManagerEntity<Long, Product> implements Auditable {
private static final long serialVersionUID = 1L;

@Id
@Column(name = "PRODUCT_ID", unique=true, nullable=false)
@TableGenerator(
		name = "TABLE_GEN",
		table = "SM_SEQUENCER",
		pkColumnName = "SEQ_NAME",
		valueColumnName = "SEQ_COUNT",
		pkColumnValue = "PRODUCT_SEQ_NEXT_VAL")
@GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
private Long id;

@Embedded
private AuditSection auditSection = new AuditSection();

@Setter
@Getter
@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "product")
private Set<ProductDescription> descriptions = new HashSet<>();

/**
 * Inventory
 */
@Setter
@Getter
@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy="product")
private Set<ProductAvailability> availabilities = new HashSet<>();

/**
 * Attributes of a product
 * Decorates the product with additional properties
 */
@Setter
@Getter
@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "product")
private Set<ProductAttribute> attributes = new HashSet<>();

/**
 * Default product images
 */
@Setter
@Getter
@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, mappedBy = "product")//cascade is set to remove because product save requires logic to create physical image first and then save the image id in the database, cannot be done in cascade
private Set<ProductImage> images = new HashSet<>();

/**
 * Related items / product groups
 */
@Setter
@Getter
@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "product")
private Set<ProductRelationship> relationships = new HashSet<>();

@Setter
@Getter
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name="MERCHANT_ID", nullable=false)
private MerchantStore merchantStore;

/**
 * Product to category
 */
@Setter
@Getter
@ManyToMany(fetch=FetchType.LAZY, cascade = {CascadeType.REFRESH})
@JoinTable(name = "PRODUCT_CATEGORY", joinColumns = {
		@JoinColumn(name = "PRODUCT_ID", nullable = false, updatable = false) }
		,
		inverseJoinColumns = { @JoinColumn(name = "CATEGORY_ID",
				nullable = false, updatable = false) }
)
@Cascade({
		org.hibernate.annotations.CascadeType.DETACH,
		org.hibernate.annotations.CascadeType.LOCK,
		org.hibernate.annotations.CascadeType.REFRESH,
		org.hibernate.annotations.CascadeType.REPLICATE
	
})
private Set<Category> categories = new HashSet<>();

/**
 * Product variants
 * Decorates the product with variants
 *
 */
@Setter
@Getter
@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "product")
private Set<ProductVariant> variants = new HashSet<>();

@Setter
@Getter
@Column(name="DATE_AVAILABLE")
@Temporal(TemporalType.TIMESTAMP)
private Date dateAvailable = new Date();


@Setter
@Getter
@Column(name = "AVAILABLE")
private boolean available = true;


@Setter
@Getter
@Column(name = "PREORDER")
private boolean preOrder = false;


@Setter
@Getter
@ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH})
@JoinColumn(name="MANUFACTURER_ID", nullable=true)
private Manufacturer manufacturer;

@Setter
@Getter
@ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH})
@JoinColumn(name="PRODUCT_TYPE_ID", nullable=true)
private ProductType type;

@Setter
@Getter
@ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH})
@JoinColumn(name="TAX_CLASS_ID", nullable=true)
private TaxClass taxClass;

@Setter
@Getter
@Column(name = "PRODUCT_VIRTUAL")
private boolean productVirtual = false;

@Setter
@Getter
@Column(name = "PRODUCT_SHIP")
private boolean productShipeable = false;

@Setter
@Column(name = "PRODUCT_FREE")
private boolean productIsFree;

@Setter
@Getter
@Column(name = "PRODUCT_LENGTH")
private BigDecimal productLength;

@Setter
@Getter
@Column(name = "PRODUCT_WIDTH")
private BigDecimal productWidth;

@Setter
@Getter
@Column(name = "PRODUCT_HEIGHT")
private BigDecimal productHeight;

@Setter
@Getter
@Column(name = "PRODUCT_WEIGHT")
private BigDecimal productWeight;

@Setter
@Getter
@Column(name = "REVIEW_AVG")
private BigDecimal productReviewAvg;

@Setter
@Getter
@Column(name = "REVIEW_COUNT")
private Integer productReviewCount;

@Setter
@Getter
@Column(name = "QUANTITY_ORDERED")
private Integer productOrdered;

@Setter
@Getter
@Column(name = "SORT_ORDER")
private Integer sortOrder = new Integer(0);

@Setter
@Getter
@NotEmpty
@Pattern(regexp="^[a-zA-Z0-9_]*$")
@Column(name = "SKU")
private String sku;

/**
 * External system reference SKU/ID
 */
@Setter
@Getter
@Column(name = "REF_SKU")
private String refSku;

@Setter
@Getter
@Column(name="COND", nullable = true)
private ProductCondition condition;

/**
 * Product variants
 * Decorates the product with variants
 *
 */
@Getter
@Setter
@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "product")
private Set<ProductInstance> instances = new HashSet<ProductInstance>();

/**
 * RENTAL ADDITIONAL FIELDS
 */

@Setter
@Getter
@Column(name="RENTAL_STATUS", nullable = true)
private RentalStatus rentalStatus;


@Setter
@Getter
@Column(name="RENTAL_DURATION", nullable = true)
private Integer rentalDuration;

@Setter
@Getter
@Column(name="RENTAL_PERIOD", nullable = true)
private Integer rentalPeriod;

/**
 * End rental fields
 */

@Setter
@Getter
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name="CUSTOMER_ID", nullable=true)
private Customer owner;

public Product() {
}

@Override
public Long getId() {
	return this.id;
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

public boolean getProductVirtual() {
	return productVirtual;
}

public boolean getProductIsFree() {
	return productIsFree;
}

public void setAvailable(Boolean available) {
	this.available = available;
}

public void setProductShipeable(Boolean productShipeable) {
	this.productShipeable = productShipeable;
}


public ProductDescription getProductDescription() {
	if(this.getDescriptions()!=null && !this.getDescriptions().isEmpty()) {
		return this.getDescriptions().iterator().next();
	}
	return null;
}

public ProductImage getProductImage() {
	ProductImage productImage = null;
	if(this.getImages()!=null && !this.getImages().isEmpty()) {
		for(ProductImage image : this.getImages()) {
			productImage = image;
			if(productImage.isDefaultImage()) {
				break;
			}
		}
	}
	return productImage;
}


}
