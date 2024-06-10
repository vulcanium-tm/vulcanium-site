package dev.vulcanium.site.tech.model.catalog.product.definition;

import dev.vulcanium.site.tech.model.catalog.product.ProductSpecification;
import dev.vulcanium.site.tech.model.entity.Entity;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductDefinition extends Entity {

private static final long serialVersionUID = 1L;
private boolean visible = true;
private boolean shipeable = true;
private boolean virtual = false;
private boolean canBePurchased = true;
private String dateAvailable;
private String identifier;
private String sku;
private ProductSpecification productSpecifications;
private int sortOrder;


}
