package dev.vulcanium.site.tech.model.catalog;

import java.util.ArrayList;
import java.util.List;

import dev.vulcanium.site.tech.model.catalog.category.ReadableCategory;


/**
 * Object representing the results of a search query
 */
public class SearchProductList extends ProductList {


private static final long serialVersionUID = 1L;
private List<ReadableCategory> categoryFacets = new ArrayList<ReadableCategory>();
public List<ReadableCategory> getCategoryFacets() {
	return categoryFacets;
}
public void setCategoryFacets(List<ReadableCategory> categoryFacets) {
	this.categoryFacets = categoryFacets;
}


}
