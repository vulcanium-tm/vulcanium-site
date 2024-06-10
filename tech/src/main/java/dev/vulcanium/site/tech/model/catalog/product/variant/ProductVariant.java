package dev.vulcanium.site.tech.model.catalog.product.variant;

import dev.vulcanium.site.tech.model.catalog.product.Product;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductVariant extends Product {

private static final long serialVersionUID = 1L;
private String store;
private Long productId;
private String sku;
private boolean available;
private String dateAvailable;
private int sortOrder;

private boolean defaultSelection;


}
