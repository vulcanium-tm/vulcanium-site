package dev.vulcanium.site.tech.model.order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.vulcanium.site.tech.model.customer.PersistableCustomer;
import dev.vulcanium.site.tech.model.order.transaction.PersistablePayment;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * This object is used when processing an order from the API
 * It will be used for processing the payment and as Order meta data
 */
@Setter
@Getter
public class PersistableOrder extends OrderEntity {

private static final long serialVersionUID = 1L;

private PersistablePayment payment;
private Long shippingQuote;
@JsonIgnore
private Long shoppingCartId;
@JsonIgnore
private Long customerId;
private PersistableCustomer customer;
private List<PersistableOrderProduct> orderProductItems;
private boolean shipToBillingAdress = true;
private boolean shipToDeliveryAddress = false;
}
