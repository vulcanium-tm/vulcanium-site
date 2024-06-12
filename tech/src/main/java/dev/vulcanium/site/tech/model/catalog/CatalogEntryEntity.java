package dev.vulcanium.site.tech.model.catalog;

import dev.vulcanium.business.model.entity.Entity;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CatalogEntryEntity extends Entity  {

private static final long serialVersionUID = 1L;
private String catalog;
private boolean visible;

}
