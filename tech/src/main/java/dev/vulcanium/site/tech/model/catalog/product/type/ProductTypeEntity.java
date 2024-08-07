package dev.vulcanium.site.tech.model.catalog.product.type;

import dev.vulcanium.business.model.entity.Entity;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductTypeEntity extends Entity implements Serializable {

private static final long serialVersionUID = 1L;

private String code;
private boolean visible;
boolean allowAddToCart;

}
