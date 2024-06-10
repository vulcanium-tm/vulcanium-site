package dev.vulcanium.site.tech.model.catalog.product.definition;

import java.util.ArrayList;
import java.util.List;

import dev.vulcanium.site.tech.model.catalog.category.ReadableCategory;
import dev.vulcanium.site.tech.model.catalog.manufacturer.ReadableManufacturer;
import dev.vulcanium.site.tech.model.catalog.product.ProductDescription;
import dev.vulcanium.site.tech.model.catalog.product.ReadableImage;
import dev.vulcanium.site.tech.model.catalog.product.attribute.PersistableProductAttribute;
import dev.vulcanium.site.tech.model.catalog.product.inventory.ReadableInventory;
import dev.vulcanium.site.tech.model.catalog.product.type.ReadableProductType;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReadableProductDefinition extends ProductDefinition {

private static final long serialVersionUID = 1L;
private ReadableProductType type;
private List<ReadableCategory> categories = new ArrayList<>();
private ReadableManufacturer manufacturer;
private ProductDescription description = null;
private List<PersistableProductAttribute> properties = new ArrayList<PersistableProductAttribute>();
private List<ReadableImage> images = new ArrayList<>();
private ReadableInventory inventory;


}
