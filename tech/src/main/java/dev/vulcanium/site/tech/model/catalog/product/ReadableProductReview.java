package dev.vulcanium.site.tech.model.catalog.product;

import java.io.Serializable;
import dev.vulcanium.site.tech.model.customer.ReadableCustomer;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReadableProductReview extends ProductReviewEntity implements Serializable {

private static final long serialVersionUID = 1L;
private ReadableCustomer customer;

}
