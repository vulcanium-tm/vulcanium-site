package dev.vulcanium.site.tech.model.catalog.product.attribute.api;

import dev.vulcanium.site.tech.model.catalog.product.attribute.ProductOptionValueDescription;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReadableProductOptionValue extends ProductOptionValueEntity {

private String price;
private static final long serialVersionUID = 1L;
private ProductOptionValueDescription description;

}