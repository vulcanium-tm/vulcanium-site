package dev.vulcanium.site.tech.model.catalog.manufacturer;

import java.io.Serializable;

import dev.vulcanium.site.tech.model.entity.Entity;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Manufacturer extends Entity implements Serializable {

private static final long serialVersionUID = 1L;
private String code;

}
