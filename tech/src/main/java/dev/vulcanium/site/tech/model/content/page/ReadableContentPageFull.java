package dev.vulcanium.site.tech.model.content.page;

import java.util.List;

import dev.vulcanium.site.tech.model.content.common.ContentDescription;

public class ReadableContentPageFull extends ReadableContentPage {

private static final long serialVersionUID = 1L;
private List<ContentDescription> descriptions;

public List<ContentDescription> getDescriptions() {
	return descriptions;
}

public void setDescriptions(List<ContentDescription> descriptions) {
	this.descriptions = descriptions;
}

}
