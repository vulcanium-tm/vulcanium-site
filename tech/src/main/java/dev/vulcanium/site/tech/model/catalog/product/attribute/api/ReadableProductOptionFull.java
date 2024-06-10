package dev.vulcanium.site.tech.model.catalog.product.attribute.api;

import java.util.ArrayList;
import java.util.List;

import dev.vulcanium.site.tech.model.catalog.product.attribute.ProductOptionDescription;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReadableProductOptionFull extends ReadableProductOptionEntity {

private static final long serialVersionUID = 1L;
private List<ProductOptionDescription> descriptions = new ArrayList<>();

}