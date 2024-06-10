package dev.vulcanium.site.tech.model.catalog.manufacturer;

import java.util.ArrayList;
import java.util.List;
import dev.vulcanium.site.tech.model.entity.ReadableList;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReadableManufacturerList extends ReadableList {

private static final long serialVersionUID = 1L;

private List<ReadableManufacturer> manufacturers = new ArrayList<ReadableManufacturer>();

}
