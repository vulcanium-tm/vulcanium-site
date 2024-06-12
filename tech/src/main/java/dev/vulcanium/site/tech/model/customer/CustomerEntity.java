package dev.vulcanium.site.tech.model.customer;

import dev.vulcanium.site.tech.model.customer.address.Address;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CustomerEntity extends Customer implements Serializable {

private static final long serialVersionUID = 1L;

@ApiModelProperty(notes = "Customer email address. Required for registration")
@Email (message="{messages.invalid.email}")
@NotEmpty(message="{NotEmpty.customer.emailAddress}")
private String emailAddress;
@Valid
@ApiModelProperty(notes = "Customer billing address")
private Address billing;
private Address delivery;

@ApiModelProperty(notes = "2 letters language code en | fr | ...")
private String language;
private String firstName;
private String lastName;

private String provider;


private String storeCode;

private String userName;

private Double rating = 0D;
private int ratingCount;

}
