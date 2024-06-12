package dev.vulcanium.business.model.shop;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ContactForm {

@NotEmpty
private String name;
@NotEmpty
private String subject;
@Email
private String email;
@NotEmpty
private String comment;


}
