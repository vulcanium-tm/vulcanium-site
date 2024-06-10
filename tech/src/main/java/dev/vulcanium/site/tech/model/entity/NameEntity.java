package dev.vulcanium.site.tech.model.entity;

import jakarta.validation.constraints.NotEmpty;

/**
 * Used as an input request object where an entity name and or id is important
 */
public class NameEntity extends Entity {

private static final long serialVersionUID = 1L;
@NotEmpty
private String name;

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

}
