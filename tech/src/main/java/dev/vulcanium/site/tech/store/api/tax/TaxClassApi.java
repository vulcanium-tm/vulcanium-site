package dev.vulcanium.site.tech.store.api.tax;

import dev.vulcanium.business.model.entity.Entity;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.site.tech.model.entity.EntityExists;
import dev.vulcanium.site.tech.model.entity.ReadableEntityList;
import dev.vulcanium.site.tech.model.tax.PersistableTaxClass;
import dev.vulcanium.site.tech.model.tax.ReadableTaxClass;
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
@Api(tags = { "Tax class management resource (Tax class management Api)" })
@SwaggerDefinition(tags = { @Tag(name = "Tax class management resource", description = "Manage tax classes") })
public class TaxClassApi {

private static final Logger LOGGER = LoggerFactory.getLogger(TaxClassApi.class);

@Autowired
private TaxFacade taxFacade;

/** Create new tax class for a given MerchantStore */
@PostMapping("/private/tax/class")
@ApiOperation(httpMethod = "POST", value = "Creates a taxClass", notes = "Requires administration access", produces = "application/json", response = Entity.class)
@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT") })
public Entity create(@ApiIgnore MerchantStore merchantStore, @ApiIgnore Language language,
                     @Valid @RequestBody PersistableTaxClass taxClass) {
	
	return taxFacade.createTaxClass(taxClass, merchantStore, language);
	
}

@GetMapping(value = "/private/tax/class/unique", produces = MediaType.APPLICATION_JSON_VALUE)
@ApiOperation(httpMethod = "GET", value = "Verify if taxClass is unique", notes = "", produces = "application/json", response = ResponseEntity.class)
@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
		@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en") })
public ResponseEntity<EntityExists> exists(@RequestParam String code, @ApiIgnore MerchantStore merchantStore,
                                           @ApiIgnore Language language) {
	
	boolean exists = taxFacade.existsTaxClass(code, merchantStore, language);
	return new ResponseEntity<EntityExists>(new EntityExists(exists), HttpStatus.OK);
	
}

/** Update tax class for a given MerchantStore */
@PutMapping("/private/tax/class/{id}")
@ApiOperation(httpMethod = "PUT", value = "Updates a taxClass", notes = "Requires administration access", produces = "application/json", response = Void.class)
@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT") })
public void update(@ApiIgnore MerchantStore merchantStore, @PathVariable Long id, @ApiIgnore Language language,
                   @Valid @RequestBody PersistableTaxClass taxClass) {
	
	taxClass.setId(id);
	taxFacade.updateTaxClass(id, taxClass, merchantStore, language);
	
}

@GetMapping(value = "/private/tax/class", produces = MediaType.APPLICATION_JSON_VALUE)
@ApiOperation(httpMethod = "GET", value = "List taxClasses by store", notes = "", produces = "application/json", response = ReadableEntityList.class)
@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
		@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en") })
public ReadableEntityList<ReadableTaxClass> list(@RequestParam(name = "count", defaultValue = "10") int count,
                                                 @RequestParam(name = "page", defaultValue = "0") int page, @ApiIgnore MerchantStore merchantStore,
                                                 @ApiIgnore Language language) {
	
	return taxFacade.taxClasses(merchantStore, language);
	
}

@GetMapping("/private/tax/class/{code}")
@ApiOperation(httpMethod = "GET", value = "Get a taxClass by code", notes = "Requires administration access", produces = "application/json", response = Void.class)
@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT") })
public ReadableTaxClass get(@ApiIgnore MerchantStore merchantStore, @PathVariable String code, @ApiIgnore Language language) {
	
	return taxFacade.taxClass(code, merchantStore, language);
	
}

@DeleteMapping(value = "/private/tax/class/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
@ApiOperation(httpMethod = "DELETE", value = "Delete tax class", notes = "", produces = "application/json", response = Void.class)
@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
		@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en") })
public void delete(@PathVariable Long id, @ApiIgnore MerchantStore merchantStore, @ApiIgnore Language language) {
	
	taxFacade.deleteTaxClass(id, merchantStore, language);
	
}

}
