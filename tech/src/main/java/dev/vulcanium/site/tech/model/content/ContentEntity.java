package dev.vulcanium.site.tech.model.content;

import dev.vulcanium.site.tech.model.entity.Entity;
import lombok.Getter;
import lombok.Setter;

@Deprecated
public class ContentEntity extends Entity{

private static final long serialVersionUID = 1L;
@Setter
@Getter
private String code;
@Setter
@Getter
private String contentType = "BOX";
private boolean isDisplayedInMenu;
@Setter
@Getter
private boolean visible;

public boolean isDisplayedInMenu() {
	return isDisplayedInMenu;
}
public void setDisplayedInMenu(boolean isDisplayedInMenu) {
	this.isDisplayedInMenu = isDisplayedInMenu;
}

public static long getSerialversionuid() {
	return serialVersionUID;
}

}