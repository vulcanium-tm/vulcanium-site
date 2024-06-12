package dev.vulcanium.site.tech.model.content;

import dev.vulcanium.business.model.entity.Entity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

public class PersistableContent extends Entity implements Serializable {

private static final long serialVersionUID = 1L;
@Setter
@Getter
private String code;
private boolean isDisplayedInMenu;

public boolean isDisplayedInMenu() {
	return isDisplayedInMenu;
}

public void setDisplayedInMenu(boolean isDisplayedInMenu) {
	this.isDisplayedInMenu = isDisplayedInMenu;
}

@Setter
@Getter
private List<ObjectContent> descriptions = new ArrayList<>();

}