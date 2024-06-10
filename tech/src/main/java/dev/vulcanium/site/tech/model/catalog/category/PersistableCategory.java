package dev.vulcanium.site.tech.model.catalog.category;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PersistableCategory extends CategoryEntity implements Serializable {

private static final long serialVersionUID = 1L;
private List<CategoryDescription> descriptions;
private List<PersistableCategory> children = new ArrayList<>();

}
