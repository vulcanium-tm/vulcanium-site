package dev.vulcanium.site.tech.model.catalog.product.attribute.api;

import java.io.Serializable;

import dev.vulcanium.site.tech.model.catalog.product.attribute.ProductOptionValue;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductOptionValueEntity extends ProductOptionValue implements Serializable {

private static final long serialVersionUID = 1L;
private int order;


}