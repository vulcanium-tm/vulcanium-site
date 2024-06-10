package dev.vulcanium.site.tech.model.customer;

import java.io.Serializable;
import jakarta.validation.constraints.Size;
import dev.vulcanium.site.tech.validation.FieldMatch;


@FieldMatch.List({
		@FieldMatch(first="password",second="checkPassword",message="password.notequal")
	
})
public class SecuredCustomer extends PersistableCustomer implements Serializable {

private static final long serialVersionUID = 1L;

@Size(min=6, message="{registration.password.not.empty}")
private String password;

@Size(min=6, message="{registration.password.not.empty}")
private String checkPassword;

public String getPassword() {
	return password;
}

public void setPassword(String password) {
	this.password = password;
}

public String getCheckPassword()
{
	return checkPassword;
}

public void setCheckPassword( String checkPassword )
{
	this.checkPassword = checkPassword;
}



}
