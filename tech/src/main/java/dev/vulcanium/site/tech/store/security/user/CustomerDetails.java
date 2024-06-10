package dev.vulcanium.site.tech.store.security.user;

import java.util.Collection;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

@Setter
@Getter
public class CustomerDetails extends User {

private static final long serialVersionUID = 1L;

private String email;
private Long id;
private String firstName;
private String lastName;

public CustomerDetails(
		String username,
		String password,
		boolean enabled,
		boolean accountNonExpired,
		boolean credentialsNonExpired,
		boolean accountNonLocked,
		Collection<? extends GrantedAuthority> authorities) {
	super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
}

public CustomerDetails(
		String username,
		String password,
		Collection<? extends GrantedAuthority> authorities) {
	super(username, password, true, true, true, true, authorities);
}

}
