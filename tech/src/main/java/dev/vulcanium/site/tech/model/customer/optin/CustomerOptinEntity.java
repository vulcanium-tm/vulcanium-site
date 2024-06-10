package dev.vulcanium.site.tech.model.customer.optin;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CustomerOptinEntity extends CustomerOptin {

private static final long serialVersionUID = 1L;

private String firstName;
private String lastName;
@NotNull
@Email
private String email;

}
