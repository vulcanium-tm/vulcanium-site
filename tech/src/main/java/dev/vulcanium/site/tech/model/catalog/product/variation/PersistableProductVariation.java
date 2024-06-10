package dev.vulcanium.site.tech.model.catalog.product.variation;

import lombok.Getter;
import lombok.Setter;

/**
 * A Variant
 */
@Setter
@Getter
public class PersistableProductVariation extends ProductVariationEntity {

private static final long serialVersionUID = 1L;
private Long option = null;
private Long optionValue = null;


}
