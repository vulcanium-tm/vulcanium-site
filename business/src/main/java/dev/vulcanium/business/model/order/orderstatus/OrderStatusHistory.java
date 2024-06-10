package dev.vulcanium.business.model.order.orderstatus;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.TableGenerator;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.vulcanium.business.constants.SchemaConstant;
import dev.vulcanium.business.model.order.Order;
import dev.vulcanium.business.utils.CloneUtils;

@Entity
@Table (name="ORDER_STATUS_HISTORY" )
public class OrderStatusHistory implements Serializable {
private static final long serialVersionUID = 3438730310126102187L;

@Id
@Column ( name="ORDER_STATUS_HISTORY_ID")
@TableGenerator(name = "TABLE_GEN", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT",
		pkColumnValue = "STATUS_HIST_ID_NEXT_VALUE")
@GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
private Long id;

@JsonIgnore
@ManyToOne(targetEntity = Order.class)
@JoinColumn(name = "ORDER_ID", nullable = false)
private Order order;

@Enumerated(value = EnumType.STRING)
private OrderStatus status;

@Temporal(TemporalType.TIMESTAMP)
@Column(name = "DATE_ADDED", nullable = false)
private Date dateAdded;

@Column(name = "CUSTOMER_NOTIFIED")
private java.lang.Integer customerNotified;

@Column(name = "COMMENTS")
@Type(type = "org.hibernate.type.TextType")
private String comments;

public OrderStatusHistory() {
}

public Long getId() {
	return id;
}

public void setId(Long id) {
	this.id = id;
}

public Order getOrder() {
	return order;
}

public void setOrder(Order order) {
	this.order = order;
}

public OrderStatus getStatus() {
	return status;
}

public void setStatus(OrderStatus status) {
	this.status = status;
}

public Date getDateAdded() {
	return CloneUtils.clone(dateAdded);
}

public void setDateAdded(Date dateAdded) {
	this.dateAdded = CloneUtils.clone(dateAdded);
}

public java.lang.Integer getCustomerNotified() {
	return customerNotified;
}

public void setCustomerNotified(java.lang.Integer customerNotified) {
	this.customerNotified = customerNotified;
}

public String getComments() {
	return comments;
}

public void setComments(String comments) {
	this.comments = comments;
}

}