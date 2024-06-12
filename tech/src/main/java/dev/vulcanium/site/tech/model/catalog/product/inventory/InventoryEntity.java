package dev.vulcanium.site.tech.model.catalog.product.inventory;

import dev.vulcanium.business.model.entity.Entity;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class InventoryEntity extends Entity {

private static final long serialVersionUID = 1L;
private int quantity;
private String region;
private String regionVariant;
private String owner;
private String dateAvailable;
private boolean available;
private int productQuantityOrderMin = 0;
private int productQuantityOrderMax = 0;


}
