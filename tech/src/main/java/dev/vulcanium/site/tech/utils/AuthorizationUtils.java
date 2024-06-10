package dev.vulcanium.site.tech.utils;

import java.util.Arrays;
import java.util.List;

import jakarta.inject.Inject;

import org.springframework.stereotype.Component;

import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.constants.Constants;
import dev.vulcanium.site.tech.store.api.exception.UnauthorizedException;
import dev.vulcanium.site.tech.store.facade.user.UserFacade;

/**
 * Performs authorization check for REST Api
 * - check if user is in role
 * - check if user can perform actions on marchant
 */
@Component
public class AuthorizationUtils {

@Inject
private UserFacade userFacade;

public String authenticatedUser() {
	String authenticatedUser = userFacade.authenticatedUser();
	if (authenticatedUser == null) {
		throw new UnauthorizedException();
	}
	return authenticatedUser;
}

public void authorizeUser(String authenticatedUser, List<String> roles, MerchantStore store) {
	userFacade.authorizedGroup(authenticatedUser, roles);
	if (!userFacade.userInRoles(authenticatedUser, Arrays.asList(Constants.GROUP_SUPERADMIN))) {
		if (!userFacade.authorizedStore(authenticatedUser, store.getCode())) {
			throw new UnauthorizedException("Operation unauthorized for user [" + authenticatedUser
					                                + "] and store [" + store.getCode() + "]");
		}
	}
}

}
