package dev.vulcanium.site.tech.model.catalog.product.inventory;

import java.util.ArrayList;
import java.util.List;
import dev.vulcanium.site.tech.model.entity.ReadableList;

public class ReadableInventoryList extends ReadableList {

private static final long serialVersionUID = 1L;
private List<ReadableInventory> inventory = new ArrayList<ReadableInventory>();
public List<ReadableInventory> getInventory() {
	return inventory;
}
public void setInventory(List<ReadableInventory> inventory) {
	this.inventory = inventory;
}

}
