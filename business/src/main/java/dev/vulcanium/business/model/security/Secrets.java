package dev.vulcanium.business.model.security;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Secrets implements Serializable {

private static final long serialVersionUID = 1L;
private String userName;
private String password;
}
