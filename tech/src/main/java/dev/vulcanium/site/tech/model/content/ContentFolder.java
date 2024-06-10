package dev.vulcanium.site.tech.model.content;

import dev.vulcanium.site.tech.model.content.common.Content;
import java.util.ArrayList;
import java.util.List;

public class ContentFolder {

private String path;
List<Content> content = new ArrayList<Content>();
public String getPath() {
	return path;
}
public void setPath(String path) {
	this.path = path;
}
public List<Content> getContent() {
	return content;
}
public void setContent(List<Content> content) {
	this.content = content;
}

}
