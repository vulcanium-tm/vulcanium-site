package dev.vulcanium.site.tech.model.order;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import dev.vulcanium.business.model.catalog.product.attribute.ProductAttribute;


public class PersistableOrderProduct extends OrderProductEntity implements
		Serializable {

/**
 *
 */
private static final long serialVersionUID = 1L;
private BigDecimal price;//specify final price
private List<ProductAttribute> attributes;//may have attributes



public void setAttributes(List<ProductAttribute> attributes) {
	this.attributes = attributes;
}

public List<ProductAttribute> getAttributes() {
	return attributes;
}

public BigDecimal getPrice() {
	return price;
}

public void setPrice(BigDecimal price) {
	this.price = price;
}

}
