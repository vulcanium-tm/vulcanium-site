package dev.vulcanium.site.tech.model.catalog.product;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersistableProductPrice extends ProductPriceEntity {

private static final long serialVersionUID = 1L;

private String sku;
private Long productAvailabilityId;

private List<ProductPriceDescription> descriptions = new ArrayList<>();

}
