package dev.vulcanium.site.tech.store.security;

import java.io.Serializable;
import dev.vulcanium.site.tech.model.entity.Entity;

public class AuthenticationResponse extends Entity implements Serializable {
public AuthenticationResponse() {}

private static final long serialVersionUID = 1L;
private String token;

public AuthenticationResponse(Long userId, String token) {
	this.token = token;
	super.setId(userId);
}

public String getToken() {
	return token;
}

}
