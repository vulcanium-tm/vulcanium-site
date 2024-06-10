package dev.vulcanium.site.tech.model.catalog.category;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReadableCategory extends CategoryEntity {

private static final long serialVersionUID = 1L;
private CategoryDescription description;
private int productCount;
private String store;
private List<ReadableCategory> children = new ArrayList<>();

}
