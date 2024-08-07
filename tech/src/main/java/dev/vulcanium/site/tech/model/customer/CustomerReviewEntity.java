package dev.vulcanium.site.tech.model.customer;

import java.io.Serializable;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotEmpty;

import dev.vulcanium.site.tech.model.entity.ShopEntity;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CustomerReviewEntity extends ShopEntity implements Serializable {

private static final long serialVersionUID = 1L;
@NotEmpty
private String description;
private Long customerId;//review creator
private String date;

@NotNull
@Min(1)
@Max(5)
private Double rating;


}
