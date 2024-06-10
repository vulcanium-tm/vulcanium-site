package dev.vulcanium.site.tech.model.shoppingcart;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import dev.vulcanium.site.tech.model.entity.ShopEntity;
import lombok.Getter;

public class ShoppingCartItem extends ShopEntity implements Serializable {

public void setSku(String sku) {
	this.sku = sku;
}

private static final long serialVersionUID = 1L;
private String name;
private String price;
private String image;
private BigDecimal productPrice;
private int quantity;
@Getter
private String sku;//sku
private String code;//shopping cart code
private boolean productVirtual;

private String subTotal;

private List<ShoppingCartAttribute> shoppingCartAttributes;

public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getPrice() {
	return price;
}
public void setPrice(String price) {
	this.price = price;
}
public int getQuantity() {
	if(quantity <= 0) {
		quantity = 1;
	}
	return quantity;
}
public void setQuantity(int quantity) {
	this.quantity = quantity;
}


public String getCode() {
	return code;
}
public void setCode(String code) {
	this.code = code;
}
public List<ShoppingCartAttribute> getShoppingCartAttributes() {
	return shoppingCartAttributes;
}
public void setShoppingCartAttributes(List<ShoppingCartAttribute> shoppingCartAttributes) {
	this.shoppingCartAttributes = shoppingCartAttributes;
}
public void setProductPrice(BigDecimal productPrice) {
	this.productPrice = productPrice;
}
public BigDecimal getProductPrice() {
	return productPrice;
}
public void setImage(String image) {
	this.image = image;
}
public String getImage() {
	return image;
}
public void setSubTotal(String subTotal) {
	this.subTotal = subTotal;
}
public String getSubTotal() {
	return subTotal;
}
public boolean isProductVirtual() {
	return productVirtual;
}
public void setProductVirtual(boolean productVirtual) {
	this.productVirtual = productVirtual;
}


}