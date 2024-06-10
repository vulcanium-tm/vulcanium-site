package dev.vulcanium.site.tech.model.catalog.product;

import java.io.Serializable;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PersistableProductReview extends ProductReviewEntity implements
		Serializable {

private static final long serialVersionUID = 1L;
@NotNull
private Long customerId;


}
