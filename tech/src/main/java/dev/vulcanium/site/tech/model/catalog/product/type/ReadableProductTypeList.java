package dev.vulcanium.site.tech.model.catalog.product.type;

import java.util.ArrayList;
import java.util.List;

import dev.vulcanium.site.tech.model.entity.ReadableList;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReadableProductTypeList extends ReadableList {

private static final long serialVersionUID = 1L;

List<ReadableProductType> list = new ArrayList<ReadableProductType>();

}
