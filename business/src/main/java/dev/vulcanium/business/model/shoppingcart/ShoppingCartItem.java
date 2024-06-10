package dev.vulcanium.business.model.shoppingcart;

import dev.vulcanium.business.model.catalog.product.Product;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collections;
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
import jakarta.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.vulcanium.business.model.catalog.product.price.FinalPrice;
import dev.vulcanium.business.model.common.audit.AuditListener;
import dev.vulcanium.business.model.common.audit.AuditSection;
import dev.vulcanium.business.model.common.audit.Auditable;
import dev.vulcanium.business.model.generic.SalesManagerEntity;


@Entity
@EntityListeners(value = AuditListener.class)
@Table(name = "SHOPPING_CART_ITEM")
public class ShoppingCartItem extends SalesManagerEntity<Long, ShoppingCartItem> implements Auditable, Serializable {


private static final long serialVersionUID = 1L;

@Id
@Column(name = "SHP_CART_ITEM_ID", unique=true, nullable=false)
@TableGenerator(name = "TABLE_GEN", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "SHP_CRT_ITM_SEQ_NEXT_VAL")
@GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
private Long id;

@JsonIgnore
@ManyToOne(targetEntity = ShoppingCart.class)
@JoinColumn(name = "SHP_CART_ID", nullable = false)
private ShoppingCart shoppingCart;

@Column(name="QUANTITY")
private Integer quantity = 1;

@Embedded
private AuditSection auditSection = new AuditSection();

@Deprecated
@Column(name="PRODUCT_ID", nullable=false)
private Long productId;

@Column(name="SKU", nullable=true)
private String sku;

@JsonIgnore
@Transient
private boolean productVirtual;

@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "shoppingCartItem")
private Set<ShoppingCartAttributeItem> attributes = new HashSet<ShoppingCartAttributeItem>();

@Column(name="PRODUCT_VARIANT", nullable=true)
private Long variant;

@JsonIgnore
@Transient
private BigDecimal itemPrice;

@JsonIgnore
@Transient
private BigDecimal subTotal;

@JsonIgnore
@Transient
private FinalPrice finalPrice;

@JsonIgnore
@Transient
private Product product;

@JsonIgnore
@Transient
private boolean obsolete = false;




public ShoppingCartItem(ShoppingCart shoppingCart, Product product) {
	this(product);
	this.shoppingCart = shoppingCart;
}

public ShoppingCartItem(Product product) {
	this.product = product;
	this.productId = product.getId();
	this.setSku(product.getSku());
	this.quantity = 1;
	this.productVirtual = product.getProductVirtual();
}

/** remove usage to limit possibility to implement bugs, would use constructors above to make sure all needed attributes are set correctly **/
@Deprecated
public ShoppingCartItem() {
}

@Override
public AuditSection getAuditSection() {
	return auditSection;
}

@Override
public void setAuditSection(AuditSection audit) {
	this.auditSection = audit;
	
}

@Override
public Long getId() {
	return id;
}

@Override
public void setId(Long id) {
	this.id = id;
	
}

public void setAttributes(Set<ShoppingCartAttributeItem> attributes) {
	this.attributes = attributes;
}

public Set<ShoppingCartAttributeItem> getAttributes() {
	return attributes;
}

public void setItemPrice(BigDecimal itemPrice) {
	this.itemPrice = itemPrice;
}

public BigDecimal getItemPrice() {
	return itemPrice;
}

public void setQuantity(Integer quantity) {
	this.quantity = quantity;
}

public Integer getQuantity() {
	return quantity;
}

public ShoppingCart getShoppingCart() {
	return shoppingCart;
}

public void setShoppingCart(ShoppingCart shoppingCart) {
	this.shoppingCart = shoppingCart;
}

public void setProductId(Long productId) {
	this.productId = productId;
}

public Long getProductId() {
	return productId;
}

public void setProduct(Product product) {
	this.product = product;
}

public Product getProduct() {
	return product;
}

public void addAttributes(ShoppingCartAttributeItem shoppingCartAttributeItem)
{
	this.attributes.add(shoppingCartAttributeItem);
}

public void removeAttributes(ShoppingCartAttributeItem shoppingCartAttributeItem)
{
	this.attributes.remove(shoppingCartAttributeItem);
}

public void removeAllAttributes(){
	this.attributes.removeAll(Collections.EMPTY_SET);
}

public void setSubTotal(BigDecimal subTotal) {
	this.subTotal = subTotal;
}

public BigDecimal getSubTotal() {
	return subTotal;
}

public void setFinalPrice(FinalPrice finalPrice) {
	this.finalPrice = finalPrice;
}

public FinalPrice getFinalPrice() {
	return finalPrice;
}

public boolean isObsolete() {
	return obsolete;
}

public void setObsolete(boolean obsolete) {
	this.obsolete = obsolete;
}


public boolean isProductVirtual() {
	return productVirtual;
}

public void setProductVirtual(boolean productVirtual) {
	this.productVirtual = productVirtual;
}

public String getSku() {
	return sku;
}

public void setSku(String sku) {
	this.sku = sku;
}

public Long getVariant() {
	return variant;
}

public void setVariant(Long variant) {
	this.variant = variant;
}

}
