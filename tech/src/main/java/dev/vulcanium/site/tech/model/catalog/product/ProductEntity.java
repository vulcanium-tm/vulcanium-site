package dev.vulcanium.site.tech.model.catalog.product;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

/**
 * A product entity is used by services API to populate or retrieve a Product
 * entity
 */
@Setter
@Getter
public class ProductEntity extends Product implements Serializable {


private static final long serialVersionUID = 1L;

/**
 * -- GETTER --
 *  End RENTAL fields
 *
 * @return
 */
private BigDecimal price;
private int quantity = 0;
private String sku;
private boolean preOrder = false;
private boolean productVirtual = false;
private int quantityOrderMaximum = -1;
private int quantityOrderMinimum = 1;
private boolean productIsFree;

private ProductSpecification productSpecifications;
private Double rating = 0D;
private int ratingCount;
private int sortOrder;
private String refSku;


/**
 * RENTAL additional fields
 *
 * @return
 */

private int rentalDuration;
private int rentalPeriod;


}
