package dev.vulcanium.site.tech.model.catalog.product.attribute.api;

import java.util.ArrayList;
import java.util.List;

import dev.vulcanium.site.tech.model.entity.ReadableList;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReadableProductAttributeList extends ReadableList {

private static final long serialVersionUID = 1L;

private List<ReadableProductAttributeEntity> attributes = new ArrayList<>();

}
