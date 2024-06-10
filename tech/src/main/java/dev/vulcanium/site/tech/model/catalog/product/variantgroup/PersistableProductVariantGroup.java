package dev.vulcanium.site.tech.model.catalog.product.variantgroup;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersistableProductVariantGroup extends ProductVariantGroup {

private static final long serialVersionUID = 1L;
List<Long> productVariants = null;

}
