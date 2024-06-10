package dev.vulcanium.site.tech.model.order;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import dev.vulcanium.business.model.order.OrderTotalSummary;
import dev.vulcanium.business.model.shipping.ShippingOption;
import dev.vulcanium.business.model.shipping.ShippingSummary;
import dev.vulcanium.business.model.shoppingcart.ShoppingCartItem;
import lombok.Getter;
import lombok.Setter;

/**
 * Orders saved on the website
 */
@Setter
@Getter
public class ShopOrder extends PersistableOrder implements Serializable {

private static final long serialVersionUID = 1L;
private List<ShoppingCartItem> shoppingCartItems;
private String cartCode = null;

private OrderTotalSummary orderTotalSummary;


private ShippingSummary shippingSummary;
private ShippingOption selectedShippingOption = null;

private String defaultPaymentMethodCode = null;


private String paymentMethodType = null;
private Map<String,String> payment;

private String errorMessage = null;


}