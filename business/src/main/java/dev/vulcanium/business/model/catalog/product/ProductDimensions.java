package dev.vulcanium.business.model.catalog.product;

import java.math.BigDecimal;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Embeddable
public class ProductDimensions {


@Column(name = "LENGTH")
private BigDecimal length;

@Column(name = "WIDTH")
private BigDecimal width;

@Column(name = "HEIGHT")
private BigDecimal height;

@Column(name = "WEIGHT")
private BigDecimal weight;

}