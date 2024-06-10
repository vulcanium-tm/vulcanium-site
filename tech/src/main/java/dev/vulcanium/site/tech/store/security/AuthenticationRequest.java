package dev.vulcanium.site.tech.store.security;

import java.io.Serializable;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuthenticationRequest implements Serializable {


private static final long serialVersionUID = 1L;

/**
 * Username and password must be used when using normal system authentication
 * for a registered customer
 */
@NotEmpty(message="{NotEmpty.customer.userName}")
private String username;
@NotEmpty(message="{message.password.required}")
private String password;



public AuthenticationRequest() {
	super();
}

public AuthenticationRequest(String username, String password) {
	this.setUsername(username);
	this.setPassword(password);
}


}
