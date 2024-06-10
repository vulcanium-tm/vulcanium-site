package dev.vulcanium.site.tech.model.catalog.product.attribute;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReadableProductPropertyValue extends ProductOptionValue{

private static final long serialVersionUID = 1L;

private List<ProductOptionValueDescription> values = new ArrayList<ProductOptionValueDescription>();


}
