package dev.vulcanium.site.tech.store.api.product;

import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.business.store.api.exception.RestApiException;
import dev.vulcanium.site.tech.model.catalog.product.inventory.PersistableInventory;
import dev.vulcanium.site.tech.model.catalog.product.inventory.ReadableInventory;
import dev.vulcanium.site.tech.model.entity.ReadableEntityList;
import dev.vulcanium.site.tech.store.facade.product.ProductInventoryFacade;
import io.swagger.annotations.*;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@Controller
@RequestMapping("/api")
@Api(tags = { "Product inventory resource (Product Inventory Api)" })
@SwaggerDefinition(tags = {
		@Tag(name = "Product inventory resource", description = "Manage inventory for a given product") })
public class ProductInventoryApi {

@Autowired
private ProductInventoryFacade productInventoryFacade;

private static final Logger LOGGER = LoggerFactory.getLogger(ProductInventoryApi.class);

@ResponseStatus(HttpStatus.CREATED)
@RequestMapping(value = { "/private/product/{productId}/inventory" }, method = RequestMethod.POST)
@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
		@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en") })
public @ResponseBody ReadableInventory create(@PathVariable Long productId,
                                              @Valid @RequestBody PersistableInventory inventory, @ApiIgnore MerchantStore merchantStore,
                                              @ApiIgnore Language language) {
	inventory.setProductId(productId);
	return productInventoryFacade.add(inventory, merchantStore, language);
}

@ResponseStatus(HttpStatus.OK)
@RequestMapping(value = { "/private/product/{productId}/inventory/{id}" }, method = RequestMethod.PUT)
@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
		@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en") })
public void update(
		@PathVariable Long productId,
		@PathVariable Long id,
		@Valid @RequestBody PersistableInventory inventory, @ApiIgnore MerchantStore merchantStore,
		@ApiIgnore Language language) {
	inventory.setId(id);
	inventory.setProductId(inventory.getProductId());
	inventory.setVariant(inventory.getVariant());
	inventory.setProductId(productId);
	productInventoryFacade.update(inventory, merchantStore, language);
	
}

@ResponseStatus(HttpStatus.OK)
@RequestMapping(value = { "/private/product/{productId}/inventory/{id}" }, method = RequestMethod.DELETE)
@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
		@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en") })
public void delete(
		@PathVariable Long productId,
		@PathVariable Long id,
		@ApiIgnore MerchantStore merchantStore,
		@ApiIgnore Language language) {
	
	productInventoryFacade.delete(productId, id, merchantStore);
	
}

@ResponseStatus(HttpStatus.OK)
@GetMapping(value = { "/private/product/{sku}/inventory" })
@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
		@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en") })
public @ResponseBody ReadableEntityList<ReadableInventory> getBySku(
		@PathVariable String sku,
		@ApiIgnore MerchantStore merchantStore,
		@ApiIgnore Language language,
		@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
		@RequestParam(value = "count", required = false, defaultValue = "10") Integer count) {
	
	return productInventoryFacade.get(sku, merchantStore, language, page, count);
	
}

@ResponseStatus(HttpStatus.OK)
@GetMapping(value = { "/private/product/inventory" })
@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
		@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en") })
public @ResponseBody ReadableEntityList<ReadableInventory> getByProductId(
		@RequestParam Long productId,
		@ApiIgnore MerchantStore merchantStore,
		@ApiIgnore Language language,
		@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
		@RequestParam(value = "count", required = false, defaultValue = "10") Integer count) {
	
	if(productId == null) {
		throw new RestApiException("Requires request parameter product id [/product/inventoty?productId");
	}
	
	return productInventoryFacade.get(productId, merchantStore, language, page, count);
	
}

}
