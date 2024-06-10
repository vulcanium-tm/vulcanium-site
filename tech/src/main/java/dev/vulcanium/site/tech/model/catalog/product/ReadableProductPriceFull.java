package dev.vulcanium.site.tech.model.catalog.product;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReadableProductPriceFull extends ReadableProductPrice {

/**
 *
 */
private static final long serialVersionUID = 1L;

private List<ProductPriceDescription> descriptions = new ArrayList<ProductPriceDescription>();

}
