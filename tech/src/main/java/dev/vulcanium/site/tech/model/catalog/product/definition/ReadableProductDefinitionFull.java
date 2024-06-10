package dev.vulcanium.site.tech.model.catalog.product.definition;

import java.util.ArrayList;
import java.util.List;

import dev.vulcanium.site.tech.model.catalog.product.ProductDescription;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReadableProductDefinitionFull extends ReadableProductDefinition {

private static final long serialVersionUID = 1L;
private List<ProductDescription> descriptions = new ArrayList<>();

}
