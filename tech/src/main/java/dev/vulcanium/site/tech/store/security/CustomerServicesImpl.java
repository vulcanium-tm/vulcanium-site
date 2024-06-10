package dev.vulcanium.site.tech.store.security;

import java.util.Collection;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import dev.vulcanium.business.services.customer.CustomerService;
import dev.vulcanium.business.services.user.GroupService;
import dev.vulcanium.business.services.user.PermissionService;
import dev.vulcanium.business.model.customer.Customer;
import dev.vulcanium.site.tech.store.security.user.CustomerDetails;

/**
 *         http://stackoverflow.com/questions/5105776/spring-security-with
 *         -custom-user-details
 */
@Service("customerDetailsService")
public class CustomerServicesImpl extends AbstractCustomerServices{

private static final Logger LOGGER = LoggerFactory.getLogger(CustomerServicesImpl.class);


private CustomerService customerService;
private PermissionService  permissionService;
private GroupService   groupService;

@Inject
public CustomerServicesImpl(CustomerService customerService, PermissionService permissionService, GroupService groupService) {
	super(customerService, permissionService, groupService);
	this.customerService = customerService;
	this.permissionService = permissionService;
	this.groupService = groupService;
}

@Override
protected UserDetails userDetails(String userName, Customer customer, Collection<GrantedAuthority> authorities) {
	
	CustomerDetails authUser = new CustomerDetails(userName, customer.getPassword(), true, true,
			true, true, authorities);
	
	authUser.setEmail(customer.getEmailAddress());
	authUser.setId(customer.getId());
	
	return authUser;
}





}