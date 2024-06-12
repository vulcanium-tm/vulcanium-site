package dev.vulcanium.site.tech.model.order;

import java.io.Serializable;

import dev.vulcanium.business.model.entity.Entity;


public class OrderProduct extends Entity implements Serializable {

private static final long serialVersionUID = 1L;
private String sku;
public String getSku() {
	return sku;
}
public void setSku(String sku) {
	this.sku = sku;
}

}
