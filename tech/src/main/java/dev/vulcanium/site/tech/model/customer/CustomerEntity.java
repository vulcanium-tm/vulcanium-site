package dev.vulcanium.site.tech.model.customer;

import java.io.Serializable;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

import org.springframework.validation.annotation.Validated;

import dev.vulcanium.site.tech.model.customer.address.Address;

import io.swagger.annotations.ApiModelProperty;

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

public void setUserName(final String userName) {
	this.userName = userName;
}

public String getUserName() {
	return userName;
}


public void setStoreCode(final String storeCode) {
	this.storeCode = storeCode;
}


public String getStoreCode() {
	return storeCode;
}


public void setEmailAddress(final String emailAddress) {
	this.emailAddress = emailAddress;
}


public String getEmailAddress() {
	return emailAddress;
}


public void setLanguage(final String language) {
	this.language = language;
}
public String getLanguage() {
	return language;
}


public Address getBilling() {
	return billing;
}
public void setBilling(final Address billing) {
	this.billing = billing;
}
public Address getDelivery() {
	return delivery;
}
public void setDelivery(final Address delivery) {
	this.delivery = delivery;
}

public String getFirstName() {
	return firstName;
}


public void setFirstName(String firstName) {
	this.firstName = firstName;
}


public String getLastName() {
	return lastName;
}


public void setLastName(String lastName) {
	this.lastName = lastName;
}


public int getRatingCount() {
	return ratingCount;
}

public void setRatingCount(int ratingCount) {
	this.ratingCount = ratingCount;
}

public Double getRating() {
	return rating;
}

public void setRating(Double rating) {
	this.rating = rating;
}

public String getProvider() {
	return provider;
}

public void setProvider(String provider) {
	this.provider = provider;
}

}
