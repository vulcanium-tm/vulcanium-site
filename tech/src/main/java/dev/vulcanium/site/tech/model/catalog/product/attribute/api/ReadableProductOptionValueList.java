package dev.vulcanium.site.tech.model.catalog.product.attribute.api;

import java.util.ArrayList;
import java.util.List;

import dev.vulcanium.site.tech.model.entity.ReadableList;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReadableProductOptionValueList extends ReadableList {

private static final long serialVersionUID = 1L;
List<ReadableProductOptionValue> optionValues = new ArrayList<>();

}
