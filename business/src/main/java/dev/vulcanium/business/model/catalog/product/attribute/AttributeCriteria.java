package dev.vulcanium.business.model.catalog.product.attribute;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AttributeCriteria implements Serializable {

private static final long serialVersionUID = 1L;
private String attributeCode;
private String attributeValue;

}
