package dev.vulcanium.site.tech.store.api.product;

import dev.vulcanium.business.constants.Constants;
import dev.vulcanium.business.model.entity.Entity;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.business.store.api.exception.UnauthorizedException;
import dev.vulcanium.site.tech.model.catalog.product.variant.PersistableProductVariant;
import dev.vulcanium.site.tech.model.catalog.product.variant.ReadableProductVariant;
import dev.vulcanium.site.tech.model.entity.EntityExists;
import dev.vulcanium.site.tech.model.entity.ReadableEntityList;
import dev.vulcanium.site.tech.store.facade.product.ProductVariantFacade;
import dev.vulcanium.site.tech.store.facade.user.UserFacade;
import io.swagger.annotations.*;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Api to manage productVariant
 *
 * Product variant allows to specify product
 * size, sku and options related to this product variant
 */
@Controller
@RequestMapping("/api")
@Api(tags = { "Product variants api" })
@SwaggerDefinition(tags = {
		@Tag(name = "Product variants resource", description = "Manage inventory for a given product") })
public class ProductVariantApi {

private static final Logger LOGGER = LoggerFactory.getLogger(ProductVariantApi.class);

@Autowired
private ProductVariantFacade productVariantFacade;

@Inject
private UserFacade userFacade;

@ResponseStatus(HttpStatus.CREATED)
@PostMapping(value = { "/private/product/{productId}/variant" })
@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT"),
		@ApiImplicitParam(name = "lang", dataType = "string", defaultValue = "en") })
public @ResponseBody Entity create(
		@Valid @RequestBody PersistableProductVariant variant,
		@PathVariable Long productId,
		@ApiIgnore MerchantStore merchantStore,
		@ApiIgnore Language language) {
	
	String authenticatedUser = userFacade.authenticatedUser();
	if (authenticatedUser == null) {
		throw new UnauthorizedException();
	}
	
	userFacade.authorizedGroup(authenticatedUser, Stream.of(Constants.GROUP_SUPERADMIN, Constants.GROUP_ADMIN,
			Constants.GROUP_ADMIN_CATALOGUE, Constants.GROUP_ADMIN_RETAIL).collect(Collectors.toList()));
	
	Long id = productVariantFacade.create(variant, productId, merchantStore, language);
	return new Entity(id);
	
}


@ResponseStatus(HttpStatus.OK)
@PutMapping(value = { "/private/product/{id}/variant/{variantId}" })
@ApiOperation(httpMethod = "PUT", value = "Update product variant", notes = "", produces = "application/json", response = Void.class)
public @ResponseBody void update(@PathVariable Long id, @PathVariable Long variantId,
                                 @Valid @RequestBody PersistableProductVariant variant, @ApiIgnore MerchantStore merchantStore,
                                 @ApiIgnore Language language) {
	
	String authenticatedUser = userFacade.authenticatedUser();
	if (authenticatedUser == null) {
		throw new UnauthorizedException();
	}
	
	userFacade.authorizedGroup(authenticatedUser, Stream.of(Constants.GROUP_SUPERADMIN, Constants.GROUP_ADMIN,
			Constants.GROUP_ADMIN_CATALOGUE, Constants.GROUP_ADMIN_RETAIL).collect(Collectors.toList()));
	
	productVariantFacade.update(variantId, variant, id, merchantStore, language);
}

@ResponseStatus(HttpStatus.OK)
@GetMapping(value = { "/private/product/{id}/variant/{sku}/unique" }, produces = "application/json")
@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT"),
		@ApiImplicitParam(name = "lang", dataType = "string", defaultValue = "en") })
@ApiOperation(httpMethod = "GET", value = "Check if option set code already exists", notes = "", response = EntityExists.class)
public @ResponseBody ResponseEntity<EntityExists> exists(
		@PathVariable Long id,
		@PathVariable String sku,
		@ApiIgnore MerchantStore merchantStore,
		@ApiIgnore Language language) {
	
	String authenticatedUser = userFacade.authenticatedUser();
	if (authenticatedUser == null) {
		throw new UnauthorizedException();
	}
	
	userFacade.authorizedGroup(authenticatedUser, Stream.of(Constants.GROUP_SUPERADMIN, Constants.GROUP_ADMIN,
			Constants.GROUP_ADMIN_CATALOGUE, Constants.GROUP_ADMIN_RETAIL).collect(Collectors.toList()));
	
	boolean exist = productVariantFacade.exists(sku, merchantStore, id, language);
	return new ResponseEntity<EntityExists>(new EntityExists(exist), HttpStatus.OK);
	
}

@GetMapping(value = "/private/product/{id}/variant/{variantId}", produces = "application/json")
@ApiOperation(httpMethod = "GET", value = "Get a productVariant by id", notes = "For administration and shop purpose. Specifying ?merchant is required otherwise it falls back to DEFAULT")
@ApiResponses(value = {
		@ApiResponse(code = 200, message = "Single product found", response = ReadableProductVariant.class) })
@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
		@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en") })
public @ResponseBody ReadableProductVariant get(
		@PathVariable final Long id,
		@PathVariable Long variantId,
		@RequestParam(value = "lang", required = false) String lang,
		@ApiIgnore MerchantStore merchantStore,
		@ApiIgnore Language language) throws Exception {
	
	return productVariantFacade.get(variantId, id, merchantStore, language);
	
}

@ResponseStatus(HttpStatus.OK)
@RequestMapping(value = { "/private/product/{id}/variants" }, method = RequestMethod.GET)
@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
		@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en") })
public @ResponseBody ReadableEntityList<ReadableProductVariant> list(@PathVariable final Long id,
                                                                     @ApiIgnore MerchantStore merchantStore, @ApiIgnore Language language,
                                                                     @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                                                     @RequestParam(value = "count", required = false, defaultValue = "10") Integer count) {
	
	return productVariantFacade.list(id, merchantStore, language, page, count);
	
}

@ResponseStatus(HttpStatus.OK)
@RequestMapping(value = { "/private/product/{id}/variant/{variantId}" }, method = RequestMethod.DELETE)
@ApiImplicitParams({
		@ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
		@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en") })
public void delete(
		@PathVariable Long id,
		@PathVariable Long variantId,
		@ApiIgnore MerchantStore merchantStore,
		@ApiIgnore Language language) {
	
	productVariantFacade.delete(variantId, id, merchantStore);
	
	
}

}
