package dev.vulcanium.site.tech.store.security;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PasswordRequest extends AuthenticationRequest {

private static final long serialVersionUID = 1L;

@NotEmpty(message = "{message.password.required}")
private String current;

@NotEmpty(message = "{message.password.required}")
private String repeatPassword;

}
