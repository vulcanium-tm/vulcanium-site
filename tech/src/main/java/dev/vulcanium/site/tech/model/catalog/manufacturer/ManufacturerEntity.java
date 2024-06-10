package dev.vulcanium.site.tech.model.catalog.manufacturer;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ManufacturerEntity extends Manufacturer implements Serializable {

private static final long serialVersionUID = 1L;
private int order;


}
