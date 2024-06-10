package dev.vulcanium.site.tech.model.content.box;

import java.util.List;

import dev.vulcanium.site.tech.model.content.common.ContentDescription;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersistableContentBox extends ContentBox {

private static final long serialVersionUID = 1L;

private List<ContentDescription> descriptions;

}
