package dev.vulcanium.site.tech.store.api.tax;

import dev.vulcanium.business.model.entity.Entity;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.site.tech.model.entity.EntityExists;
import dev.vulcanium.site.tech.model.entity.ReadableEntityList;
import dev.vulcanium.site.tech.model.tax.PersistableTaxRate;
import dev.vulcanium.site.tech.model.tax.ReadableTaxRate;
import dev.vulcanium.site.tech.store.facade.tax.TaxFacade;
import io.swagger.annotations.*;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Tax class management
 */

@RestController
@RequestMapping(value = "/api")
@Api(tags = { "Tax rates management resource (Tax rates management Api)" })
@SwaggerDefinition(tags = { @Tag(name = "Tax rates management resource", description = "Manage tax rates") })
public class TaxRatesApi {

private static final Logger LOGGER = LoggerFactory.getLogger(TaxRatesApi.class);

@Autowired
private TaxFacade taxFacade;

/** Create new tax rate for a given MerchantStore */
@PostMapping("/private/tax/rate")
@ApiOperation(httpMethod = "POST", value = "Creates a taxRate", notes = "Requires administration access", produces = "application/json", response = Entity.class)
@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT") })
public Entity create(@ApiIgnore MerchantStore merchantStore, @ApiIgnore Language language,
                     @Valid @RequestBody PersistableTaxRate taxRate) {
	
	return taxFacade.createTaxRate(taxRate, merchantStore, language);
	
}

@GetMapping(value = "/private/tax/rate/unique", produces = MediaType.APPLICATION_JSON_VALUE)
@ApiOperation(httpMethod = "GET", value = "Verify if taxRate is unique", notes = "", produces = "application/json", response = ResponseEntity.class)
@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
		@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en") })
public ResponseEntity<EntityExists> exists(@RequestParam String code, @ApiIgnore MerchantStore merchantStore,
                                           @ApiIgnore Language language) {
	
	boolean exists = taxFacade.existsTaxRate(code, merchantStore, language);
	return new ResponseEntity<EntityExists>(new EntityExists(exists), HttpStatus.OK);
	
}

/** Update tax rate for a given MerchantStore */
@PutMapping("/private/tax/rate/{id}")
@ApiOperation(httpMethod = "PUT", value = "Updates a taxRate", notes = "Requires administration access", produces = "application/json", response = Void.class)
@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT") })
public void update(@ApiIgnore MerchantStore merchantStore, @PathVariable Long id, @ApiIgnore Language language,
                   @Valid @RequestBody PersistableTaxRate taxRate) {
	
	taxRate.setId(id);
	taxFacade.updateTaxRate(id, taxRate, merchantStore, language);
	
}

@GetMapping(value = "/private/tax/rates", produces = MediaType.APPLICATION_JSON_VALUE)
@ApiOperation(httpMethod = "GET", value = "List taxRates by store", notes = "", produces = "application/json", response = ReadableEntityList.class)
@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
		@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en") })
public ReadableEntityList<ReadableTaxRate> list(@RequestParam(name = "count", defaultValue = "10") int count,
                                                @RequestParam(name = "page", defaultValue = "0") int page, @ApiIgnore MerchantStore merchantStore,
                                                @ApiIgnore Language language) {
	
	return taxFacade.taxRates(merchantStore, language);
	
}

@GetMapping("/private/tax/rate/{id}")
@ApiOperation(httpMethod = "GET", value = "Get a taxRate by code", notes = "Requires administration access", produces = "application/json", response = Void.class)
@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT") })
public ReadableTaxRate get(@ApiIgnore MerchantStore merchantStore, @PathVariable Long id, @ApiIgnore Language language) {
	
	return taxFacade.taxRate(id, merchantStore, language);
	
}

@DeleteMapping(value = "/private/tax/rate/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
@ApiOperation(httpMethod = "DELETE", value = "Delete tax rate", notes = "", produces = "application/json", response = Void.class)
@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
		@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en") })
public void delete(@PathVariable Long id, @ApiIgnore MerchantStore merchantStore, @ApiIgnore Language language) {
	
	taxFacade.deleteTaxRate(id, merchantStore, language);
	
}

}
