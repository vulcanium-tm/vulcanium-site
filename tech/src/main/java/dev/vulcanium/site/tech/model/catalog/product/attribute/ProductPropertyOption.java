package dev.vulcanium.site.tech.model.catalog.product.attribute;

import dev.vulcanium.business.model.entity.Entity;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductPropertyOption extends Entity implements Serializable {

private static final long serialVersionUID = 1L;
private String code;
private String type;
private boolean readOnly;

}
