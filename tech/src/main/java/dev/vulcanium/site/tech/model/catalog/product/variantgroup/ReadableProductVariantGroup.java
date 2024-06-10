package dev.vulcanium.site.tech.model.catalog.product.variantgroup;

import java.util.ArrayList;
import java.util.List;

import dev.vulcanium.site.tech.model.catalog.product.ReadableImage;
import dev.vulcanium.site.tech.model.catalog.product.variant.ReadableProductVariant;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReadableProductVariantGroup extends ProductVariantGroup {

private static final long serialVersionUID = 1L;

List<ReadableImage> images = new ArrayList<>();

private List<ReadableProductVariant> productVariants = new ArrayList<ReadableProductVariant>();

}
