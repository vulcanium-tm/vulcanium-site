package dev.vulcanium.site.tech.model.catalog.product;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * Lightweight version of Persistable product
 */
@Getter
public class LightPersistableProduct implements Serializable {

private static final long serialVersionUID = 1L;
@Setter
private String price;
@Setter
private boolean available;
private boolean productShipeable;
@Setter
private int quantity;

public void setProductShipeable(Boolean productShipeable) { this.productShipeable = productShipeable; }

}
