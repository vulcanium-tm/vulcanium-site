package dev.vulcanium.site.tech.model.entity;

import dev.vulcanium.business.model.entity.Entity;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class ShopEntity extends Entity implements Serializable {

private static final long serialVersionUID = 1L;
private String language;


}
