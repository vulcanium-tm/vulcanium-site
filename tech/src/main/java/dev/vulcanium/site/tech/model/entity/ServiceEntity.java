package dev.vulcanium.site.tech.model.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class ServiceEntity {

private int status = 0;
private String message = null;

}
