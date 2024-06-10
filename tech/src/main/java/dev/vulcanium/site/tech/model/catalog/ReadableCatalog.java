package dev.vulcanium.site.tech.model.catalog;

import java.util.ArrayList;
import java.util.List;

import dev.vulcanium.site.tech.model.catalog.category.ReadableCategory;
import dev.vulcanium.site.tech.model.store.ReadableMerchantStore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReadableCatalog extends ReadableCatalogName {

private static final long serialVersionUID = 1L;
private ReadableMerchantStore store;
private List<ReadableCategory> category = new ArrayList<ReadableCategory>();


}
