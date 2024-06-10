package dev.vulcanium.site.tech.model.order;

import java.io.Serializable;
import java.util.List;

import dev.vulcanium.site.tech.model.order.shipping.ReadableShippingSummary;
import dev.vulcanium.site.tech.model.order.total.ReadableOrderTotal;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReadableShopOrder extends ReadableOrder implements Serializable {

/**
 *
 */
private static final long serialVersionUID = 1L;

private ReadableShippingSummary shippingSummary;

private String errorMessage = null;//global error message
private List<ReadableOrderTotal> subTotals;//order calculation
private String grandTotal;//grand total - order calculation

}
