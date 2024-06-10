package dev.vulcanium.business.model.shipping;

import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.TableGenerator;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import dev.vulcanium.business.constants.SchemaConstant;
import dev.vulcanium.business.model.common.Delivery;
import dev.vulcanium.business.model.generic.SalesManagerEntity;


/**
 * Shipping quote received from external shipping quote module or calculated internally
 */

@Entity
@Table (name="SHIPPING_QUOTE" )
public class Quote extends SalesManagerEntity<Long, Quote> {

private static final long serialVersionUID = 1L;
@Id
@Column(name = "SHIPPING_QUOTE_ID", unique=true, nullable=false)
@TableGenerator(name = "TABLE_GEN", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "SHIP_QUOTE_ID_NEXT_VALUE")
@GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
Long id;

@Column(name = "ORDER_ID", nullable = true)
private Long orderId;

@Column(name = "CUSTOMER_ID", nullable = true)
private Long customerId;

@Column(name = "CART_ID", nullable = true)
private Long cartId;

@Column(name = "MODULE", nullable = false)
private String module;

@Column(name = "OPTION_NAME", nullable = true)
private String optionName = null;

@Column(name = "OPTION_CODE", nullable = true)
private String optionCode = null;

@Temporal(TemporalType.TIMESTAMP)
@Column (name ="OPTION_DELIVERY_DATE")
private Date optionDeliveryDate = null;

@Temporal(TemporalType.TIMESTAMP)
@Column (name ="OPTION_SHIPPING_DATE")
private Date optionShippingDate = null;

@Temporal(TemporalType.TIMESTAMP)
@Column (name ="QUOTE_DATE")
private Date quoteDate;

@Column(name = "SHIPPING_NUMBER_DAYS")
private Integer estimatedNumberOfDays;

@Column (name ="QUOTE_PRICE")
private BigDecimal price = null;

@Column (name ="QUOTE_HANDLING")
private BigDecimal handling = null;

@Column(name = "FREE_SHIPPING")
private boolean freeShipping;

@Column (name ="IP_ADDRESS")
private String ipAddress;

@Embedded
private Delivery delivery = null;

public Long getOrderId() {
	return orderId;
}

public void setOrderId(Long orderId) {
	this.orderId = orderId;
}

public Long getCustomerId() {
	return customerId;
}

public void setCustomerId(Long customerId) {
	this.customerId = customerId;
}

public String getModule() {
	return module;
}

public void setModule(String module) {
	this.module = module;
}

public String getOptionName() {
	return optionName;
}

public void setOptionName(String optionName) {
	this.optionName = optionName;
}

public String getOptionCode() {
	return optionCode;
}

public void setOptionCode(String optionCode) {
	this.optionCode = optionCode;
}

public Date getOptionDeliveryDate() {
	return optionDeliveryDate;
}

public void setOptionDeliveryDate(Date optionDeliveryDate) {
	this.optionDeliveryDate = optionDeliveryDate;
}

public Date getOptionShippingDate() {
	return optionShippingDate;
}

public void setOptionShippingDate(Date optionShippingDate) {
	this.optionShippingDate = optionShippingDate;
}

public Date getQuoteDate() {
	return quoteDate;
}

public void setQuoteDate(Date quoteDate) {
	this.quoteDate = quoteDate;
}

public Integer getEstimatedNumberOfDays() {
	return estimatedNumberOfDays;
}

public void setEstimatedNumberOfDays(Integer estimatedNumberOfDays) {
	this.estimatedNumberOfDays = estimatedNumberOfDays;
}

public BigDecimal getPrice() {
	return price;
}

public void setPrice(BigDecimal price) {
	this.price = price;
}

public Delivery getDelivery() {
	return delivery;
}

public void setDelivery(Delivery delivery) {
	this.delivery = delivery;
}

public boolean isFreeShipping() {
	return freeShipping;
}

public void setFreeShipping(boolean freeShipping) {
	this.freeShipping = freeShipping;
}

@Override
public Long getId() {
	return id;
}

@Override
public void setId(Long id) {
	this.id = id;
	
}

public BigDecimal getHandling() {
	return handling;
}

public void setHandling(BigDecimal handling) {
	this.handling = handling;
}

public Long getCartId() {
	return cartId;
}

public void setCartId(Long cartId) {
	this.cartId = cartId;
}

public String getIpAddress() {
	return ipAddress;
}

public void setIpAddress(String ipAddress) {
	this.ipAddress = ipAddress;
}


}
