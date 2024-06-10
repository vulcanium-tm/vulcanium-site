package dev.vulcanium.site.tech.model.catalog;

import java.util.ArrayList;
import java.util.List;

import dev.vulcanium.site.tech.model.entity.ReadableList;

public class ReadableCatalogList extends ReadableList {

private static final long serialVersionUID = 1L;
private List<ReadableCatalog> catalogs = new ArrayList<ReadableCatalog>();
public List<ReadableCatalog> getCatalogs() {
	return catalogs;
}
public void setCatalogs(List<ReadableCatalog> catalogs) {
	this.catalogs = catalogs;
}

}
