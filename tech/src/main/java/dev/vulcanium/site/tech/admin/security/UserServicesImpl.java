package dev.vulcanium.site.tech.admin.security;

import dev.vulcanium.business.services.merchant.MerchantStoreService;
import dev.vulcanium.business.services.user.GroupService;
import dev.vulcanium.business.services.user.PermissionService;
import dev.vulcanium.business.services.user.UserService;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.user.Group;
import dev.vulcanium.business.model.user.GroupType;
import dev.vulcanium.business.model.user.Permission;
import dev.vulcanium.business.constants.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *         http://stackoverflow.com/questions/5105776/spring-security-with
 *         -custom-user-details
 */
@Service("userDetailsService")
public class UserServicesImpl implements WebUserServices{

private static final Logger LOGGER = LoggerFactory.getLogger(UserServicesImpl.class);

private static final String DEFAULT_INITIAL_PASSWORD = "password";

@Inject
private UserService userService;


@Inject
private MerchantStoreService merchantStoreService;

@Inject
@Named("passwordEncoder")
private PasswordEncoder passwordEncoder;



@Inject
protected PermissionService  permissionService;

@Inject
protected GroupService   groupService;

public final static String ROLE_PREFIX = "ROLE_";



public UserDetails loadUserByUsername(String userName)
		throws UsernameNotFoundException, DataAccessException {
	
	dev.vulcanium.business.model.user.User user = null;
	Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
	
	try {
		
		user = userService.getByUserName(userName);
		
		if(user==null) {
			return null;
		}
		
		GrantedAuthority role = new SimpleGrantedAuthority(ROLE_PREFIX + Constants.PERMISSION_AUTHENTICATED);//required to login
		authorities.add(role);
		
		List<Integer> groupsId = new ArrayList<Integer>();
		List<Group> groups = user.getGroups();
		for(Group group : groups) {
			
			
			groupsId.add(group.getId());
			
		}
		
		
		
		List<Permission> permissions = permissionService.getPermissions(groupsId);
		for(Permission permission : permissions) {
			GrantedAuthority auth = new SimpleGrantedAuthority(ROLE_PREFIX + permission.getPermissionName());
			authorities.add(auth);
		}
		
	} catch (Exception e) {
		LOGGER.error("Exception while querrying user",e);
		throw new SecurityDataAccessException("Exception while querrying user",e);
	}
	
	
	
	
	
	User secUser = new User(userName, user.getAdminPassword(), user.isActive(), true,
			true, true, authorities);
	return secUser;
}


public void createDefaultAdmin() throws Exception {
	
	MerchantStore store = merchantStoreService.getByCode(MerchantStore.DEFAULT_STORE);
	
	String password = passwordEncoder.encode(DEFAULT_INITIAL_PASSWORD);
	
	List<Group> groups = groupService.listGroup(GroupType.ADMIN);
	
	dev.vulcanium.business.model.user.User user = new dev.vulcanium.business.model.user.User("admin@shopizer.com",password,"admin@shopizer.com");
	user.setFirstName("Administrator");
	user.setLastName("User");
	
	for(Group group : groups) {
		if(group.getGroupName().equals(Constants.GROUP_SUPERADMIN) || group.getGroupName().equals(Constants.GROUP_ADMIN)) {
			user.getGroups().add(group);
		}
	}
	
	user.setMerchantStore(store);
	userService.create(user);
	
	
}



}
