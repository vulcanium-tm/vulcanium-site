package dev.vulcanium.site.tech.model.catalog.product.attribute;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReadableProductProperty extends ProductPropertyOption {

private static final long serialVersionUID = 1L;

/**
 * Property use option objects
 */
private ReadableProductOption property = null;
private ReadableProductPropertyValue propertyValue = null;


}
