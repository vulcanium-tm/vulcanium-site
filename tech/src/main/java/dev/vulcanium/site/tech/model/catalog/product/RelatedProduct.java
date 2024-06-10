package dev.vulcanium.site.tech.model.catalog.product;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RelatedProduct extends Product implements Serializable {

private static final long serialVersionUID = 1L;
private String relationShipType; //RELATED_ITEM ~ BUNDLED_ITEM

}
