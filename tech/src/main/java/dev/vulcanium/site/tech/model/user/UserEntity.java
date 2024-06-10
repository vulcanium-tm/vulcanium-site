package dev.vulcanium.site.tech.model.user;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserEntity extends User {

private static final long serialVersionUID = 1L;
private String firstName;
private String lastName;
private String emailAddress;
private String defaultLanguage;
private String userName;
private boolean active;


}
