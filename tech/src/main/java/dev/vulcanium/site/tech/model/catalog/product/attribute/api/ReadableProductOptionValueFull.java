package dev.vulcanium.site.tech.model.catalog.product.attribute.api;

import java.util.ArrayList;
import java.util.List;

import dev.vulcanium.site.tech.model.catalog.product.attribute.ProductOptionValueDescription;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReadableProductOptionValueFull extends ReadableProductOptionValue {

private static final long serialVersionUID = 1L;
private List<ProductOptionValueDescription> descriptions = new ArrayList<>();

}
