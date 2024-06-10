package dev.vulcanium.site.tech.model.content.page;

import dev.vulcanium.site.tech.model.content.common.ContentDescription;

public class ReadableContentPage extends ContentPage {

private static final long serialVersionUID = 1L;

private ContentDescription description ;
private String path;


public ContentDescription getDescription() {
	return description;
}

public void setDescription(ContentDescription description) {
	this.description = description;
}

public String getPath() {
	return path;
}

public void setPath(String path) {
	this.path = path;
}

}
