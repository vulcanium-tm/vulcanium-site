package dev.vulcanium.business.model.catalog.product.inventory;

import java.io.Serializable;

import dev.vulcanium.business.model.catalog.product.price.FinalPrice;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductInventory implements Serializable {

private static final long serialVersionUID = 1L;

private String sku;
private long quantity;
private FinalPrice price;

}
