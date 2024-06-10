package dev.vulcanium.business.model.catalog.product.price;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * Transient entity used to display
 * different price information in the catalogue
 */
@Setter
@Getter
public class FinalPrice implements Serializable {

private static final long serialVersionUID = 1L;
private BigDecimal discountedPrice = null;
private BigDecimal originalPrice = null;
private BigDecimal finalPrice = null;
private boolean discounted = false;
private int discountPercent = 0;
private String stringPrice;
private String stringDiscountedPrice;

private Date discountEndDate = null;

private boolean defaultPrice;
private ProductPrice productPrice;
List<FinalPrice> additionalPrices;

}
