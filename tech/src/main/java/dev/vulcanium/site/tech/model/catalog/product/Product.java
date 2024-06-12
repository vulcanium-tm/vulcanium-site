package dev.vulcanium.site.tech.model.catalog.product;

import dev.vulcanium.business.model.entity.Entity;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Product extends Entity implements Serializable {

private static final long serialVersionUID = 1L;

private boolean productShipeable = false;

private boolean available;
private boolean visible = true;

private int sortOrder;
private String dateAvailable;
private String creationDate;


}
