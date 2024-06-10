package dev.vulcanium.site.tech.model.content;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Deprecated
public class ReadableContentEntity extends ContentEntity {

private static final long serialVersionUID = 1L;
private ContentDescriptionEntity description = null;

}