package dev.vulcanium.site.tech.model.catalog.product.attribute;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReadableProductAttributeValue extends ProductOptionValue {

private static final long serialVersionUID = 1L;

private String name;
private String lang;
private String description;


}
