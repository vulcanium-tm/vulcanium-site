package dev.vulcanium.site.tech.model.catalog.product.variation;

import dev.vulcanium.site.tech.model.entity.Entity;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductVariationEntity extends Entity {

private static final long serialVersionUID = 1L;
private String code;//sku
private String date;

private int sortOrder;

private boolean defaultValue = false;

}
