package dev.vulcanium.site.tech.model.customer;

import dev.vulcanium.site.tech.model.customer.address.Address;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BillingEntity extends Address {

private static final long serialVersionUID = 1L;

private String email;

private String countryName;

private String provinceName;

}
