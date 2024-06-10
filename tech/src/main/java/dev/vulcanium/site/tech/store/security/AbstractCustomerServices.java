package dev.vulcanium.site.tech.store.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import dev.vulcanium.business.exception.ServiceException;
import dev.vulcanium.business.services.customer.CustomerService;
import dev.vulcanium.business.services.user.GroupService;
import dev.vulcanium.business.services.user.PermissionService;
import dev.vulcanium.business.model.customer.Customer;
import dev.vulcanium.business.model.user.Group;
import dev.vulcanium.business.model.user.Permission;
import dev.vulcanium.site.tech.admin.security.SecurityDataAccessException;
import dev.vulcanium.business.constants.Constants;

public abstract class AbstractCustomerServices implements UserDetailsService{

private static final Logger LOGGER = LoggerFactory.getLogger(AbstractCustomerServices.class);

protected CustomerService customerService;
protected PermissionService  permissionService;
protected GroupService   groupService;

public final static String ROLE_PREFIX = "ROLE_";//Spring Security 4

public AbstractCustomerServices(
		CustomerService customerService,
		PermissionService permissionService,
		GroupService groupService) {
	
	this.customerService = customerService;
	this.permissionService = permissionService;
	this.groupService = groupService;
}

protected abstract UserDetails userDetails(String userName, Customer customer, Collection<GrantedAuthority> authorities);


public UserDetails loadUserByUsername(String userName)
		throws UsernameNotFoundException, DataAccessException {
	Customer user = null;
	Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
	
	try {
		
		LOGGER.debug("Loading user by user id: {}", userName);
		
		user = customerService.getByNick(userName);
		
		if(user==null) {
			//return null;
			throw new UsernameNotFoundException("User " + userName + " not found");
		}
		
		
		
		GrantedAuthority role = new SimpleGrantedAuthority(ROLE_PREFIX + Constants.PERMISSION_CUSTOMER_AUTHENTICATED);//required to login
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
