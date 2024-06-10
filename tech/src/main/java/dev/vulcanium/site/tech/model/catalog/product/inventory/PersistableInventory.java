package dev.vulcanium.site.tech.model.catalog.product.inventory;

import java.util.List;
import dev.vulcanium.site.tech.model.catalog.product.PersistableProductPrice;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PersistableInventory extends InventoryEntity {

/**
 * An inventory for a given product and possibly a given variant
 */
private static final long serialVersionUID = 1L;
private String store;
@NotNull
private Long productId;
private Long variant;
private List<PersistableProductPrice> prices;

}
