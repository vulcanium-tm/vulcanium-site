package dev.vulcanium.site.tech.store.api.customer;

import dev.vulcanium.business.constants.Constants;
import dev.vulcanium.business.model.customer.Customer;
import dev.vulcanium.business.model.customer.CustomerCriteria;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.business.store.api.exception.ResourceNotFoundException;
import dev.vulcanium.business.store.api.exception.ServiceRuntimeException;
import dev.vulcanium.business.store.api.exception.UnauthorizedException;
import dev.vulcanium.site.tech.model.customer.PersistableCustomer;
import dev.vulcanium.site.tech.model.customer.ReadableCustomer;
import dev.vulcanium.site.tech.populator.customer.ReadableCustomerList;
import dev.vulcanium.site.tech.store.facade.customer.CustomerFacade;
import dev.vulcanium.site.tech.store.facade.user.UserFacade;
import io.swagger.annotations.*;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.security.Principal;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping(value = "/api")
@Api(tags = { "Customer management resource (Customer Management Api)" })
@SwaggerDefinition(tags = { @Tag(name = "Customer management resource", description = "Manage customers") })
public class CustomerApi {

private static final Logger LOGGER = LoggerFactory.getLogger(CustomerApi.class);

@Inject
private CustomerFacade customerFacade;

@Autowired
private UserFacade userFacade;

/** Create new customer for a given MerchantStore */
@PostMapping("/private/customer")
@ApiOperation(httpMethod = "POST", value = "Creates a customer", notes = "Requires administration access", produces = "application/json", response = ReadableCustomer.class)
@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT") })
public ReadableCustomer create(@ApiIgnore MerchantStore merchantStore, @ApiIgnore Language language,
                               @Valid @RequestBody PersistableCustomer customer) {
	return customerFacade.create(customer, merchantStore, language);
	
}

@PutMapping("/private/customer/{id}")
@ApiOperation(httpMethod = "PUT", value = "Updates a customer", notes = "Requires administration access", produces = "application/json", response = PersistableCustomer.class)
@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT") })
public PersistableCustomer update(@PathVariable Long id, @ApiIgnore MerchantStore merchantStore,
                                  @Valid @RequestBody PersistableCustomer customer) {
	
	customer.setId(id);
	return customerFacade.update(customer, merchantStore);
}

@PatchMapping("/private/customer/{id}/address")
@ApiOperation(httpMethod = "PATCH", value = "Updates a customer", notes = "Requires administration access", produces = "application/json", response = Void.class)
@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT") })
public void updateAddress(@PathVariable Long id, @ApiIgnore MerchantStore merchantStore,
                          @RequestBody PersistableCustomer customer) {
	
	customer.setId(id);
	customerFacade.updateAddress(customer, merchantStore);
}

@DeleteMapping("/private/customer/{id}")
@ApiOperation(httpMethod = "DELETE", value = "Deletes a customer", notes = "Requires administration access")
@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT") })
public void delete(@PathVariable Long id, @ApiIgnore MerchantStore merchantStore) {
	
	String authenticatedUser = userFacade.authenticatedUser();
	if (authenticatedUser == null) {
		throw new UnauthorizedException();
	}
	
	userFacade.authorizedGroup(authenticatedUser, Stream.of(Constants.GROUP_SUPERADMIN, Constants.GROUP_ADMIN, Constants.GROUP_ADMIN_RETAIL).collect(Collectors.toList()));
	
	
	customerFacade.deleteById(id);
}

/**
 * Get all customers
 *
 * @param start
 * @param count
 * @param request
 * @return
 * @throws Exception
 */
@GetMapping("/private/customers")
@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT"),
		@ApiImplicitParam(name = "lang", dataType = "string", defaultValue = "en") })
public ReadableCustomerList list(@RequestParam(value = "page", required = false) Integer page,
                                 @RequestParam(value = "count", required = false) Integer count, @ApiIgnore MerchantStore merchantStore,
                                 @ApiIgnore Language language) {
	CustomerCriteria customerCriteria = createCustomerCriteria(page, count);
	return customerFacade.getListByStore(merchantStore, customerCriteria, language);
}

private CustomerCriteria createCustomerCriteria(Integer start, Integer count) {
	CustomerCriteria customerCriteria = new CustomerCriteria();
	Optional.ofNullable(start).ifPresent(customerCriteria::setStartIndex);
	Optional.ofNullable(count).ifPresent(customerCriteria::setMaxCount);
	return customerCriteria;
}

@GetMapping("/private/customer/{id}")
@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT"),
		@ApiImplicitParam(name = "lang", dataType = "string", defaultValue = "en") })
public ReadableCustomer get(@PathVariable Long id, @ApiIgnore MerchantStore merchantStore,
                            @ApiIgnore Language language) {
	return customerFacade.getCustomerById(id, merchantStore, language);
}

/**
 * Get logged in customer profile
 *
 * @param merchantStore
 * @param language
 * @param request
 * @return
 */
@GetMapping({ "/private/customer/profile", "/auth/customer/profile" })
@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT"),
		@ApiImplicitParam(name = "lang", dataType = "string", defaultValue = "en") })
public ReadableCustomer getAuthUser(@ApiIgnore MerchantStore merchantStore, @ApiIgnore Language language,
                                    HttpServletRequest request) {
	Principal principal = request.getUserPrincipal();
	String userName = principal.getName();
	return customerFacade.getCustomerByNick(userName, merchantStore, language);
}

@PatchMapping("/auth/customer/address")
@ApiOperation(httpMethod = "PATCH", value = "Updates a loged in customer address", notes = "Requires authentication", produces = "application/json", response = Void.class)
@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT") })
public void updateAuthUserAddress(@ApiIgnore MerchantStore merchantStore, @RequestBody PersistableCustomer customer,
                                  HttpServletRequest request) {
	Principal principal = request.getUserPrincipal();
	String userName = principal.getName();
	
	customerFacade.updateAddress(userName, customer, merchantStore);
	
}

@PatchMapping("/auth/customer/")
@ApiOperation(httpMethod = "PATCH", value = "Updates a loged in customer profile", notes = "Requires authentication", produces = "application/json", response = PersistableCustomer.class)
@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT") })
public PersistableCustomer update(@ApiIgnore MerchantStore merchantStore,
                                  @Valid @RequestBody PersistableCustomer customer, HttpServletRequest request) {
	
	Principal principal = request.getUserPrincipal();
	String userName = principal.getName();
	
	return customerFacade.update(userName, customer, merchantStore);
}

@DeleteMapping("/auth/customer/")
@ApiOperation(httpMethod = "DELETE", value = "Deletes a loged in customer profile", notes = "Requires authentication", produces = "application/json", response = Void.class)
@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT") })
public void delete(@ApiIgnore MerchantStore merchantStore, @ApiIgnore Language language,
                   HttpServletRequest request) {
	
	Principal principal = request.getUserPrincipal();
	String userName = principal.getName();
	
	Customer customer;
	try {
		customer = customerFacade.getCustomerByUserName(userName, merchantStore);
		if(customer == null) {
			throw new ResourceNotFoundException("Customer [" + userName + "] not found");
		}
		customerFacade.delete(customer);
	} catch (Exception e) {
		throw new ServiceRuntimeException("An error occured while deleting customer ["+userName+"]");
	}
	
	
}
}