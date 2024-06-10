package dev.vulcanium.site.tech.model.catalog.product;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

/**
 * A product entity is used by services API
 * to populate or retrieve a Product price entity
 */
@Setter
public class ProductPriceEntity extends ProductPrice implements Serializable {

private static final long serialVersionUID = 1L;
private String code;
@Getter
private boolean discounted = false;
@Getter
private String discountStartDate;
@Getter
private String discountEndDate;
@Getter
private boolean defaultPrice = true;
@Getter
private BigDecimal price;
@Getter
private BigDecimal discountedPrice;

public String getCode() {
	if(StringUtils.isBlank(this.code)) {
		code = DEFAULT_PRICE_CODE;
	}
	return code;
}

}
