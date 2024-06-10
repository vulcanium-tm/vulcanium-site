package dev.vulcanium.site.tech.store.api.product;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.business.constants.Constants;
import dev.vulcanium.site.tech.model.catalog.product.variantgroup.PersistableProductVariantGroup;
import dev.vulcanium.site.tech.model.catalog.product.variantgroup.ReadableProductVariantGroup;
import dev.vulcanium.site.tech.model.entity.Entity;
import dev.vulcanium.site.tech.model.entity.ReadableEntityList;
import dev.vulcanium.site.tech.store.api.exception.UnauthorizedException;
import dev.vulcanium.site.tech.store.facade.product.ProductVariantGroupFacade;
import dev.vulcanium.site.tech.store.facade.user.UserFacade;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import springfox.documentation.annotations.ApiIgnore;

@Controller
@RequestMapping("/api")
@Api(tags = { "Product instances group api" })
@SwaggerDefinition(tags = {
		@Tag(name = "Product instances group allows attaching property and images to a group of instances", description = "Manage product instances group") })
public class ProductVariantGroupApi {

@Autowired
private ProductVariantGroupFacade productVariantGroupFacade;

@Autowired
private UserFacade userFacade;

@ResponseStatus(HttpStatus.CREATED)
@PostMapping(value = { "/private/product/productVariantGroup" })
@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT"),
		@ApiImplicitParam(name = "lang", dataType = "string", defaultValue = "en") })
public @ResponseBody Entity create(
		@Valid @RequestBody PersistableProductVariantGroup instanceGroup,
		@ApiIgnore MerchantStore merchantStore, @ApiIgnore Language language) {
	
	String authenticatedUser = userFacade.authenticatedUser();
	if (authenticatedUser == null) {
		throw new UnauthorizedException();
	}
	
	userFacade.authorizedGroup(authenticatedUser, Stream.of(Constants.GROUP_SUPERADMIN, Constants.GROUP_ADMIN,
			Constants.GROUP_ADMIN_CATALOGUE, Constants.GROUP_ADMIN_RETAIL).collect(Collectors.toList()));
	
	Long id = productVariantGroupFacade.create(instanceGroup, merchantStore, language);
	
	return new Entity(id);
	
}

@ResponseStatus(HttpStatus.OK)
@PutMapping(value = { "/private/product/productVariantGroup/{id}" })
@ApiOperation(httpMethod = "PUT", value = "Update product instance group", notes = "", produces = "application/json", response = Void.class)
public @ResponseBody void update(@PathVariable Long id,
                                 @Valid @RequestBody PersistableProductVariantGroup instance,
                                 @ApiIgnore MerchantStore merchantStore,
                                 @ApiIgnore Language language) {
	
	String authenticatedUser = userFacade.authenticatedUser();
	if (authenticatedUser == null) {
		throw new UnauthorizedException();
	}
	
	userFacade.authorizedGroup(authenticatedUser, Stream.of(Constants.GROUP_SUPERADMIN, Constants.GROUP_ADMIN,
			Constants.GROUP_ADMIN_CATALOGUE, Constants.GROUP_ADMIN_RETAIL).collect(Collectors.toList()));
	
	productVariantGroupFacade.update(id, instance, merchantStore, language);
}

@ResponseStatus(HttpStatus.OK)
@GetMapping(value = { "/private/product/productVariantGroup/{id}" })
@ApiOperation(httpMethod = "GET", value = "Get product instance group", notes = "", produces = "application/json", response = Void.class)
public @ResponseBody ReadableProductVariantGroup get(
		@PathVariable Long id, @ApiIgnore MerchantStore merchantStore,
		@ApiIgnore Language language) {
	
	String authenticatedUser = userFacade.authenticatedUser();
	if (authenticatedUser == null) {
		throw new UnauthorizedException();
	}
	
	userFacade.authorizedGroup(authenticatedUser, Stream.of(Constants.GROUP_SUPERADMIN, Constants.GROUP_ADMIN,
			Constants.GROUP_ADMIN_CATALOGUE, Constants.GROUP_ADMIN_RETAIL).collect(Collectors.toList()));
	
	return productVariantGroupFacade.get(id, merchantStore, language);
}

@ResponseStatus(HttpStatus.OK)
@DeleteMapping(value = { "/private/product/productVariantGroup/{id}" })
@ApiOperation(httpMethod = "DELETE", value = "Delete product instance group", notes = "", produces = "application/json", response = Void.class)
public @ResponseBody void delete(@PathVariable Long id, @ApiIgnore MerchantStore merchantStore,
                                 @ApiIgnore Language language) {
	
	String authenticatedUser = userFacade.authenticatedUser();
	if (authenticatedUser == null) {
		throw new UnauthorizedException();
	}
	
	userFacade.authorizedGroup(authenticatedUser, Stream.of(Constants.GROUP_SUPERADMIN, Constants.GROUP_ADMIN,
			Constants.GROUP_ADMIN_CATALOGUE, Constants.GROUP_ADMIN_RETAIL).collect(Collectors.toList()));
	
	productVariantGroupFacade.delete(id, id, merchantStore);
}

@ResponseStatus(HttpStatus.OK)
@GetMapping(value = { "/private/product/{id}/productVariantGroup" })
@ApiOperation(httpMethod = "GET", value = "Delete product instance group", notes = "", produces = "application/json", response = Void.class)
public @ResponseBody ReadableEntityList<ReadableProductVariantGroup> list(
		@PathVariable final Long id,
		@ApiIgnore MerchantStore merchantStore,
		@ApiIgnore Language language,
		@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
		@RequestParam(value = "count", required = false, defaultValue = "10") Integer count) {
	
	String authenticatedUser = userFacade.authenticatedUser();
	if (authenticatedUser == null) {
		throw new UnauthorizedException();
	}
	
	userFacade.authorizedGroup(authenticatedUser, Stream.of(Constants.GROUP_SUPERADMIN, Constants.GROUP_ADMIN,
			Constants.GROUP_ADMIN_CATALOGUE, Constants.GROUP_ADMIN_RETAIL).collect(Collectors.toList()));
	
	return productVariantGroupFacade.list(id, merchantStore, language, page, count);
}

@ResponseStatus(HttpStatus.CREATED)
@RequestMapping(value = { "/private/product/productVariantGroup/{id}/image" }, consumes = {
		MediaType.MULTIPART_FORM_DATA_VALUE }, method = RequestMethod.POST)
@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
		@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en") })
public void addImage(
		@PathVariable Long id,
		@RequestParam(value = "file", required = true) MultipartFile file,
		@RequestParam(value = "order", required = false, defaultValue = "0") Integer position,
		@ApiIgnore MerchantStore merchantStore,
		@ApiIgnore Language language) {
	
	String authenticatedUser = userFacade.authenticatedUser();
	if (authenticatedUser == null) {
		throw new UnauthorizedException();
	}
	
	userFacade.authorizedGroup(authenticatedUser, Stream.of(Constants.GROUP_SUPERADMIN, Constants.GROUP_ADMIN,
			Constants.GROUP_ADMIN_CATALOGUE, Constants.GROUP_ADMIN_RETAIL).collect(Collectors.toList()));
	
	productVariantGroupFacade.addImage(file, id, merchantStore, language);
	
}

@ResponseStatus(HttpStatus.OK)
@RequestMapping(value = {
		"/private/product/productVariantGroup/{id}/image/{imageId}" }, method = RequestMethod.DELETE)
public void removeImage(@PathVariable Long id, @PathVariable Long imageId, @ApiIgnore MerchantStore merchantStore,
                        @ApiIgnore Language language) {
	
	String authenticatedUser = userFacade.authenticatedUser();
	if (authenticatedUser == null) {
		throw new UnauthorizedException();
	}
	
	userFacade.authorizedGroup(authenticatedUser, Stream.of(Constants.GROUP_SUPERADMIN, Constants.GROUP_ADMIN,
			Constants.GROUP_ADMIN_CATALOGUE, Constants.GROUP_ADMIN_RETAIL).collect(Collectors.toList()));
	
	productVariantGroupFacade.removeImage(imageId, id, merchantStore);
	
}

}
