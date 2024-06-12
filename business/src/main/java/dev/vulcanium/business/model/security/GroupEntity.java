package dev.vulcanium.business.model.security;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GroupEntity implements Serializable {

private static final long serialVersionUID = 1L;

private String name;
private String type;

}
