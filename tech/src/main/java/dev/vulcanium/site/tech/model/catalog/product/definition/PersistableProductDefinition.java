package dev.vulcanium.site.tech.model.catalog.product.definition;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import dev.vulcanium.site.tech.model.catalog.category.Category;
import dev.vulcanium.site.tech.model.catalog.product.ProductDescription;
import dev.vulcanium.site.tech.model.catalog.product.attribute.PersistableProductAttribute;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PersistableProductDefinition extends ProductDefinition {

/**
 * type and manufacturer are String type corresponding to the unique code
 */
private static final long serialVersionUID = 1L;

private List<ProductDescription> descriptions = new ArrayList<>();
private List<PersistableProductAttribute> properties = new ArrayList<PersistableProductAttribute>();
private List<Category> categories = new ArrayList<>();
private String type;
private String manufacturer;
private BigDecimal price;
private int quantity;

}
