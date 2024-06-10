package dev.vulcanium.site.tech.model.catalog.product.variation;

import dev.vulcanium.site.tech.model.catalog.product.attribute.ReadableProductOption;
import dev.vulcanium.site.tech.model.catalog.product.attribute.ReadableProductOptionValue;

public class ReadableProductVariation extends ProductVariationEntity {

private static final long serialVersionUID = 1L;

ReadableProductOption option = null;
ReadableProductOptionValue optionValue = null;
public ReadableProductOption getOption() {
	return option;
}
public void setOption(ReadableProductOption option) {
	this.option = option;
}
public ReadableProductOptionValue getOptionValue() {
	return optionValue;
}
public void setOptionValue(ReadableProductOptionValue optionValue) {
	this.optionValue = optionValue;
}

}
