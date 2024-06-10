package dev.vulcanium.site.tech.model.catalog.product.attribute.optionset;

import java.util.List;

import dev.vulcanium.site.tech.model.catalog.product.attribute.ReadableProductOption;
import dev.vulcanium.site.tech.model.catalog.product.attribute.ReadableProductOptionValue;
import dev.vulcanium.site.tech.model.catalog.product.type.ReadableProductType;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReadableProductOptionSet extends ProductOptionSetEntity {

private static final long serialVersionUID = 1L;

private ReadableProductOption option;
private List<ReadableProductOptionValue> values;
private List<ReadableProductType> productTypes;

}
