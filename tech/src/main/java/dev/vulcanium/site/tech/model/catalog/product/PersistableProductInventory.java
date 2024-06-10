package dev.vulcanium.site.tech.model.catalog.product;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PersistableProductInventory implements Serializable {

private static final long serialVersionUID = 1L;

private String sku;
private int quantity = 0;
private PersistableProductPrice price;

}
