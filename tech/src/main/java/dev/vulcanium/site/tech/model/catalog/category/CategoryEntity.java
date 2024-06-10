package dev.vulcanium.site.tech.model.catalog.category;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CategoryEntity extends Category implements Serializable {

private static final long serialVersionUID = 1L;

private int sortOrder;
private boolean visible;
private boolean featured;
private String lineage;
private int depth;
private Category parent;

}