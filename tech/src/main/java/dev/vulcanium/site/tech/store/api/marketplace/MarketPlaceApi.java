package dev.vulcanium.site.tech.store.api.marketplace;

import jakarta.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.site.tech.model.marketplace.ReadableMarketPlace;
import dev.vulcanium.site.tech.model.marketplace.SignupStore;
import dev.vulcanium.site.tech.model.user.ReadableUser;
import dev.vulcanium.site.tech.store.api.exception.OperationNotAllowedException;
import dev.vulcanium.site.tech.store.api.exception.ResourceNotFoundException;
import dev.vulcanium.site.tech.store.controller.marketplace.facade.MarketPlaceFacade;
import dev.vulcanium.site.tech.store.controller.store.facade.StoreFacade;
import dev.vulcanium.site.tech.store.facade.user.UserFacade;
import dev.vulcanium.site.tech.utils.LanguageUtils;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/api")
public class MarketPlaceApi {

@Autowired
private MarketPlaceFacade marketPlaceFacade;

@Autowired
private UserFacade userFacade;

@Inject
private StoreFacade storeFacade;

@Inject
private LanguageUtils languageUtils;

/**
 * Get a marketplace from storeCode returns market place details and
 * merchant store
 */
@GetMapping("/private/marketplace/{store}")
@ApiOperation(httpMethod = "GET", value = "Get market place meta-data", notes = "", produces = "application/json", response = ReadableMarketPlace.class)
public ReadableMarketPlace marketPlace(@PathVariable String store,
                                       @RequestParam(value = "lang", required = false) String lang) {
	
	Language language = languageUtils.getServiceLanguage(lang);
	return marketPlaceFacade.get(store, language);
}

@PostMapping("/store/signup")
@ApiOperation(httpMethod = "POST", value = "Signup store", notes = "", produces = "application/json", response = Void.class)
public void signup(@RequestBody SignupStore store, @ApiIgnore Language language) {
	
	ReadableUser user = null;
	try {
		user = userFacade.findByUserName(store.getEmail());
		
	} catch (ResourceNotFoundException ignore) {
	}
	
	if (user != null) {
		throw new OperationNotAllowedException(
				"User [" + store.getEmail() + "] already exist and cannot be registered");
	}
	
	if (storeFacade.existByCode(store.getCode())) {
		throw new OperationNotAllowedException(
				"Store [" + store.getCode() + "] already exist and cannot be registered");
	}
	
}

@ResponseStatus(HttpStatus.OK)
@GetMapping(value = { "/store/{store}/signup/{token}" }, produces = MediaType.APPLICATION_JSON_VALUE)
@ApiOperation(httpMethod = "GET", value = "Validate store signup token", notes = "", response = Void.class)
@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
		@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en") })
public void storeSignupVerify(@PathVariable String store, @PathVariable String token,
                              @ApiIgnore MerchantStore merchantStore,
                              @ApiIgnore Language language) {
	
	/**
	 * Receives signup token. Needs to validate if a store
	 * to validate if token has expired
	 *
	 * If no problem void is returned otherwise throw OperationNotAllowed
	 */
}
}
