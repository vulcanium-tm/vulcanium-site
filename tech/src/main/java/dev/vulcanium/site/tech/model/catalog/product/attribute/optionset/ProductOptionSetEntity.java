package dev.vulcanium.site.tech.model.catalog.product.attribute.optionset;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductOptionSetEntity implements Serializable {

private static final long serialVersionUID = 1L;
private Long id;
private String code;
private boolean readOnly;

}
