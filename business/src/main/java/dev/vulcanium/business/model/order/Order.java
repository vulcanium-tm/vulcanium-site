package dev.vulcanium.business.model.order;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.TableGenerator;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.Valid;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OrderBy;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.vulcanium.business.model.common.Billing;
import dev.vulcanium.business.model.common.Delivery;
import dev.vulcanium.business.model.generic.SalesManagerEntity;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.order.attributes.OrderAttribute;
import dev.vulcanium.business.model.order.orderproduct.OrderProduct;
import dev.vulcanium.business.model.order.orderstatus.OrderStatus;
import dev.vulcanium.business.model.order.orderstatus.OrderStatusHistory;
import dev.vulcanium.business.model.order.payment.CreditCard;
import dev.vulcanium.business.model.payments.PaymentType;
import dev.vulcanium.business.model.reference.currency.Currency;
import dev.vulcanium.business.utils.CloneUtils;

@Entity
@Table (name="ORDERS")
public class Order extends SalesManagerEntity<Long, Order> {

private static final long serialVersionUID = 1L;

@Id
@Column (name ="ORDER_ID" , unique=true , nullable=false )
@TableGenerator(name = "TABLE_GEN", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT",
		pkColumnValue = "ORDER_ID_SEQ_NEXT_VAL")
@GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
private Long id;

@Setter
@Getter
@Column (name ="ORDER_STATUS")
@Enumerated(value = EnumType.STRING)
private OrderStatus status;

@Temporal(TemporalType.TIMESTAMP)
@Column (name ="LAST_MODIFIED")
private Date lastModified;

@Setter
@Getter
@Column (name ="CUSTOMER_ID")
private Long customerId;

@Temporal(TemporalType.DATE)
@Column (name ="DATE_PURCHASED")
private Date datePurchased;

@Temporal(TemporalType.TIMESTAMP)
@Column (name ="ORDER_DATE_FINISHED")
private Date orderDateFinished;

@Setter
@Getter
@Column (name ="CURRENCY_VALUE")
private BigDecimal currencyValue = new BigDecimal(1);//default 1-1

@Setter
@Getter
@Column (name ="ORDER_TOTAL")
private BigDecimal total;

@Setter
@Getter
@Column (name ="IP_ADDRESS")
private String ipAddress;

@Setter
@Getter
@Column(name = "CART_CODE", nullable=true)
private String shoppingCartCode;

@Setter
@Getter
@Column (name ="CHANNEL")
@Enumerated(value = EnumType.STRING)
private OrderChannel channel;

@Setter
@Getter
@Column (name ="ORDER_TYPE")
@Enumerated(value = EnumType.STRING)
private OrderType orderType = OrderType.ORDER;

@Setter
@Getter
@Column (name ="PAYMENT_TYPE")
@Enumerated(value = EnumType.STRING)
private PaymentType paymentType;

@Setter
@Getter
@Column (name ="PAYMENT_MODULE_CODE")
private String paymentModuleCode;

@Setter
@Getter
@Column (name ="SHIPPING_MODULE_CODE")
private String shippingModuleCode;

@Setter
@Getter
@Column(name = "CUSTOMER_AGREED")
private Boolean customerAgreement = false;

@Setter
@Getter
@Column(name = "CONFIRMED_ADDRESS")
private Boolean confirmedAddress = false;

@Setter
@Getter
@Embedded
private Delivery delivery = null;

@Setter
@Getter
@Valid
@Embedded
private Billing billing = null;

@Setter
@Getter
@Embedded
@Deprecated
private CreditCard creditCard = null;


@Setter
@Getter
@ManyToOne(targetEntity = Currency.class)
@JoinColumn(name = "CURRENCY_ID")
private Currency currency;

@Setter
@Getter
@Type(type="locale")
@Column (name ="LOCALE")
private Locale locale;


@Setter
@Getter
@JsonIgnore
@ManyToOne(targetEntity = MerchantStore.class)
@JoinColumn(name="MERCHANTID")
private MerchantStore merchant;

@Setter
@Getter
@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
private Set<OrderProduct> orderProducts = new LinkedHashSet<>();

@Setter
@Getter
@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
@OrderBy(clause = "sort_order asc")
private Set<OrderTotal> orderTotal = new LinkedHashSet<>();

@Setter
@Getter
@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
@OrderBy(clause = "ORDER_STATUS_HISTORY_ID asc")
private Set<OrderStatusHistory> orderHistory = new LinkedHashSet<>();

@Setter
@Getter
@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
private Set<OrderAttribute> orderAttributes = new LinkedHashSet<>();

public Order() {
}

@Setter
@Getter
@Column (name ="CUSTOMER_EMAIL_ADDRESS", length=50, nullable=false)
private String customerEmailAddress;


@Override
public Long getId() {
	return id;
}

@Override
public void setId(Long id) {
	this.id = id;
}

public Date getLastModified() {
	return CloneUtils.clone(lastModified);
}

public void setLastModified(Date lastModified) {
	this.lastModified = CloneUtils.clone(lastModified);
}

public Date getDatePurchased() {
	return CloneUtils.clone(datePurchased);
}

public void setDatePurchased(Date datePurchased) {
	this.datePurchased = CloneUtils.clone(datePurchased);
}

public Date getOrderDateFinished() {
	return CloneUtils.clone(orderDateFinished);
}

public void setOrderDateFinished(Date orderDateFinished) {
	this.orderDateFinished = CloneUtils.clone(orderDateFinished);
}


}