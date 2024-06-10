package dev.vulcanium.site.tech.model.catalog.product.attribute.api;

import java.util.ArrayList;
import java.util.List;

import dev.vulcanium.site.tech.model.catalog.product.attribute.ProductOptionValueDescription;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersistableProductOptionValueEntity extends ProductOptionValueEntity {

private static final long serialVersionUID = 1L;
private List<ProductOptionValueDescription> descriptions = new ArrayList<>();

}