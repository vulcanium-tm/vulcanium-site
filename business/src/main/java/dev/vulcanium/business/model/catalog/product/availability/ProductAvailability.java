package dev.vulcanium.business.model.catalog.product.availability;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.TableGenerator;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.vulcanium.business.constants.SchemaConstant;
import dev.vulcanium.business.model.catalog.product.Product;
import dev.vulcanium.business.model.catalog.product.ProductDimensions;
import dev.vulcanium.business.model.catalog.product.price.ProductPrice;
import dev.vulcanium.business.model.catalog.product.variant.ProductVariant;
import dev.vulcanium.business.model.common.audit.AuditSection;
import dev.vulcanium.business.model.common.audit.Auditable;
import dev.vulcanium.business.model.generic.SalesManagerEntity;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.utils.CloneUtils;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "PRODUCT_AVAILABILITY",
		uniqueConstraints= @UniqueConstraint(columnNames = {"MERCHANT_ID", "PRODUCT_ID", "PRODUCT_VARIANT", "REGION_VARIANT"}),
		indexes =
				{
						@Index(name="PRD_AVAIL_STORE_PRD_IDX", columnList = "PRODUCT_ID,MERCHANT_ID"),
						@Index(name="PRD_AVAIL_PRD_IDX", columnList = "PRODUCT_ID")
				}
)

/**
 * Default availability
 * <p>
 * store
 * product id
 * <p>
 * variant null
 * regionVariant null
 */
public class ProductAvailability extends SalesManagerEntity<Long, ProductAvailability> implements Auditable {

private static final long serialVersionUID = 1L;

@Embedded
private AuditSection auditSection = new AuditSection();

@Id
@Column(name = "PRODUCT_AVAIL_ID", unique = true, nullable = false)
@TableGenerator(name = "TABLE_GEN", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "PRODUCT_AVAIL_SEQ_NEXT_VAL")
@GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
private Long id;

@Setter
@Getter
@JsonIgnore
@ManyToOne(targetEntity = Product.class)
@JoinColumn(name = "PRODUCT_ID", nullable = false)
private Product product;

/** Specific retailer store **/
@Setter
@Getter
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "MERCHANT_ID", nullable = true)
private MerchantStore merchantStore;

/**
 * This describes the availability of a product variant
 */
@Setter
@Getter
@ManyToOne(targetEntity = ProductVariant.class)
@JoinColumn(name = "PRODUCT_VARIANT", nullable = true)
private ProductVariant productVariant;

@Setter
@Getter
@Pattern(regexp="^[a-zA-Z0-9_]*$")
@Column(name = "SKU", nullable = true)
private String sku;

@Setter
@Getter
@Embedded
private ProductDimensions dimensions;

@Setter
@Getter
@NotNull
@Column(name = "QUANTITY")
private Integer productQuantity = 0;

@Temporal(TemporalType.DATE)
@Column(name = "DATE_AVAILABLE")
private Date productDateAvailable;

@Setter
@Getter
@Column(name = "REGION")
private String region = SchemaConstant.ALL_REGIONS;

@Setter
@Getter
@Column(name = "REGION_VARIANT")
private String regionVariant;

@Setter
@Getter
@Column(name = "OWNER")
private String owner;

@Setter
@Column(name = "STATUS")
private boolean productStatus = true; //can be used as flag for variant can be purchase or not

@Setter
@Column(name = "FREE_SHIPPING")
private boolean productIsAlwaysFreeShipping;

@Setter
@Getter
@Column(name = "AVAILABLE")
private Boolean available;

@Setter
@Getter
@Column(name = "QUANTITY_ORD_MIN")
private Integer productQuantityOrderMin = 0;

@Setter
@Getter
@Column(name = "QUANTITY_ORD_MAX")
private Integer productQuantityOrderMax = 0;

@Setter
@Getter
@OneToMany(fetch = FetchType.LAZY, mappedBy = "productAvailability", cascade = CascadeType.ALL)
private Set<ProductPrice> prices = new HashSet<>();


@Transient
public ProductPrice defaultPrice() {
	for (ProductPrice price : prices) {
		if (price.isDefaultPrice()) {
			return price;
		}
	}
	return new ProductPrice();
}

public ProductAvailability() {
}

public ProductAvailability(Product product, MerchantStore store) {
	this.product = product;
	this.merchantStore = store;
}

public Date getProductDateAvailable() {
	return CloneUtils.clone(productDateAvailable);
}

public void setProductDateAvailable(Date productDateAvailable) {
	this.productDateAvailable = CloneUtils.clone(productDateAvailable);
}

public boolean getProductStatus() {
	return productStatus;
}

public boolean getProductIsAlwaysFreeShipping() {
	return productIsAlwaysFreeShipping;
}

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
public void setAuditSection(AuditSection audit) {
	this.auditSection = audit;
	
}


}
