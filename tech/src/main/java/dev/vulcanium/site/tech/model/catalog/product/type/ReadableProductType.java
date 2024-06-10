package dev.vulcanium.site.tech.model.catalog.product.type;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReadableProductType extends ProductTypeEntity {

private static final long serialVersionUID = 1L;
private ProductTypeDescription description;

}
