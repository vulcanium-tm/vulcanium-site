package dev.vulcanium.site.tech.model.catalog.product.attribute;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import dev.vulcanium.site.tech.model.catalog.product.attribute.api.ProductAttributeEntity;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReadableProductAttribute extends ProductAttributeEntity implements Serializable {

private static final long serialVersionUID = 1L;

private String name;
private String lang;
private String code;
private String type;

private List<ReadableProductAttributeValue> attributeValues = new ArrayList<>();

}
