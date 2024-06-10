package dev.vulcanium.site.tech.model.catalog.product;

import java.io.Serializable;
import java.math.BigDecimal;
import dev.vulcanium.site.tech.model.references.DimensionUnitOfMeasure;
import dev.vulcanium.site.tech.model.references.WeightUnitOfMeasure;
import lombok.Getter;
import lombok.Setter;

/**
 * Specs weight dimension model and manufacturer
 */
@Setter
@Getter
public class ProductSpecification implements Serializable {

private static final long serialVersionUID = 1L;


private BigDecimal height;
private BigDecimal weight;
private BigDecimal length;
private BigDecimal width;
private String model;
private String manufacturer; //manufacturer code

private DimensionUnitOfMeasure dimensionUnitOfMeasure;
private WeightUnitOfMeasure weightUnitOfMeasure;

public static long getSerialversionuid() {
	return serialVersionUID;
}



}
