package dev.vulcanium.site.tech.model.catalog.product.attribute;

import dev.vulcanium.business.model.entity.Entity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
