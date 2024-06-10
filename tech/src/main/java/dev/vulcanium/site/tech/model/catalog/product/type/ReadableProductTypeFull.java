package dev.vulcanium.site.tech.model.catalog.product.type;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReadableProductTypeFull extends ReadableProductType {

private static final long serialVersionUID = 1L;

private List<ProductTypeDescription> descriptions;

}
