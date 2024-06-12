package dev.vulcanium.site.tech.store.api.product;

import dev.vulcanium.business.model.entity.Entity;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.site.tech.model.catalog.product.type.PersistableProductType;
import dev.vulcanium.site.tech.model.catalog.product.type.ReadableProductType;
import dev.vulcanium.site.tech.model.catalog.product.type.ReadableProductTypeList;
import dev.vulcanium.site.tech.model.entity.EntityExists;
import dev.vulcanium.site.tech.store.facade.product.ProductTypeFacade;
import io.swagger.annotations.*;
import jakarta.inject.Inject;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

/**
 * API to create, read, update and delete a Product API to create Manufacturer
 */
@RestController
@RequestMapping("/api")
@Api(tags = { "Product type resource (Product Type Api)" })
@SwaggerDefinition(tags = { @Tag(name = "Product type resource", description = "Manage product types") })
public class ProductTypeApi {

@Inject
private ProductTypeFacade productTypeFacade;

private static final Logger LOGGER = LoggerFactory.getLogger(ProductTypeApi.class);

@GetMapping(value = "/private/product/types", produces = MediaType.APPLICATION_JSON_VALUE)
@ApiOperation(httpMethod = "GET", value = "Get product types list", notes = "", produces = "application/json", response = List.class)
@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
		@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en") })
public ReadableProductTypeList list(@RequestParam(name = "count", defaultValue = "10") int count,
                                    @RequestParam(name = "page", defaultValue = "0") int page, @ApiIgnore MerchantStore merchantStore,
                                    @ApiIgnore Language language) {
	
	return productTypeFacade.getByMerchant(merchantStore, language, count, page);
	
}

@GetMapping(value = "/private/product/type/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
@ApiOperation(httpMethod = "GET", value = "Get product type", notes = "", produces = "application/json", response = ReadableProductType.class)
@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
		@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en") })
public ReadableProductType get(@PathVariable Long id, @ApiIgnore MerchantStore merchantStore,
                               @ApiIgnore Language language) {
	
	return productTypeFacade.get(merchantStore, id, language);
	
}

@GetMapping(value = "/private/product/type/unique", produces = MediaType.APPLICATION_JSON_VALUE)
@ApiOperation(httpMethod = "GET", value = "Verify if product type is unique", notes = "", produces = "application/json", response = ResponseEntity.class)
@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
		@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en") })
public ResponseEntity<EntityExists> exists(@RequestParam String code, @ApiIgnore MerchantStore merchantStore,
                                           @ApiIgnore Language language) {
	
	boolean exists = productTypeFacade.exists(code, merchantStore, language);
	return new ResponseEntity<EntityExists>(new EntityExists(exists), HttpStatus.OK);
	
}

@PostMapping(value = "/private/product/type", produces = MediaType.APPLICATION_JSON_VALUE)
@ApiOperation(httpMethod = "POST", value = "Create product type", notes = "", produces = "application/json", response = Entity.class)
@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
		@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en") })
public Entity create(@RequestBody PersistableProductType type, @ApiIgnore MerchantStore merchantStore,
                     @ApiIgnore Language language) {
	
	Long id = productTypeFacade.save(type, merchantStore, language);
	Entity entity = new Entity();
	entity.setId(id);
	return entity;
	
}

@PutMapping(value = "/private/product/type/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
@ApiOperation(httpMethod = "PUT", value = "Update product type", notes = "", produces = "application/json", response = Void.class)
@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
		@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en") })
public void update(@RequestBody PersistableProductType type, @PathVariable Long id,
                   @ApiIgnore MerchantStore merchantStore, @ApiIgnore Language language) {
	
	productTypeFacade.update(type, id, merchantStore, language);
	
}

@DeleteMapping(value = "/private/product/type/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
@ApiOperation(httpMethod = "DELETE", value = "Delete product type", notes = "", produces = "application/json", response = Void.class)
@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
		@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en") })
public void delete(@PathVariable Long id, @ApiIgnore MerchantStore merchantStore, @ApiIgnore Language language) {
	
	productTypeFacade.delete(id, merchantStore, language);
	
}

}
