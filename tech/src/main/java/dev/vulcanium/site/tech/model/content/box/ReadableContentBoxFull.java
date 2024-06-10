package dev.vulcanium.site.tech.model.content.box;

import java.util.List;

import dev.vulcanium.site.tech.model.content.common.ContentDescription;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReadableContentBoxFull extends ReadableContentBox {

private static final long serialVersionUID = 1L;

private List<ContentDescription> descriptions;

}
