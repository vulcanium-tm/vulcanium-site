package dev.vulcanium.site.tech.model.order;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dev.vulcanium.business.model.order.orderstatus.OrderStatus;
import dev.vulcanium.business.model.order.payment.CreditCard;
import dev.vulcanium.business.model.payments.PaymentType;
import dev.vulcanium.site.tech.model.order.total.OrderTotal;
import dev.vulcanium.business.model.order.Order;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderEntity extends Order implements Serializable {

private static final long serialVersionUID = 1L;
private List<OrderTotal> totals;
private List<OrderAttribute> attributes = new ArrayList<>();

private PaymentType paymentType;
private String paymentModule;
private String shippingModule;
private List<OrderStatus> previousOrderStatus;
private OrderStatus orderStatus;
private CreditCard creditCard;
private Date datePurchased;
private boolean customerAgreed;
private boolean confirmedAddress;
private String comments;


}
