package dev.vulcanium.site.tech.model.catalog.product;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import dev.vulcanium.site.tech.model.catalog.product.attribute.ProductAttribute;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductPriceRequest implements Serializable {

private static final long serialVersionUID = 1L;
private List<ProductAttribute> options = new ArrayList<ProductAttribute>();
private String sku;

}
