package dev.vulcanium.site.tech.model.content.box;

import dev.vulcanium.site.tech.model.content.common.Content;
import dev.vulcanium.site.tech.model.content.common.ContentDescription;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReadableContentBox extends Content {

private ContentDescription description ;

private static final String BOX = "BOX";

private static final long serialVersionUID = 1L;

public ReadableContentBox() {
	super.setContentType(BOX);
}

}
