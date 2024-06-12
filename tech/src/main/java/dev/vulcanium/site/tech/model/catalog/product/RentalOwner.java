package dev.vulcanium.site.tech.model.catalog.product;

import dev.vulcanium.business.model.entity.Entity;
import dev.vulcanium.site.tech.model.customer.address.Address;
import lombok.Getter;
import lombok.Setter;

/**
 * RENTAL customer
 */
@Getter
@Setter
public class RentalOwner extends Entity {

private static final long serialVersionUID = 1L;
private String firstName;
private String lastName;
private Address address;
private String emailAddress;

}
