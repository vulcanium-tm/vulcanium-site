package dev.vulcanium.site.tech.model.references;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Address implements Serializable {

private static final long serialVersionUID = 1L;


private String stateProvince;
private String country;
private String address;
private String postalCode;
private String city;

private boolean active = true;


}
