package dev.vulcanium.site.tech.model.catalog.product.attribute.api;

import java.util.ArrayList;
import java.util.List;

import dev.vulcanium.site.tech.model.catalog.product.attribute.ProductOptionDescription;
import dev.vulcanium.site.tech.model.catalog.product.attribute.ProductOptionEntity;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PersistableProductOptionEntity extends ProductOptionEntity {

private static final long serialVersionUID = 1L;
private List<ProductOptionDescription> descriptions = new ArrayList<>();

}