package dev.vulcanium.business.model.shipping;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.TableGenerator;

import jakarta.validation.constraints.NotEmpty;

import dev.vulcanium.business.constants.SchemaConstant;
import dev.vulcanium.business.model.generic.SalesManagerEntity;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.country.Country;
import dev.vulcanium.business.model.reference.zone.Zone;

@Entity
@Table(name = "SHIPING_ORIGIN")
public class ShippingOrigin extends SalesManagerEntity<Long, ShippingOrigin> {

private static final long serialVersionUID = 1172536723717691214L;


@Id
@Column(name = "SHIP_ORIGIN_ID", unique=true, nullable=false)
@TableGenerator(name = "TABLE_GEN", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT",
		pkColumnValue = "SHP_ORIG_SEQ_NEXT_VAL")
@GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
private Long id;

@Column(name = "ACTIVE")
private boolean active;


@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name="MERCHANT_ID", nullable=false)
private MerchantStore merchantStore;

@NotEmpty
@Column (name ="STREET_ADDRESS", length=256)
private String address;


@NotEmpty
@Column (name ="CITY", length=100)
private String city;

@NotEmpty
@Column (name ="POSTCODE", length=20)
private String postalCode;

@Column (name ="STATE", length=100)
private String state;

@ManyToOne(fetch = FetchType.EAGER, targetEntity = Country.class)
@JoinColumn(name="COUNTRY_ID", nullable=true)
private Country country;

@ManyToOne(fetch = FetchType.EAGER, targetEntity = Zone.class)
@JoinColumn(name="ZONE_ID", nullable=true)
private Zone zone;

@Override
public Long getId() {
	return this.id;
}

@Override
public void setId(Long id) {
	this.id = id;
	
}

public MerchantStore getMerchantStore() {
	return merchantStore;
}

public void setMerchantStore(MerchantStore merchantStore) {
	this.merchantStore = merchantStore;
}

public boolean isActive() {
	return active;
}

public void setActive(boolean active) {
	this.active = active;
}

public String getAddress() {
	return address;
}

public void setAddress(String address) {
	this.address = address;
}

public String getCity() {
	return city;
}

public void setCity(String city) {
	this.city = city;
}

public String getPostalCode() {
	return postalCode;
}

public void setPostalCode(String postalCode) {
	this.postalCode = postalCode;
}

public String getState() {
	return state;
}

public void setState(String state) {
	this.state = state;
}

public Country getCountry() {
	return country;
}

public void setCountry(Country country) {
	this.country = country;
}

public Zone getZone() {
	return zone;
}

public void setZone(Zone zone) {
	this.zone = zone;
}



}
