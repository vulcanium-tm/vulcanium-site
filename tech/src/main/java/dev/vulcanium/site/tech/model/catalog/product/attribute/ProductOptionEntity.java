package dev.vulcanium.site.tech.model.catalog.product.attribute;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductOptionEntity extends ProductPropertyOption implements Serializable {

private static final long serialVersionUID = 1L;
private int order;

private String type;

}
