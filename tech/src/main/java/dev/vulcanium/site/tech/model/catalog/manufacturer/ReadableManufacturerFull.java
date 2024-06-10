package dev.vulcanium.site.tech.model.catalog.manufacturer;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReadableManufacturerFull extends ReadableManufacturer {

private static final long serialVersionUID = 1L;
private List<ManufacturerDescription> descriptions;

}
