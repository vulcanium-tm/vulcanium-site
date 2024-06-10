package dev.vulcanium.site.tech.model.content;

import dev.vulcanium.site.tech.model.content.common.Content;

/**
 * Input Object used in REST request
 */
@Deprecated
public class ContentName extends Content{

private static final long serialVersionUID = 1L;

public ContentName() {
	super();
}

public ContentName(String name) {
	super(name);
}

public ContentName(String name, String contentType) {
	super(name);
}




}