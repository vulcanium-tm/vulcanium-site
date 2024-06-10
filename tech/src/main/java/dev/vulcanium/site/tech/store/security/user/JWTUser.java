package dev.vulcanium.site.tech.store.security.user;

import java.util.Collection;
import java.util.Date;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class JWTUser implements UserDetails {

private static final long serialVersionUID = 1L;
private final Long id;
private final String username;
@Getter
private final String firstname;
@Getter
private final String lastname;
private final String password;
@Getter
private final String email;
private final Collection<? extends GrantedAuthority> authorities;
private final boolean enabled;
private final Date lastPasswordResetDate;

public JWTUser(
		Long id,
		String username,
		String firstname,
		String lastname,
		String email,
		String password, Collection<? extends GrantedAuthority> authorities,
		boolean enabled,
		Date lastPasswordResetDate
) {
	this.id = id;
	this.username = username;
	this.firstname = firstname;
	this.lastname = lastname;
	this.email = email;
	this.password = password;
	this.authorities = authorities;
	this.enabled = enabled;
	this.lastPasswordResetDate = lastPasswordResetDate;
}

@JsonIgnore
public Long getId() {
	return id;
}

@Override
public String getUsername() {
	return username;
}

@JsonIgnore
@Override
public boolean isAccountNonExpired() {
	return true;
}

@JsonIgnore
@Override
public boolean isAccountNonLocked() {
	return true;
}

@JsonIgnore
@Override
public boolean isCredentialsNonExpired() {
	return true;
}

@JsonIgnore
@Override
public String getPassword() {
	return password;
}

@Override
public Collection<? extends GrantedAuthority> getAuthorities() {
	return authorities;
}

@Override
public boolean isEnabled() {
	return enabled;
}

@JsonIgnore
public Date getLastPasswordResetDate() {
	return lastPasswordResetDate;
}

}
