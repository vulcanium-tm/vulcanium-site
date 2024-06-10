package dev.vulcanium.site.tech.store.security;

import java.io.IOException;
import java.util.Enumeration;

import jakarta.inject.Inject;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.filter.OncePerRequestFilter;

import dev.vulcanium.business.model.common.UserContext;
import dev.vulcanium.site.tech.store.security.common.CustomAuthenticationManager;
import dev.vulcanium.site.tech.utils.GeoLocationUtils;


public class AuthenticationTokenFilter extends OncePerRequestFilter {


private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationTokenFilter.class);


@Value("${authToken.header}")
private String tokenHeader;

private final static String BEARER_TOKEN ="Bearer ";

private final static String FACEBOOK_TOKEN ="FB ";

@Inject
private CustomAuthenticationManager jwtCustomCustomerAuthenticationManager;

@Inject
private CustomAuthenticationManager jwtCustomAdminAuthenticationManager;

@Override
protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
	
	
	String origin = "*";
	if(!StringUtils.isBlank(request.getHeader("origin"))) {
		origin = request.getHeader("origin");
	}
	response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE, PATCH");
	response.setHeader("Access-Control-Allow-Origin", origin);
	response.setHeader("Access-Control-Allow-Headers", "X-Auth-Token, Content-Type, Authorization, Cache-Control, X-Requested-With");
	response.setHeader("Access-Control-Allow-Credentials", "true");
	
	try {
		
		String ipAddress = GeoLocationUtils.getClientIpAddress(request);
		
		UserContext userContext = UserContext.create();
		userContext.setIpAddress(ipAddress);
		
	} catch(Exception s) {
		LOGGER.error("Error while getting ip address ", s);
	}
	
	String requestUrl = request.getRequestURL().toString();
	
	
	if(requestUrl.contains("/api/auth")) {
		final String requestHeader = request.getHeader(this.tokenHeader);//token
		
		try {
			if (requestHeader != null && requestHeader.startsWith(BEARER_TOKEN)) {//Bearer
				
				jwtCustomCustomerAuthenticationManager.authenticateRequest(request, response);
				
			} else if(requestHeader != null && requestHeader.startsWith(FACEBOOK_TOKEN)) {
			} else {
				LOGGER.warn("couldn't find any authorization token, will ignore the header");
			}
			
		} catch(Exception e) {
			throw new ServletException(e);
		}
	}
	
	
	if(requestUrl.contains("/api/private") || requestUrl.contains("/api/private")) {
		
		Enumeration<String> headers = request.getHeaderNames();
		final String requestHeader = request.getHeader(this.tokenHeader);//token
		
		try {
			if (requestHeader != null && requestHeader.startsWith(BEARER_TOKEN)) {//Bearer
				
				jwtCustomAdminAuthenticationManager.authenticateRequest(request, response);
				
			} else {
				LOGGER.warn("couldn't find any authorization token, will ignore the header, might be a preflight check");
			}
			
		} catch(Exception e) {
			throw new ServletException(e);
		}
	}
	
	chain.doFilter(request, response);
	postFilter(request, response, chain);
}


private void postFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
	
	try {
		
		UserContext userContext = UserContext.getCurrentInstance();
		if(userContext!=null) {
			userContext.close();
		}
		
	} catch(Exception s) {
		LOGGER.error("Error while getting ip address ", s);
	}
	
}

}
