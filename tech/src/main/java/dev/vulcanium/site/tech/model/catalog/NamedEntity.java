package dev.vulcanium.site.tech.model.catalog;

import java.io.Serializable;

import dev.vulcanium.site.tech.model.entity.ShopEntity;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class NamedEntity extends ShopEntity implements Serializable {

private static final long serialVersionUID = 1L;
private String name;
private String description;
private String friendlyUrl;
private String keyWords;
private String highlights;
private String metaDescription;
private String title;

}
