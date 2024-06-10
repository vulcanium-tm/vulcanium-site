package dev.vulcanium.site.tech.store.api.system;

import java.security.Principal;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.business.constants.Constants;
import dev.vulcanium.site.tech.model.user.ReadableUser;
import dev.vulcanium.site.tech.store.api.exception.RestApiException;
import dev.vulcanium.site.tech.store.api.exception.UnauthorizedException;
import dev.vulcanium.site.tech.store.controller.search.facade.SearchFacade;
import dev.vulcanium.site.tech.store.facade.user.UserFacade;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Searching and indexing products
 */

@Controller
@RequestMapping("/api")
public class SearchToolsApi {

private static final Logger LOGGER = LoggerFactory.getLogger(SearchToolsApi.class);

@Inject
private SearchFacade searchFacade;

@Inject
private UserFacade userFacade;

@PostMapping("/private/system/search/index")
@ApiOperation(httpMethod = "POST", value = "Indexes all products", notes = "", produces = "application/json")
@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
		@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en") })
public ResponseEntity<Void> contact(@ApiIgnore MerchantStore merchantStore, @ApiIgnore Language language, HttpServletRequest request) {
	
	String authenticatedUser = userFacade.authenticatedUser();
	if (authenticatedUser == null) {
		throw new UnauthorizedException();
	}
	
	Principal principal = request.getUserPrincipal();
	String userName = principal.getName();
	ReadableUser user = userFacade.findByUserName(userName, null, language);
	
	if(user== null) {
		throw new UnauthorizedException();
	}
	
	userFacade.authorizedGroup(authenticatedUser, Stream.of(Constants.GROUP_SUPERADMIN, Constants.GROUP_ADMIN,
					Constants.GROUP_ADMIN_CATALOGUE, Constants.GROUP_ADMIN_RETAIL)
			                                              .collect(Collectors.toList()));
	
	if(!user.getMerchant().equals(merchantStore.getCode())) {
		throw new UnauthorizedException();
	}
	try {
		searchFacade.indexAllData(merchantStore);
	} catch (Exception e) {
		throw new RestApiException("Exception while indexing store data", e);
	}
	return new ResponseEntity<Void>(HttpStatus.CREATED);
}

}
