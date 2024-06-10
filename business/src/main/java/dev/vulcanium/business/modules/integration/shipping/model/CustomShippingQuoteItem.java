package dev.vulcanium.business.modules.integration.shipping.model;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class CustomShippingQuoteItem {

private String priceText;
private BigDecimal price;

}
