package dev.vulcanium.site.tech.model.catalog.product.attribute;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReadableProductOptionValue extends ProductOptionValue {

private static final long serialVersionUID = 1L;

private String price;
private String image;
private String description;

}
