package dev.vulcanium.site.tech.model.catalog.product.attribute;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import dev.vulcanium.site.tech.model.catalog.product.attribute.api.ProductOptionValueEntity;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PersistableProductOptionValue extends ProductOptionValueEntity
		implements Serializable {

private static final long serialVersionUID = 1L;
private List<ProductOptionValueDescription> descriptions = new ArrayList<>();

}
