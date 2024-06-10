package dev.vulcanium.site.tech.model.catalog;

import dev.vulcanium.site.tech.model.catalog.category.ReadableCategory;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReadableCatalogCategoryEntry extends CatalogEntryEntity {

private static final long serialVersionUID = 1L;
private String creationDate;
private ReadableCategory category;

}
