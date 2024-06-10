package dev.vulcanium.site.tech.store.api.system;

import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.site.tech.model.system.PersistableOptin;
import dev.vulcanium.site.tech.model.system.ReadableOptin;
import dev.vulcanium.site.tech.store.controller.optin.facade.OptinFacade;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

/** Optin a customer to events such s newsletter */
@RestController
@RequestMapping("/api")
public class OptinApi {

private static final Logger LOGGER = LoggerFactory.getLogger(OptinApi.class);

@Inject private OptinFacade optinFacade;


/** Create new optin */
@PostMapping("/private/optin")
@ApiOperation(
		httpMethod = "POST",
		value = "Creates an optin event type definition",
		notes = "",
		produces = "application/json")
@ApiImplicitParams({
		@ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
		@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en")
})
public ReadableOptin create(
		@Valid @RequestBody PersistableOptin optin,
		@ApiIgnore MerchantStore merchantStore,
		@ApiIgnore Language language,
		HttpServletRequest request) {
	LOGGER.debug("[" + request.getUserPrincipal().getName() + "] creating optin [" + optin.getCode() + "]");
	return optinFacade.create(optin, merchantStore, language);
}
}
