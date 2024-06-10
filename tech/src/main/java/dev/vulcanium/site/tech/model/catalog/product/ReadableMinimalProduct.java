package dev.vulcanium.site.tech.model.catalog.product;

import java.io.Serializable;
import java.util.List;
import dev.vulcanium.site.tech.model.entity.ReadableDescription;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReadableMinimalProduct extends ProductEntity implements Serializable {

private static final long serialVersionUID = 1L;

private ReadableDescription description;
private ReadableProductPrice productPrice;
private String finalPrice = "0";
private String originalPrice = null;
private ReadableImage image;
private List<ReadableImage> images;


}
