package dev.vulcanium.site.tech.model.order;

import java.io.Serializable;

import dev.vulcanium.site.tech.model.catalog.product.ReadableProduct;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderProductEntity extends OrderProduct implements Serializable {

/**
 *
 */
private static final long serialVersionUID = 1L;
private int orderedQuantity;
private ReadableProduct product;

}
