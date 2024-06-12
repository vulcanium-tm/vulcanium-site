package dev.vulcanium.site.tech.store.api.product;

import dev.vulcanium.business.model.entity.Entity;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.site.tech.model.catalog.product.PersistableProductPrice;
import dev.vulcanium.site.tech.model.catalog.product.ReadableProductPrice;
import dev.vulcanium.site.tech.store.facade.product.ProductPriceFacade;
import io.swagger.annotations.*;
import jakarta.validation.Valid;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Use inventory
 */

@Controller
@RequestMapping("/api")
@Api(tags = { "Product price api" })
@SwaggerDefinition(tags = { @Tag(name = "Product price management", description = "Edit price and discount") })
public class ProductPriceApi {


@Autowired
private ProductPriceFacade productPriceFacade;;


private static final Logger LOGGER = LoggerFactory.getLogger(ProductApi.class);

@ResponseStatus(HttpStatus.OK)
@RequestMapping(value = { "/private/product/{sku}/inventory/{inventoryId}/price"},
		method = RequestMethod.POST)
@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
		@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en") })
public @ResponseBody Entity save(
		@PathVariable String sku,
		@PathVariable Long inventoryId,
		@Valid @RequestBody PersistableProductPrice price,
		@ApiIgnore MerchantStore merchantStore,
		@ApiIgnore Language language) {
	
	price.setSku(sku);
	price.setProductAvailabilityId(inventoryId);
	
	Long id = productPriceFacade.save(price, merchantStore);
	return new Entity(id);
	
	
}

@ResponseStatus(HttpStatus.CREATED)
@RequestMapping(value = { "/private/product/{sku}/price"},
		method = RequestMethod.POST)
@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
		@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en") })
public @ResponseBody Entity save(
		@PathVariable String sku,
		@Valid @RequestBody PersistableProductPrice price,
		@ApiIgnore MerchantStore merchantStore,
		@ApiIgnore Language language) {
	
	price.setSku(sku);
	
	Long id = productPriceFacade.save(price, merchantStore);
	return new Entity(id);
	
	
}

@ResponseStatus(HttpStatus.OK)
@RequestMapping(value = { "/private/product/{sku}/inventory/{inventoryId}/price/{priceId}"},
		method = RequestMethod.PUT)
@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
		@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en") })
public void edit(
		@PathVariable String sku,
		@PathVariable Long inventoryId,
		@PathVariable Long priceId,
		@Valid @RequestBody PersistableProductPrice price,
		@ApiIgnore MerchantStore merchantStore,
		@ApiIgnore Language language) {
	
	
	price.setSku(sku);
	price.setProductAvailabilityId(inventoryId);
	price.setId(priceId);
	productPriceFacade.save(price, merchantStore);
	
	
	
}

@ResponseStatus(HttpStatus.OK)
@RequestMapping(value = { "/private/product/{sku}/price/{priceId}"},
		method = RequestMethod.GET)
@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
		@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en") })
public ReadableProductPrice get(
		@PathVariable String sku,
		@PathVariable Long priceId,
		@Valid @RequestBody PersistableProductPrice price,
		@ApiIgnore MerchantStore merchantStore,
		@ApiIgnore Language language) {
	
	
	price.setSku(sku);
	price.setId(priceId);
	
	return productPriceFacade.get(sku, priceId, merchantStore, language);
	
}

@RequestMapping(value = { "/private/product/{sku}/inventory/{inventoryId}/price"},
		method = RequestMethod.GET)
@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
		@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en") })
public List<ReadableProductPrice> list(
		@PathVariable String sku,
		@PathVariable Long inventoryId,
		@ApiIgnore MerchantStore merchantStore,
		@ApiIgnore Language language) {
	
	
	return productPriceFacade.list(sku, inventoryId, merchantStore, language);
	
	
}


@RequestMapping(value = { "/private/product/{sku}/prices"},
		method = RequestMethod.GET)
@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
		@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en") })
public List<ReadableProductPrice> list(
		@PathVariable String sku,
		@ApiIgnore MerchantStore merchantStore,
		@ApiIgnore Language language) {
	
	
	return productPriceFacade.list(sku, merchantStore, language);
	
	
}

@ResponseStatus(HttpStatus.OK)
@RequestMapping(value = { "/private/product/{sku}/price/{priceId}"},
		method = RequestMethod.DELETE)
@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
		@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en") })
public void delete(
		@PathVariable String sku,
		@PathVariable Long priceId,
		@ApiIgnore MerchantStore merchantStore,
		@ApiIgnore Language language) {
	
	
	productPriceFacade.delete(priceId, sku, merchantStore);
	
}

}
