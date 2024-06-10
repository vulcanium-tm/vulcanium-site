package dev.vulcanium.site.tech.model.catalog.product.attribute;

import java.io.Serializable;

import dev.vulcanium.site.tech.model.entity.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductOptionValue extends Entity implements Serializable {

private static final long serialVersionUID = 1L;
private String code;
private String name;
private boolean defaultValue;
private int sortOrder;
private String image;

}
