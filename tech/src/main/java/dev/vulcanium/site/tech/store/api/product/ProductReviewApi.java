package dev.vulcanium.site.tech.store.api.product;

import java.util.List;

import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import dev.vulcanium.business.services.catalog.product.ProductService;
import dev.vulcanium.business.services.catalog.product.review.ProductReviewService;
import dev.vulcanium.business.model.catalog.product.Product;
import dev.vulcanium.business.model.catalog.product.review.ProductReview;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.business.constants.Constants;
import dev.vulcanium.site.tech.model.catalog.product.PersistableProductReview;
import dev.vulcanium.site.tech.model.catalog.product.ReadableProductReview;
import dev.vulcanium.site.tech.store.facade.product.ProductCommonFacade;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import springfox.documentation.annotations.ApiIgnore;

@Controller
@RequestMapping("/api")
public class ProductReviewApi {

@Inject private ProductCommonFacade productCommonFacade;

@Inject private ProductService productService;

@Inject private ProductReviewService productReviewService;

private static final Logger LOGGER = LoggerFactory.getLogger(ProductReviewApi.class);

@RequestMapping(
		value = {
				"/private/products/{id}/reviews",
				"/auth/products/{id}/reviews",
				"/auth/products/{id}/reviews",
				"/auth/products/{id}/reviews"
		},
		method = RequestMethod.POST)
@ResponseStatus(HttpStatus.CREATED)
@ResponseBody
@ApiImplicitParams({
		@ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
		@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en")
})
public PersistableProductReview create(
		@PathVariable final Long id,
		@Valid @RequestBody PersistableProductReview review,
		@ApiIgnore MerchantStore merchantStore,
		@ApiIgnore Language language,
		HttpServletRequest request,
		HttpServletResponse response) {
	
	try {
		// rating already exist
		ProductReview prodReview =
				productReviewService.getByProductAndCustomer(
						review.getProductId(), review.getCustomerId());
		if (prodReview != null) {
			response.sendError(500, "A review already exist for this customer and product");
			return null;
		}
		
		// rating maximum 5
		if (review.getRating() > Constants.MAX_REVIEW_RATING_SCORE) {
			response.sendError(503, "Maximum rating score is " + Constants.MAX_REVIEW_RATING_SCORE);
			return null;
		}
		
		review.setProductId(id);
		
		productCommonFacade.saveOrUpdateReview(review, merchantStore, language);
		
		return review;
		
	} catch (Exception e) {
		LOGGER.error("Error while saving product review", e);
		try {
			response.sendError(503, "Error while saving product review" + e.getMessage());
		} catch (Exception ignore) {
		}
		
		return null;
	}
}

@RequestMapping(value = "/product/{id}/reviews", method = RequestMethod.GET)
@ResponseStatus(HttpStatus.OK)
@ResponseBody
@ApiImplicitParams({
		@ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
		@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en")
})
public List<ReadableProductReview> getAll(
		@PathVariable final Long id,
		@ApiIgnore MerchantStore merchantStore,
		@ApiIgnore Language language,
		HttpServletResponse response) {
	
	try {
		// product exist
		Product product = productService.getById(id);
		
		if (product == null) {
			response.sendError(404, "Product id " + id + " does not exists");
			return null;
		}
		
		List<ReadableProductReview> reviews =
				productCommonFacade.getProductReviews(product, merchantStore, language);
		
		return reviews;
		
	} catch (Exception e) {
		LOGGER.error("Error while getting product reviews", e);
		try {
			response.sendError(503, "Error while getting product reviews" + e.getMessage());
		} catch (Exception ignore) {
		}
		
		return null;
	}
}

@RequestMapping(
		value = {
				"/private/products/{id}/reviews/{reviewid}",
				"/auth/products/{id}/reviews/{reviewid}"
		},
		method = RequestMethod.PUT)
@ResponseStatus(HttpStatus.OK)
@ResponseBody
@ApiImplicitParams({
		@ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
		@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en")
})
public PersistableProductReview update(
		@PathVariable final Long id,
		@PathVariable final Long reviewId,
		@Valid @RequestBody PersistableProductReview review,
		@ApiIgnore MerchantStore merchantStore,
		@ApiIgnore Language language,
		HttpServletRequest request,
		HttpServletResponse response) {
	
	try {
		ProductReview prodReview = productReviewService.getById(reviewId);
		if (prodReview == null) {
			response.sendError(404, "Product review with id " + reviewId + " does not exist");
			return null;
		}
		
		if (prodReview.getCustomer().getId().longValue() != review.getCustomerId().longValue()) {
			response.sendError(404, "Product review with id " + reviewId + " does not exist");
			return null;
		}
		
		// rating maximum 5
		if (review.getRating() > Constants.MAX_REVIEW_RATING_SCORE) {
			response.sendError(503, "Maximum rating score is " + Constants.MAX_REVIEW_RATING_SCORE);
			return null;
		}
		
		review.setProductId(id);
		
		productCommonFacade.saveOrUpdateReview(review, merchantStore, language);
		
		return review;
		
	} catch (Exception e) {
		LOGGER.error("Error while saving product review", e);
		try {
			response.sendError(503, "Error while saving product review" + e.getMessage());
		} catch (Exception ignore) {
		}
		
		return null;
	}
}

@RequestMapping(
		value = {
				"/private/products/{id}/reviews/{reviewid}",
				"/auth/products/{id}/reviews/{reviewid}"
		},
		method = RequestMethod.DELETE)
@ResponseStatus(HttpStatus.OK)
@ResponseBody
@ApiImplicitParams({
		@ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
		@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en")
})
public void delete(
		@PathVariable final Long id,
		@PathVariable final Long reviewId,
		@ApiIgnore MerchantStore merchantStore,
		@ApiIgnore Language language,
		HttpServletResponse response) {
	
	try {
		ProductReview prodReview = productReviewService.getById(reviewId);
		if (prodReview == null) {
			response.sendError(404, "Product review with id " + reviewId + " does not exist");
			return;
		}
		
		if (prodReview.getProduct().getId().longValue() != id.longValue()) {
			response.sendError(404, "Product review with id " + reviewId + " does not exist");
			return;
		}
		
		productCommonFacade.deleteReview(prodReview, merchantStore, language);
		
	} catch (Exception e) {
		LOGGER.error("Error while deleting product review", e);
		try {
			response.sendError(503, "Error while deleting product review" + e.getMessage());
		} catch (Exception ignore) {
		}
		
		return;
	}
}
}
