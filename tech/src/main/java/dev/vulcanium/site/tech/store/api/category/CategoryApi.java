package dev.vulcanium.site.tech.store.api.category;

import dev.vulcanium.business.constants.Constants;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.business.store.api.exception.UnauthorizedException;
import dev.vulcanium.site.tech.model.catalog.category.PersistableCategory;
import dev.vulcanium.site.tech.model.catalog.category.ReadableCategory;
import dev.vulcanium.site.tech.model.catalog.category.ReadableCategoryList;
import dev.vulcanium.site.tech.model.entity.EntityExists;
import dev.vulcanium.site.tech.model.entity.ListCriteria;
import dev.vulcanium.site.tech.store.facade.category.CategoryFacade;
import dev.vulcanium.site.tech.store.facade.user.UserFacade;
import io.swagger.annotations.*;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api")
@Api(tags = { "Category management resource (Category Management Api)" })
@SwaggerDefinition(tags = {
		@Tag(name = "Category management resource", description = "Manage category and attached products") })
public class CategoryApi {

private static final int DEFAULT_CATEGORY_DEPTH = 0;

@Inject
private CategoryFacade categoryFacade;

@Inject
private UserFacade userFacade;

@GetMapping(value = "/category/{id}", produces = { APPLICATION_JSON_VALUE })
@ApiOperation(httpMethod = "GET", value = "Get category list for an given Category id", notes = "List current Category and child category")
@ApiResponses(value = {
		@ApiResponse(code = 200, message = "List of category found", response = ReadableCategory.class) })
@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT"),
		@ApiImplicitParam(name = "lang", dataType = "string", defaultValue = "en") })
public ReadableCategory get(
		@PathVariable(name = "id") Long categoryId,
		@ApiIgnore MerchantStore merchantStore,
		@ApiIgnore Language language) {
	ReadableCategory category = categoryFacade.getById(merchantStore, categoryId, language);
	return category;
}

@GetMapping(value = "/category/name/{friendlyUrl}", produces = { APPLICATION_JSON_VALUE })
@ApiOperation(httpMethod = "GET", value = "Get category list for an given Category code", notes = "List current Category and child category")
@ApiResponses(value = {
		@ApiResponse(code = 200, message = "List of category found", response = ReadableCategory.class) })
@ApiImplicitParams({
		@ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT"),
		@ApiImplicitParam(name = "lang", dataType = "string", defaultValue = "en")
})
public ReadableCategory getByfriendlyUrl(
		@PathVariable(name = "friendlyUrl") String friendlyUrl,
		@ApiIgnore MerchantStore merchantStore,
		@ApiIgnore Language language) throws Exception {
	ReadableCategory category = categoryFacade.getCategoryByFriendlyUrl(merchantStore, friendlyUrl, language);
	return category;
}

@ResponseStatus(HttpStatus.OK)
@GetMapping(value = { "/private/category/unique" }, produces = MediaType.APPLICATION_JSON_VALUE)
@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT"),
		@ApiImplicitParam(name = "lang", dataType = "string", defaultValue = "en") })
@ApiOperation(httpMethod = "GET", value = "Check if category code already exists", notes = "", response = EntityExists.class)
public ResponseEntity<EntityExists> exists(@RequestParam(value = "code") String code,
                                           @ApiIgnore MerchantStore merchantStore, @ApiIgnore Language language) {
	boolean isCategoryExist = categoryFacade.existByCode(merchantStore, code);
	return new ResponseEntity<EntityExists>(new EntityExists(isCategoryExist), HttpStatus.OK);
}

/**
 * Get all category starting from root filter can be used for filtering on
 * fields only featured is supported
 *
 * @return
 */
@GetMapping(value = "/category", produces = { APPLICATION_JSON_VALUE })
@ApiOperation(httpMethod = "GET", value = "Get category hierarchy from root. Supports filtering FEATURED_CATEGORIES and VISIBLE ONLY by adding ?filter=[featured] or ?filter=[visible] or ? filter=[featured,visible", notes = "Does not return any product attached")
@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT"),
		@ApiImplicitParam(name = "lang", dataType = "string", defaultValue = "en") })
public ReadableCategoryList list(
		@RequestParam(value = "filter", required = false) List<String> filter,
		@RequestParam(value = "name", required = false) String name,
		@ApiIgnore MerchantStore merchantStore,
		@ApiIgnore Language language,
		@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
		@RequestParam(value = "count", required = false, defaultValue = "10") Integer count) {
	
	
	ListCriteria criteria = new ListCriteria();
	criteria.setName(name);
	return categoryFacade.getCategoryHierarchy(merchantStore, criteria, DEFAULT_CATEGORY_DEPTH, language, filter,
			page, count);
}


@GetMapping(value = "/category/product/{ProductId}", produces = { APPLICATION_JSON_VALUE })
@ApiOperation(httpMethod = "GET", value = "Get category by product", notes = "")
@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT"),
		@ApiImplicitParam(name = "lang", dataType = "string", defaultValue = "en") })
public ReadableCategoryList list(
		@PathVariable(name = "ProductId") Long id,
		@ApiIgnore MerchantStore merchantStore,
		@ApiIgnore Language lang) {
	
	
	return categoryFacade.listByProduct(merchantStore, id, lang);
	
}

@ResponseStatus(HttpStatus.CREATED)
@PostMapping(value = "/private/category", produces = { APPLICATION_JSON_VALUE })
@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT"),
		@ApiImplicitParam(name = "lang", dataType = "string", defaultValue = "en") })
public PersistableCategory create(
		@Valid @RequestBody PersistableCategory category,
		@ApiIgnore MerchantStore merchantStore,
		@ApiIgnore Language language) {
	String authenticatedUser = userFacade.authenticatedUser();
	if (authenticatedUser == null) {
		throw new UnauthorizedException();
	}
	
	userFacade.authorizedGroup(authenticatedUser, Stream.of(Constants.GROUP_SUPERADMIN, Constants.GROUP_ADMIN, Constants.GROUP_ADMIN_CATALOGUE, Constants.GROUP_ADMIN_RETAIL).collect(Collectors.toList()));
	
	return categoryFacade.saveCategory(merchantStore, category);
}

@PutMapping(value = "/private/category/{id}", produces = { APPLICATION_JSON_VALUE })
@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT") })
public PersistableCategory update(@PathVariable Long id, @Valid @RequestBody PersistableCategory category,
                                  @ApiIgnore MerchantStore merchantStore) {
	String authenticatedUser = userFacade.authenticatedUser();
	if (authenticatedUser == null) {
		throw new UnauthorizedException();
	}
	
	userFacade.authorizedGroup(authenticatedUser, Stream.of(Constants.GROUP_SUPERADMIN, Constants.GROUP_ADMIN, Constants.GROUP_ADMIN_CATALOGUE, Constants.GROUP_ADMIN_RETAIL).collect(Collectors.toList()));
	
	
	category.setId(id);
	return categoryFacade.saveCategory(merchantStore, category);
}

@PatchMapping(value = "/private/category/{id}/visible", produces = { APPLICATION_JSON_VALUE })
@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT") })
public void updateVisible(@PathVariable Long id, @Valid @RequestBody PersistableCategory category,
                          @ApiIgnore MerchantStore merchantStore
) {
	String authenticatedUser = userFacade.authenticatedUser();
	if (authenticatedUser == null) {
		throw new UnauthorizedException();
	}
	
	userFacade.authorizedGroup(authenticatedUser, Stream.of(Constants.GROUP_SUPERADMIN, Constants.GROUP_ADMIN, Constants.GROUP_ADMIN_CATALOGUE, Constants.GROUP_ADMIN_RETAIL).collect(Collectors.toList()));
	
	category.setId(id);
	categoryFacade.setVisible(category, merchantStore);
}

@PutMapping(value = "/private/category/{id}/move/{parent}", produces = { APPLICATION_JSON_VALUE })
@ApiOperation(httpMethod = "PUT", value = "Move a category under another category", notes = "Move category {id} under category {parent}")
@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT") })
public void move(
		@PathVariable Long id,
		@PathVariable Long parent,
		@ApiIgnore MerchantStore merchantStore) {
	String authenticatedUser = userFacade.authenticatedUser();
	if (authenticatedUser == null) {
		throw new UnauthorizedException();
	}
	
	userFacade.authorizedGroup(authenticatedUser, Stream.of(Constants.GROUP_SUPERADMIN, Constants.GROUP_ADMIN, Constants.GROUP_ADMIN_CATALOGUE, Constants.GROUP_ADMIN_RETAIL).collect(Collectors.toList()));
	
	
	categoryFacade.move(id, parent, merchantStore);
	return;
}

@DeleteMapping(value = "/private/category/{id}", produces = { APPLICATION_JSON_VALUE })
@ResponseStatus(OK)
public void delete(@PathVariable("id") Long categoryId, @ApiIgnore MerchantStore merchantStore) {
	
	String authenticatedUser = userFacade.authenticatedUser();
	if (authenticatedUser == null) {
		throw new UnauthorizedException();
	}
	
	userFacade.authorizedGroup(authenticatedUser, Stream.of(Constants.GROUP_SUPERADMIN, Constants.GROUP_ADMIN, Constants.GROUP_ADMIN_CATALOGUE, Constants.GROUP_ADMIN_RETAIL).collect(Collectors.toList()));
	
	
	categoryFacade.deleteCategory(categoryId, merchantStore);
}

}