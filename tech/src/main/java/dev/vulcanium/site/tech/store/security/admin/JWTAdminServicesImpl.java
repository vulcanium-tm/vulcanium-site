package dev.vulcanium.site.tech.store.security.admin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import jakarta.inject.Inject;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import dev.vulcanium.business.exception.ServiceException;
import dev.vulcanium.business.services.user.GroupService;
import dev.vulcanium.business.services.user.PermissionService;
import dev.vulcanium.business.services.user.UserService;
import dev.vulcanium.business.model.common.audit.AuditSection;
import dev.vulcanium.business.model.user.Group;
import dev.vulcanium.business.model.user.Permission;
import dev.vulcanium.business.model.user.User;
import dev.vulcanium.site.tech.admin.security.SecurityDataAccessException;
import dev.vulcanium.business.constants.Constants;
import dev.vulcanium.site.tech.store.security.user.JWTUser;

@Service("jwtAdminDetailsService")
public class JWTAdminServicesImpl implements UserDetailsService{

private static final Logger LOGGER = LoggerFactory.getLogger(JWTAdminServicesImpl.class);


@Inject
private UserService userService;
@Inject
private PermissionService  permissionService;
@Inject
private GroupService   groupService;

public final static String ROLE_PREFIX = "ROLE_";


private UserDetails userDetails(String userName, User user, Collection<GrantedAuthority> authorities) {
	
	AuditSection section = null;
	section = user.getAuditSection();
	Date lastModified = null;

	return new JWTUser(
			user.getId(),
			userName,
			user.getFirstName(),
			user.getLastName(),
			user.getAdminEmail(),
			user.getAdminPassword(),
			authorities,
			true,
			lastModified
	);
}

@Override
public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
	User user = null;
	Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
	
	try {
		
		LOGGER.debug("Loading user by user id: {}", userName);
		
		user = userService.getByUserName(userName);
		
		if(user==null) {
			throw new UsernameNotFoundException("User " + userName + " not found");
		}
		
		GrantedAuthority role = new SimpleGrantedAuthority(ROLE_PREFIX + Constants.PERMISSION_AUTHENTICATED);//required to login
		authorities.add(role);
		
		List<Integer> groupsId = new ArrayList<Integer>();
		List<Group> groups = user.getGroups();
		for(Group group : groups) {
			groupsId.add(group.getId());
		}
		
		
		if(CollectionUtils.isNotEmpty(groupsId)) {
			List<Permission> permissions = permissionService.getPermissions(groupsId);
			for(Permission permission : permissions) {
				GrantedAuthority auth = new SimpleGrantedAuthority(permission.getPermissionName());
				authorities.add(auth);
			}
		}
		
		
	} catch (ServiceException e) {
		LOGGER.error("Exception while querrying customer",e);
		throw new SecurityDataAccessException("Cannot authenticate customer",e);
	}
	
	return userDetails(userName, user, authorities);
}

}
