package dev.vulcanium.site.tech.model.catalog.manufacturer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersistableManufacturer extends ManufacturerEntity implements
		Serializable {

private static final long serialVersionUID = 1L;
private List<ManufacturerDescription> descriptions = new ArrayList<ManufacturerDescription>();

}
