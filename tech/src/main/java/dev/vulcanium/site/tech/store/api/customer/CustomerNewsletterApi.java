package dev.vulcanium.site.tech.store.api.customer;

import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.site.tech.model.customer.PersistableCustomer;
import dev.vulcanium.site.tech.model.customer.optin.PersistableCustomerOptin;
import dev.vulcanium.site.tech.store.facade.customer.CustomerFacade;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import springfox.documentation.annotations.ApiIgnore;


/**
 * Optin a customer to newsletter
 */
@RestController
@RequestMapping(value = "/api", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = { "Optin Customer to newsletter" })
@SwaggerDefinition(tags = { @Tag(name = "Manage customer subscription to newsletter", description = "Manage customer subscription to newsletter") })
public class CustomerNewsletterApi {

@Inject
private CustomerFacade customerFacade;


/** Create new optin */
@PostMapping("/newsletter")
@ApiOperation(
		httpMethod = "POST",
		value = "Creates a newsletter optin",
		notes = "",
		produces = "application/json")
@ApiImplicitParams({
		@ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT"),
		@ApiImplicitParam(name = "lang", dataType = "string", defaultValue = "en")
})
public void create(
		@Valid @RequestBody PersistableCustomerOptin optin,
		@ApiIgnore MerchantStore merchantStore,
		@ApiIgnore Language language) {
	customerFacade.optinCustomer(optin, merchantStore);
}

@PutMapping("/newsletter/{email}")
@ApiOperation(
		httpMethod = "PUT",
		value = "Updates a customer",
		notes = "Requires administration access",
		produces = "application/json",
		response = PersistableCustomer.class)
public void update(
		@PathVariable String email,
		@Valid @RequestBody PersistableCustomer customer,
		HttpServletRequest request,
		HttpServletResponse response) {
	throw new UnsupportedOperationException();
}

@DeleteMapping("/newsletter/{email}")
@ApiOperation(
		httpMethod = "DELETE",
		value = "Deletes a customer",
		notes = "Requires administration access",
		response = Void.class)
public ResponseEntity<Void> delete(
		@PathVariable String email, HttpServletRequest request, HttpServletResponse response) {
	throw new UnsupportedOperationException();
}
}
