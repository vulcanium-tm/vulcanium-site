package dev.vulcanium.site.tech.model.catalog.product.attribute.api;

import dev.vulcanium.site.tech.model.catalog.product.attribute.ProductOptionDescription;
import dev.vulcanium.site.tech.model.catalog.product.attribute.ProductOptionEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReadableProductOptionEntity extends ProductOptionEntity {

private static final long serialVersionUID = 1L;
private ProductOptionDescription description;

}
