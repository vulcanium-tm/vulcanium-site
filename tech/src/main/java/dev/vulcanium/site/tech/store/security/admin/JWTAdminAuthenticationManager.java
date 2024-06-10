package dev.vulcanium.site.tech.store.security.admin;

import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import dev.vulcanium.site.tech.store.security.JWTTokenUtil;
import dev.vulcanium.site.tech.store.security.common.CustomAuthenticationException;
import dev.vulcanium.site.tech.store.security.common.CustomAuthenticationManager;
import static java.util.Optional.ofNullable;
import static org.apache.commons.lang3.StringUtils.removeStart;
import io.jsonwebtoken.ExpiredJwtException;

@Component("jwtCustomAdminAuthenticationManager")
public class JWTAdminAuthenticationManager extends CustomAuthenticationManager {

protected final Log logger = LogFactory.getLog(getClass());
private static final String BEARER = "Bearer";

@Inject
private JWTTokenUtil jwtTokenUtil;

@Inject
private UserDetailsService jwtAdminDetailsService;

@Override
public Authentication attemptAuthentication(HttpServletRequest request,
                                            HttpServletResponse response) throws AuthenticationException {
	
	final String requestHeader = request.getHeader(super.getTokenHeader());// token
	String username = null;
	final String authToken;
	
	authToken = ofNullable(requestHeader).map(value -> removeStart(value, BEARER)).map(String::trim)
			            .orElseThrow(() -> new CustomAuthenticationException("Missing Authentication Token"));
	
	try {
		username = jwtTokenUtil.getUsernameFromToken(authToken);
	} catch (IllegalArgumentException e) {
		logger.error("an error occured during getting username from token", e);
	} catch (ExpiredJwtException e) {
		logger.warn("the token is expired and not valid anymore", e);
	}
	
	
	UsernamePasswordAuthenticationToken authentication = null;
	
	logger.info("checking authentication for user " + username);
	if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
		
		UserDetails userDetails = this.jwtAdminDetailsService.loadUserByUsername(username);
		
		if (userDetails != null && jwtTokenUtil.validateToken(authToken, userDetails)) {
			authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
					userDetails.getAuthorities());
			authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			logger.info("authenticated user " + username + ", setting security context");
		}
	}
	
	return authentication;
}

@Override
public void successfullAuthentication(HttpServletRequest request, HttpServletResponse response,
                                      Authentication authentication) throws AuthenticationException {
	
}

@Override
public void unSuccessfullAuthentication(HttpServletRequest request, HttpServletResponse response)
		throws AuthenticationException {
	
}

}
