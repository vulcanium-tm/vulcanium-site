package dev.vulcanium.business.services.catalog.product.review;

import dev.vulcanium.business.model.catalog.product.Product;
import dev.vulcanium.business.model.catalog.product.review.ProductReview;
import dev.vulcanium.business.model.customer.Customer;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.business.services.common.generic.SalesManagerEntityService;
import java.util.List;

public interface ProductReviewService extends
		SalesManagerEntityService<Long, ProductReview> {
	
	
	List<ProductReview> getByCustomer(Customer customer);
	List<ProductReview> getByProduct(Product product);
	List<ProductReview> getByProduct(Product product, Language language);
	ProductReview getByProductAndCustomer(Long productId, Long customerId);
	/**
	 * @param product
	 * @return
	 */
	List<ProductReview> getByProductNoCustomers(Product product);



}
