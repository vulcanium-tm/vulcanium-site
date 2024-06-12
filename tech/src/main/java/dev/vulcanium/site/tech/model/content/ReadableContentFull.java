package dev.vulcanium.site.tech.model.content;

import dev.vulcanium.business.model.entity.Entity;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Deprecated
public class ReadableContentFull extends Entity{

@Setter
@Getter
private String code;
@Setter
@Getter
private boolean visible;
@Setter
@Getter
private String contentType;

private boolean isDisplayedInMenu;

private static final long serialVersionUID = 1L;
@Setter
@Getter
private List<ContentDescriptionEntity> descriptions = new ArrayList<>();

public boolean isDisplayedInMenu() {
	return isDisplayedInMenu;
}
public void setDisplayedInMenu(boolean isDisplayedInMenu) {
	this.isDisplayedInMenu = isDisplayedInMenu;
}

}