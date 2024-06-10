package dev.vulcanium.site.tech.store.controller.security.facade;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import jakarta.inject.Inject;

import org.jsoup.helper.Validate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import dev.vulcanium.business.exception.ServiceException;
import dev.vulcanium.business.services.user.GroupService;
import dev.vulcanium.business.services.user.PermissionService;
import dev.vulcanium.business.model.user.Group;
import dev.vulcanium.business.model.user.PermissionCriteria;
import dev.vulcanium.business.model.user.PermissionList;
import dev.vulcanium.site.tech.model.security.ReadablePermission;
import dev.vulcanium.site.tech.store.api.exception.ServiceRuntimeException;

@Service("securityFacade")
public class SecurityFacadeImpl implements SecurityFacade {

private static final String USER_PASSWORD_PATTERN = "((?=.*[a-z])(?=.*\\d)(?=.*[A-Z]).{6,12})";

private Pattern userPasswordPattern = Pattern.compile(USER_PASSWORD_PATTERN);

@Inject
private PermissionService permissionService;

@Inject
private GroupService groupService;

@Inject
private PasswordEncoder passwordEncoder;

@SuppressWarnings({"rawtypes", "unchecked"})
@Override
public List<ReadablePermission> getPermissions(List<String> groups) {
	
	List<Group> userGroups = null;
	try {
		userGroups = groupService.listGroupByNames(groups);
		
		List<Integer> ids = new ArrayList<Integer>();
		for (Group g : userGroups) {
			ids.add(g.getId());
		}
		
		PermissionCriteria criteria = new PermissionCriteria();
		criteria.setGroupIds(new HashSet(ids));
		
		PermissionList permissions = permissionService.listByCriteria(criteria);
		throw new ServiceRuntimeException("Not implemented");
	} catch (ServiceException e) {
		e.printStackTrace();
	}
	
	return null;
}

@Override
public boolean validateUserPassword(String password) {
	
	Matcher matcher = userPasswordPattern.matcher(password);
	return matcher.matches();
}

@Override
public String encodePassword(String password) {
	return passwordEncoder.encode(password);
}

/**
 * Match non encoded to encoded
 * Don't use this as a simple raw password check
 */
@Override
public boolean matchPassword(String modelPassword, String newPassword) {
	return passwordEncoder.matches(newPassword, modelPassword);
}

@Override
public boolean matchRawPasswords(String password, String repeatPassword) {
	Validate.notNull(password,"password is null");
	Validate.notNull(repeatPassword,"repeat password is null");
	return password.equals(repeatPassword);
}



}
