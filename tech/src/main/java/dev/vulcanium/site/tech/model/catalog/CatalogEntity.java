package dev.vulcanium.site.tech.model.catalog;

import dev.vulcanium.business.model.entity.Entity;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CatalogEntity extends Entity implements Serializable {

private static final long serialVersionUID = 1L;

private boolean visible;
private boolean defaultCatalog;
private String code;

}
