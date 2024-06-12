package dev.vulcanium.business.model.security;

/**
 * Object used for saving a group
 */
public class PersistableGroup extends GroupEntity {

private static final long serialVersionUID = 1L;

public PersistableGroup() {}

public PersistableGroup(String name) {
	super.setName(name);
}

}
