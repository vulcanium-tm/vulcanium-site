package dev.vulcanium.site.tech.model.catalog.manufacturer;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReadableManufacturer extends ManufacturerEntity implements
		Serializable {

private static final long serialVersionUID = 1L;
private ManufacturerDescription description;

}
