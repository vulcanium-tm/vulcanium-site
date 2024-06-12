package dev.vulcanium.site.tech.model.order.history;

import dev.vulcanium.business.model.entity.Entity;

public class OrderStatusHistory extends Entity {

private static final long serialVersionUID = 1L;
private long orderId;
private String orderStatus;
private String comments;

public long getOrderId() {
	return orderId;
}
public void setOrderId(long orderId) {
	this.orderId = orderId;
}
public String getOrderStatus() {
	return orderStatus;
}
public void setOrderStatus(String orderStatus) {
	this.orderStatus = orderStatus;
}
public String getComments() {
	return comments;
}
public void setComments(String comments) {
	this.comments = comments;
}

}
