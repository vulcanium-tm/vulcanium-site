package dev.vulcanium.site.tech.store.api.customer;

import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import dev.vulcanium.business.model.customer.Customer;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.business.constants.Constants;
import dev.vulcanium.site.tech.model.customer.PersistableCustomer;
import dev.vulcanium.site.tech.store.api.exception.GenericRuntimeException;
import dev.vulcanium.site.tech.store.api.exception.ResourceNotFoundException;
import dev.vulcanium.site.tech.store.api.exception.UnauthorizedException;
import dev.vulcanium.site.tech.store.facade.customer.CustomerFacade;
import dev.vulcanium.site.tech.store.controller.store.facade.StoreFacade;
import dev.vulcanium.site.tech.store.facade.user.UserFacade;
import dev.vulcanium.site.tech.store.security.AuthenticationRequest;
import dev.vulcanium.site.tech.store.security.AuthenticationResponse;
import dev.vulcanium.site.tech.store.security.JWTTokenUtil;
import dev.vulcanium.site.tech.store.security.PasswordRequest;
import dev.vulcanium.site.tech.store.security.user.JWTUser;
import dev.vulcanium.site.tech.utils.AuthorizationUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/api")
@Api(tags = {"Customer authentication resource (Customer Authentication Api)"})
@SwaggerDefinition(tags = {
		@Tag(name = "Customer authentication resource", description = "Authenticates customer, register customer and reset customer password")
})
public class AuthenticateCustomerApi {

private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticateCustomerApi.class);

@Value("${authToken.header}")
private String tokenHeader;

@Inject
private AuthenticationManager jwtCustomerAuthenticationManager;

@Inject
private JWTTokenUtil jwtTokenUtil;

@Inject
private UserDetailsService jwtCustomerDetailsService;

@Inject
private CustomerFacade customerFacade;

@Inject
private StoreFacade storeFacade;

@Autowired
AuthorizationUtils authorizationUtils;

@Autowired
private UserFacade userFacade;

/**
 * Create new customer for a given MerchantStore, then authenticate that customer
 */
@RequestMapping( value={"/customer/register"}, method=RequestMethod.POST, produces ={ "application/json" })
@ResponseStatus(HttpStatus.CREATED)
@ApiOperation(httpMethod = "POST", value = "Registers a customer to the application", notes = "Used as self-served operation",response = AuthenticationResponse.class)
@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT"),
		@ApiImplicitParam(name = "lang", dataType = "string", defaultValue = "en") })
@ResponseBody
public ResponseEntity<?> register(
		@Valid @RequestBody PersistableCustomer customer,
		@ApiIgnore MerchantStore merchantStore,
		@ApiIgnore Language language) throws Exception {
	
	
	customer.setUserName(customer.getEmailAddress());
	
	if(customerFacade.checkIfUserExists(customer.getUserName(),  merchantStore)) {
		throw new GenericRuntimeException("409", "Customer with email [" + customer.getEmailAddress() + "] is already registered");
	}
	
	Validate.notNull(customer.getUserName(),"Username cannot be null");
	Validate.notNull(customer.getBilling(),"Requires customer Country code");
	Validate.notNull(customer.getBilling().getCountry(),"Requires customer Country code");
	
	customerFacade.registerCustomer(customer, merchantStore, language);
	
	Authentication authentication = null;
	try {
		
		authentication = jwtCustomerAuthenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						customer.getUserName(),
						customer.getPassword()
				)
		);
		
	} catch(Exception e) {
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	if(authentication == null) {
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	SecurityContextHolder.getContext().setAuthentication(authentication);
	
	final JWTUser userDetails = (JWTUser)jwtCustomerDetailsService.loadUserByUsername(customer.getUserName());
	final String token = jwtTokenUtil.generateToken(userDetails);
	
	return ResponseEntity.ok(new AuthenticationResponse(customer.getId(),token));
	
	
}

/**
 * Authenticate a customer using username & password
 * @param authenticationRequest
 * @param device
 * @return
 * @throws AuthenticationException
 */
@RequestMapping(value = "/customer/login", method = RequestMethod.POST, produces ={ "application/json" })
@ApiOperation(httpMethod = "POST", value = "Authenticates a customer to the application", notes = "Customer can authenticate after registration, request is {\"username\":\"admin\",\"password\":\"password\"}",response = ResponseEntity.class)
@ResponseBody
public ResponseEntity<?> authenticate(@RequestBody @Valid AuthenticationRequest authenticationRequest) throws AuthenticationException {
	Authentication authentication = null;
	try {
		authentication = jwtCustomerAuthenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						authenticationRequest.getUsername(),
						authenticationRequest.getPassword()
				)
		);
		
	} catch(BadCredentialsException unn) {
		return new ResponseEntity<>("{\"message\":\"Bad credentials\"}",HttpStatus.UNAUTHORIZED);
	} catch(Exception e) {
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	if(authentication == null) {
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	SecurityContextHolder.getContext().setAuthentication(authentication);
	
	final JWTUser userDetails = (JWTUser)jwtCustomerDetailsService.loadUserByUsername(authenticationRequest.getUsername());
	
	final String token = jwtTokenUtil.generateToken(userDetails);
	
	return ResponseEntity.ok(new AuthenticationResponse(userDetails.getId(),token));
}

@RequestMapping(value = "/auth/customer/refresh", method = RequestMethod.GET, produces ={ "application/json" })
public ResponseEntity<?> refreshToken(HttpServletRequest request) {
	String token = request.getHeader(tokenHeader);
	
	String username = jwtTokenUtil.getUsernameFromToken(token);
	JWTUser user = (JWTUser) jwtCustomerDetailsService.loadUserByUsername(username);
	
	if (jwtTokenUtil.canTokenBeRefreshed(token, user.getLastPasswordResetDate())) {
		String refreshedToken = jwtTokenUtil.refreshToken(token);
		return ResponseEntity.ok(new AuthenticationResponse(user.getId(),refreshedToken));
	} else {
		return ResponseEntity.badRequest().body(null);
	}
}



@RequestMapping(value = "/auth/customer/password", method = RequestMethod.POST, produces ={ "application/json" })
@ApiOperation(httpMethod = "POST", value = "Sends a request to reset password", notes = "Password reset request is {\"username\":\"test@email.com\"}",response = ResponseEntity.class)
public ResponseEntity<?> changePassword(@RequestBody @Valid PasswordRequest passwordRequest, HttpServletRequest request) {
	
	
	try {
		
		MerchantStore merchantStore = storeFacade.getByCode(request);
		
		Customer customer = customerFacade.getCustomerByUserName(passwordRequest.getUsername(), merchantStore);
		
		if(customer == null){
			return ResponseEntity.notFound().build();
		}
		
		if(!customerFacade.passwordMatch(passwordRequest.getCurrent(), customer)) {
			throw new ResourceNotFoundException("Username or password does not match");
		}
		
		if(!passwordRequest.getPassword().equals(passwordRequest.getRepeatPassword())) {
			throw new ResourceNotFoundException("Both passwords do not match");
		}
		
		customerFacade.changePassword(customer, passwordRequest.getPassword());
		return ResponseEntity.ok(Void.class);
		
	} catch(Exception e) {
		return ResponseEntity.badRequest().body("Exception when reseting password "+e.getMessage());
	}
}
}
