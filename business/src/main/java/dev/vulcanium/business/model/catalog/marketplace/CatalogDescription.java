package dev.vulcanium.business.model.catalog.marketplace;

import dev.vulcanium.business.model.common.description.Description;
import dev.vulcanium.business.model.reference.language.Language;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CatalogDescription extends Description {

private static final long serialVersionUID = 1L;

private Catalog catalog;



public CatalogDescription() {
}

public CatalogDescription(String name, Language language) {
	this.setName(name);
	this.setLanguage(language);
	super.setId(0L);
}


}
