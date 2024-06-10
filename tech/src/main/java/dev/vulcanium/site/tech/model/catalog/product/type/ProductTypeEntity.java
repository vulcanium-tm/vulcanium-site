package dev.vulcanium.site.tech.model.catalog.product.type;

import java.io.Serializable;

import dev.vulcanium.site.tech.model.entity.Entity;
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
