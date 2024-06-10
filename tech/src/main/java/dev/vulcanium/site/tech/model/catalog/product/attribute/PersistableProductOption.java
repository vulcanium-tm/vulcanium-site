package dev.vulcanium.site.tech.model.catalog.product.attribute;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PersistableProductOption extends ProductOptionEntity implements
		Serializable {

private static final long serialVersionUID = 1L;
private List<ProductOptionDescription> descriptions = new ArrayList<>();

}
