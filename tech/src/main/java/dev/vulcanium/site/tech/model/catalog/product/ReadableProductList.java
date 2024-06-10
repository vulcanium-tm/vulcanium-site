package dev.vulcanium.site.tech.model.catalog.product;

import java.util.ArrayList;
import java.util.List;
import dev.vulcanium.site.tech.model.entity.ReadableList;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReadableProductList extends ReadableList {

private static final long serialVersionUID = 1L;

private List<ReadableProduct> products = new ArrayList<ReadableProduct>();

}
