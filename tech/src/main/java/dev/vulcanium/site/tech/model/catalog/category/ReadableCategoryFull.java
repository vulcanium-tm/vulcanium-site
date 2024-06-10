package dev.vulcanium.site.tech.model.catalog.category;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReadableCategoryFull extends ReadableCategory {

private static final long serialVersionUID = 1L;

private List<CategoryDescription> descriptions = new ArrayList<>();

}
