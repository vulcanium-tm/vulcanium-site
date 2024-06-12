package dev.vulcanium.business.utils;

import dev.vulcanium.business.constants.Constants;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.site.tech.store.api.exception.UnauthorizedException;
import dev.vulcanium.site.tech.store.facade.user.UserFacade;
import jakarta.inject.Inject;
import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Component;

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
