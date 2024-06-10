package dev.vulcanium.site.tech.model.catalog.product.inventory;

import java.util.ArrayList;
import java.util.List;

import dev.vulcanium.site.tech.model.catalog.product.ReadableProductPrice;
import dev.vulcanium.site.tech.model.store.ReadableMerchantStore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReadableInventory extends InventoryEntity {

private static final long serialVersionUID = 1L;
private String creationDate;

private ReadableMerchantStore store;
private String sku;
private List<ReadableProductPrice> prices = new ArrayList<ReadableProductPrice>();
private String price;


}
