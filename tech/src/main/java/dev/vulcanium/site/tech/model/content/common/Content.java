package dev.vulcanium.site.tech.model.content.common;

import dev.vulcanium.business.model.entity.Entity;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Content extends Entity {

public Content() {}

public Content(String name) {
	this.name = name;
}

private static final long serialVersionUID = 1L;
private String name;
private String code;
private boolean visible;
private String contentType;


}