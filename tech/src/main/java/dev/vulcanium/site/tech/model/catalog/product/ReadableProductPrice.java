package dev.vulcanium.site.tech.model.catalog.product;

import java.io.Serializable;
import dev.vulcanium.site.tech.model.entity.Entity;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReadableProductPrice extends Entity implements Serializable {

private static final long serialVersionUID = 1L;
private String originalPrice;
private String finalPrice;
private boolean defaultPrice = false;
private boolean discounted = false;
private ProductPriceDescription description;

}
