package dev.vulcanium.site.tech.model.content;

import dev.vulcanium.site.tech.model.entity.ResourceUrlAccess;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Deprecated
public class ObjectContent extends ContentPath implements ResourceUrlAccess {

/**
 *
 */
private static final long serialVersionUID = 1L;

private String slug;
private String metaDetails;
private String title;
private String pageContent;
private String language;


}