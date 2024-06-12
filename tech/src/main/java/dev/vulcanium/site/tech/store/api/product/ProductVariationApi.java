package dev.vulcanium.site.tech.store.api.product;

import dev.vulcanium.business.model.catalog.product.Product;
import dev.vulcanium.business.model.catalog.product.attribute.ProductAttribute;
import dev.vulcanium.business.model.catalog.product.price.FinalPrice;
import dev.vulcanium.business.model.entity.Entity;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.business.services.catalog.pricing.PricingService;
import dev.vulcanium.business.services.catalog.product.ProductService;
import dev.vulcanium.business.utils.ImageFilePath;
import dev.vulcanium.site.tech.model.catalog.product.ReadableProductPrice;
import dev.vulcanium.site.tech.model.catalog.product.attribute.ReadableProductVariant;
import dev.vulcanium.site.tech.model.catalog.product.attribute.ReadableProductVariantValue;
import dev.vulcanium.site.tech.model.catalog.product.attribute.ReadableSelectedProductVariant;
import dev.vulcanium.site.tech.model.catalog.product.variation.PersistableProductVariation;
import dev.vulcanium.site.tech.model.catalog.product.variation.ReadableProductVariation;
import dev.vulcanium.site.tech.model.entity.EntityExists;
import dev.vulcanium.site.tech.model.entity.ReadableEntityList;
import dev.vulcanium.site.tech.populator.catalog.ReadableFinalPricePopulator;
import dev.vulcanium.site.tech.store.facade.category.CategoryFacade;
import dev.vulcanium.site.tech.store.facade.product.ProductVariationFacade;
import io.swagger.annotations.*;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

/**
 * API to manage product variant
 *
 * The flow is the following
 *
 * - create a product definition
 * - create a product variant
 */
@Controller
@RequestMapping("/api/v2")
@Api(tags = {"Product variation resource (Product variant Api)"})
@SwaggerDefinition(tags = {
		@Tag(name = "Product variation resource", description = "List variations of products by different grouping")
})
public class ProductVariationApi {


@Inject private PricingService pricingService;

@Inject private ProductService productService;

@Inject private CategoryFacade categoryFacade;

@Inject private ProductVariationFacade productVariationFacade;



@Inject
@Qualifier("img")
private ImageFilePath imageUtils;


private static final Logger LOGGER = LoggerFactory.getLogger(ProductVariationApi.class);

/**
 * Calculates the price based on selected options if any
 * @param id
 * @param options
 * @param merchantStore
 * @param language
 * @param response
 * @return
 * @throws Exception
 */
@RequestMapping(value = "/product/{id}/variation", method = RequestMethod.POST)
@ResponseStatus(HttpStatus.OK)
@ApiOperation(
		httpMethod = "POST",
		value = "Get product price variation based on selected product",
		notes = "",
		produces = "application/json",
		response = ReadableProductPrice.class)
@ResponseBody
@ApiImplicitParams({
		@ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
		@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en")
})
public ReadableProductPrice calculateVariant(
		@PathVariable final Long id,
		@RequestBody ReadableSelectedProductVariant options,
		@ApiIgnore MerchantStore merchantStore,
		@ApiIgnore Language language,
		HttpServletResponse response)
		throws Exception {
	
	Product product = productService.getById(id);
	
	if (product == null) {
		response.sendError(404, "Product not fount for id " + id);
		return null;
	}
	
	List<ReadableProductVariantValue> ids = options.getOptions();
	
	if (CollectionUtils.isEmpty(ids)) {
		return null;
	}
	
	List<ReadableProductVariantValue> variants = options.getOptions();
	List<ProductAttribute> attributes = new ArrayList<ProductAttribute>();
	
	Set<ProductAttribute> productAttributes = product.getAttributes();
	for(ProductAttribute attribute : productAttributes) {
		Long option = attribute.getProductOption().getId();
		Long optionValue = attribute.getProductOptionValue().getId();
		for(ReadableProductVariantValue v : variants) {
			if(v.getOption().longValue() == option.longValue()
					   && v.getValue().longValue() == optionValue.longValue()) {
				attributes.add(attribute);
			}
		}
		
	}
	
	FinalPrice price = pricingService.calculateProductPrice(product, attributes);
	ReadableProductPrice readablePrice = new ReadableProductPrice();
	ReadableFinalPricePopulator populator = new ReadableFinalPricePopulator();
	populator.setPricingService(pricingService);
	populator.populate(price, readablePrice, merchantStore, language);
	return readablePrice;
}


@RequestMapping(value = "/category/{id}/variations", method = RequestMethod.GET)
@ResponseStatus(HttpStatus.OK)
@ApiOperation(
		httpMethod = "GET",
		value = "Get all variation for all items in a given category",
		notes = "",
		produces = "application/json",
		response = List.class)
@ResponseBody
@ApiImplicitParams({
		@ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
		@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en")
})
public List<ReadableProductVariant> categoryVariantList(
		@PathVariable final Long id, //category id
		@ApiIgnore MerchantStore merchantStore,
		@ApiIgnore Language language,
		HttpServletResponse response)
		throws Exception {
	
	return categoryFacade.categoryProductVariants(id, merchantStore, language);
	
}

@ResponseStatus(HttpStatus.CREATED)
@RequestMapping(value = { "/private/product/variation" }, method = RequestMethod.POST)
@ApiOperation(
		httpMethod = "POST",
		value = "Creates a new product variant",
		notes = "",
		produces = "application/json",
		response = Void.class)
@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
		@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en") })
public @ResponseBody Entity create(
		@Valid @RequestBody PersistableProductVariation variation,
		@ApiIgnore MerchantStore merchantStore,
		@ApiIgnore Language language) {
	
	Long variantId = productVariationFacade.create(variation, merchantStore, language);
	return new Entity(variantId);
	
}

@ResponseStatus(HttpStatus.OK)
@GetMapping(value = { "/private/product/variation/unique" }, produces = MediaType.APPLICATION_JSON_VALUE)
@ApiImplicitParams({
		@ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT"),
		@ApiImplicitParam(name = "lang", dataType = "string", defaultValue = "en") })
@ApiOperation(httpMethod = "GET", value = "Check if option set code already exists", notes = "", response = EntityExists.class)
public ResponseEntity<EntityExists> exists(
		@RequestParam(value = "code") String code,
		@ApiIgnore MerchantStore merchantStore,
		@ApiIgnore Language language) {
	
	boolean isOptionExist = productVariationFacade.exists(code, merchantStore);
	return new ResponseEntity<EntityExists>(new EntityExists(isOptionExist), HttpStatus.OK);
}


@ResponseStatus(HttpStatus.OK)
@RequestMapping(value = { "/private/product/variation/{variationId}" }, method = RequestMethod.GET)
@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
		@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en") })
@ResponseBody
public ReadableProductVariation get(
		@PathVariable Long variationId,
		@ApiIgnore MerchantStore merchantStore,
		@ApiIgnore Language language) {
	
	return productVariationFacade.get(variationId, merchantStore, language);
	
}


@ResponseStatus(HttpStatus.OK)
@RequestMapping(value = { "/private/product/variation/{variationId}" }, method = RequestMethod.PUT)
@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
		@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en") })
public void update(
		@Valid @RequestBody PersistableProductVariation variation,
		@PathVariable Long variationId,
		@ApiIgnore MerchantStore merchantStore,
		@ApiIgnore Language language) {
	
	variation.setId(variationId);
	productVariationFacade.update(variationId, variation, merchantStore, language);
	
}


@ResponseStatus(HttpStatus.OK)
@RequestMapping(value = { "/private/product/variation/{variationId}" }, method = RequestMethod.DELETE)
@ApiImplicitParams({
		@ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
		@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en") })
public void delete(
		@PathVariable Long variationId,
		@ApiIgnore MerchantStore merchantStore,
		@ApiIgnore Language language) {
	
	productVariationFacade.delete(variationId, merchantStore);
	
}


@ResponseStatus(HttpStatus.OK)
@RequestMapping(value = { "/private/product/variations" }, method = RequestMethod.GET)
@ApiImplicitParams({
		@ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
		@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en") })
public @ResponseBody ReadableEntityList<ReadableProductVariation> list(
		@ApiIgnore MerchantStore merchantStore,
		@ApiIgnore Language language,
		@RequestParam(value = "page", required = false, defaultValue="0") Integer page,
		@RequestParam(value = "count", required = false, defaultValue="10") Integer count) {
	
	return productVariationFacade.list(merchantStore, language, page, count);
	
	
}

}
