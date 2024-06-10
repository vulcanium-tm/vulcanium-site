package dev.vulcanium.site.tech.model.customer.address;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressLocation implements Serializable {

private static final long serialVersionUID = 1L;

private String postalCode;
private String countryCode;

}
