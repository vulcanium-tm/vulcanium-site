package dev.vulcanium.site.tech.model.catalog.product.attribute;

import java.util.ArrayList;
import java.util.List;

import dev.vulcanium.site.tech.model.catalog.product.attribute.api.ReadableProductOptionValue;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReadableProductOption extends ProductPropertyOption {

private static final long serialVersionUID = 1L;

private String name;
private String lang;
private boolean variant;
private List<ReadableProductOptionValue> optionValues = new ArrayList<>();


}
