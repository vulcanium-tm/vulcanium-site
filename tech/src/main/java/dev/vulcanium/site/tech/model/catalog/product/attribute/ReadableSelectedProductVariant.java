package dev.vulcanium.site.tech.model.catalog.product.attribute;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * Input object used when selecting an item option
 */
@Setter
@Getter
public class ReadableSelectedProductVariant implements Serializable {

private static final long serialVersionUID = 1L;
private List<ReadableProductVariantValue> options = new ArrayList<ReadableProductVariantValue>();

}
