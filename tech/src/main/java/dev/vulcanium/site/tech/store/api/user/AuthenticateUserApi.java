package dev.vulcanium.site.tech.store.api.user;

import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import dev.vulcanium.site.tech.store.security.AuthenticationRequest;
import dev.vulcanium.site.tech.store.security.AuthenticationResponse;
import dev.vulcanium.site.tech.store.security.JWTTokenUtil;
import dev.vulcanium.site.tech.store.security.user.JWTUser;

import io.swagger.annotations.Api;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;

/**
 * Authenticates a User (Administration purpose)
 */
@Controller
@RequestMapping("/api")
@Api(tags = { "User authentication Api" })
@SwaggerDefinition(tags = {
		@Tag(name = "User authentication resource", description = "Login for administrator users") })
public class AuthenticateUserApi {

private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticateUserApi.class);

@Value("${authToken.header}")
private String tokenHeader;

@Inject
private AuthenticationManager jwtAdminAuthenticationManager;

@Inject
private UserDetailsService jwtAdminDetailsService;

@Inject
private JWTTokenUtil jwtTokenUtil;

/**
 * Authenticate a user using username & password
 * @param authenticationRequest
 * @param device
 * @return
 * @throws AuthenticationException
 */
@RequestMapping(value = "/private/login", method = RequestMethod.POST)
public ResponseEntity<?> authenticate(@RequestBody @Valid AuthenticationRequest authenticationRequest) throws AuthenticationException {
	Authentication authentication = null;
	try {
		
		authentication = jwtAdminAuthenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						authenticationRequest.getUsername(),
						authenticationRequest.getPassword()
				)
		);
		
	} catch(Exception e) {
		if(e instanceof BadCredentialsException) {
			return new ResponseEntity<>("{\"message\":\"Bad credentials\"}",HttpStatus.UNAUTHORIZED);
		}
		LOGGER.error("Error during authentication " + e.getMessage());
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	if(authentication == null) {
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	SecurityContextHolder.getContext().setAuthentication(authentication);
	
	final JWTUser userDetails = (JWTUser)jwtAdminDetailsService.loadUserByUsername(authenticationRequest.getUsername());
	final String token = jwtTokenUtil.generateToken(userDetails);
	return ResponseEntity.ok(new AuthenticationResponse(userDetails.getId(),token));
	
}

@RequestMapping(value = "/auth/refresh", method = RequestMethod.GET)
public ResponseEntity<AuthenticationResponse> refreshAndGetAuthenticationToken(HttpServletRequest request) {
	String token = request.getHeader(tokenHeader);
	
	if(token != null && token.contains("Bearer")) {
		token = token.substring("Bearer ".length(),token.length());
	}
	
	String username = jwtTokenUtil.getUsernameFromToken(token);
	JWTUser user = (JWTUser) jwtAdminDetailsService.loadUserByUsername(username);
	
	if (jwtTokenUtil.canTokenBeRefreshedWithGrace(token, user.getLastPasswordResetDate())) {
		String refreshedToken = jwtTokenUtil.refreshToken(token);
		return ResponseEntity.ok(new AuthenticationResponse(user.getId(),refreshedToken));
	} else {
		return ResponseEntity.badRequest().body(null);
	}
}
}
