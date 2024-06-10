package dev.vulcanium.site.tech.model.catalog.product.attribute;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import dev.vulcanium.site.tech.model.entity.Entity;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReadableProductVariant extends Entity implements Serializable {

private static final long serialVersionUID = 1L;

private String name;
private String code;
private List<ReadableProductVariantValue> options = new ArrayList<>();


}
