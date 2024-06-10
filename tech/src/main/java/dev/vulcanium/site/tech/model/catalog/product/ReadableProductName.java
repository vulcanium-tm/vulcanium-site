package dev.vulcanium.site.tech.model.catalog.product;

import dev.vulcanium.site.tech.model.catalog.product.ProductEntity;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReadableProductName extends ProductEntity {

private static final long serialVersionUID = 1L;

private String name;

}
