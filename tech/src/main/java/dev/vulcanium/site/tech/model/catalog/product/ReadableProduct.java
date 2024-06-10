package dev.vulcanium.site.tech.model.catalog.product;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import dev.vulcanium.site.tech.model.catalog.category.ReadableCategory;
import dev.vulcanium.site.tech.model.catalog.manufacturer.ReadableManufacturer;
import dev.vulcanium.site.tech.model.catalog.product.attribute.ReadableProductAttribute;
import dev.vulcanium.site.tech.model.catalog.product.attribute.ReadableProductOption;
import dev.vulcanium.site.tech.model.catalog.product.attribute.ReadableProductProperty;
import dev.vulcanium.site.tech.model.catalog.product.variant.ReadableProductVariant;
import dev.vulcanium.site.tech.model.catalog.product.type.ReadableProductType;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReadableProduct extends ProductEntity implements Serializable {

/**
 *
 */
private static final long serialVersionUID = 1L;

private ProductDescription description;
private ReadableProductPrice productPrice;
private String finalPrice = "0";
private String originalPrice = null;
private boolean discounted = false;
private ReadableImage image;
private List<ReadableImage> images = new ArrayList<ReadableImage>();
private ReadableManufacturer manufacturer;
private List<ReadableProductAttribute> attributes = new ArrayList<ReadableProductAttribute>();
private List<ReadableProductOption> options = new ArrayList<ReadableProductOption>();
private List<ReadableProductVariant> variants = new ArrayList<ReadableProductVariant>();
private List<ReadableProductProperty> properties = new ArrayList<ReadableProductProperty>();
private List<ReadableCategory> categories = new ArrayList<>();
private ReadableProductType type;
private boolean canBePurchased = false;

// RENTAL
private RentalOwner owner;


}
