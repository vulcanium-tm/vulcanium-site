package dev.vulcanium.site.tech.store.api.customer;

import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.business.services.customer.CustomerService;
import dev.vulcanium.business.services.customer.review.CustomerReviewService;
import dev.vulcanium.site.tech.model.customer.PersistableCustomerReview;
import dev.vulcanium.site.tech.model.customer.ReadableCustomerReview;
import dev.vulcanium.site.tech.store.controller.store.facade.StoreFacade;
import dev.vulcanium.site.tech.store.facade.customer.CustomerFacade;
import dev.vulcanium.site.tech.utils.LanguageUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/api")
public class CustomerReviewApi {

private static final Logger LOGGER = LoggerFactory.getLogger(CustomerReviewApi.class);

@Inject
private CustomerFacade customerFacade;

@Inject
private StoreFacade storeFacade;

@Inject
private LanguageUtils languageUtils;

@Inject
private CustomerService customerService;

@Inject
private CustomerReviewService customerReviewService;

/**
 * Reviews made for a given customer
 *
 * @param id
 * @param review
 * @return
 * @throws Exception
 */
@PostMapping("/private/customers/{id}/reviews")
@ResponseStatus(HttpStatus.CREATED)
@ApiImplicitParams({
		@ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT"),
		@ApiImplicitParam(name = "lang", dataType = "string", defaultValue = "en")
})
public PersistableCustomerReview create(
		@PathVariable final Long id,
		@Valid @RequestBody PersistableCustomerReview review,
		@ApiIgnore MerchantStore merchantStore,
		@ApiIgnore Language language) {
	return customerFacade.createCustomerReview(id, review, merchantStore, language);
}

@GetMapping("/customers/{id}/reviews")
@ApiImplicitParams({
		@ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT"),
		@ApiImplicitParam(name = "lang", dataType = "string", defaultValue = "en")
})
public List<ReadableCustomerReview> getAll(
		@PathVariable final Long id, @ApiIgnore MerchantStore merchantStore, @ApiIgnore Language language) {
	return customerFacade.getAllCustomerReviewsByReviewed(id, merchantStore, language);
}

@PutMapping("/private/customers/{id}/reviews/{reviewid}")
public PersistableCustomerReview update(
		@PathVariable final Long id,
		@PathVariable final Long reviewId,
		@Valid @RequestBody PersistableCustomerReview review,
		@ApiIgnore MerchantStore merchantStore,
		@ApiIgnore Language language) {
	return customerFacade.updateCustomerReview(id, reviewId, review, merchantStore, language);
}

@DeleteMapping("/private/customers/{id}/reviews/{reviewId}")
public void delete(
		@PathVariable final Long id,
		@PathVariable final Long reviewId,
		@ApiIgnore MerchantStore merchantStore,
		@ApiIgnore Language language) {
	customerFacade.deleteCustomerReview(id, reviewId, merchantStore, language);
}
}
