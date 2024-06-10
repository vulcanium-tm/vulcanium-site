package dev.vulcanium.site.tech.model.catalog.product.attribute;

import java.io.Serializable;
import java.math.BigDecimal;

import dev.vulcanium.site.tech.model.catalog.product.attribute.api.ProductAttributeEntity;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PersistableProductAttribute extends ProductAttributeEntity
		implements Serializable {

private BigDecimal productAttributeWeight;
private BigDecimal productAttributePrice;
private Long productId;

private ProductPropertyOption option;
private PersistableProductOptionValue optionValue;

private static final long serialVersionUID = 1L;

}
