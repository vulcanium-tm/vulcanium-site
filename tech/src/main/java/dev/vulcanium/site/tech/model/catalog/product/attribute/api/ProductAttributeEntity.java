package dev.vulcanium.site.tech.model.catalog.product.attribute.api;

import java.io.Serializable;

import dev.vulcanium.site.tech.model.catalog.product.attribute.ProductAttribute;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductAttributeEntity extends ProductAttribute implements Serializable {

private static final long serialVersionUID = 1L;

private int sortOrder;
private boolean attributeDefault=false;
private boolean attributeDisplayOnly = false;

}
