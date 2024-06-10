package dev.vulcanium.site.tech.store.security.customer;

import java.util.Collection;
import java.util.Date;

import jakarta.inject.Inject;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import dev.vulcanium.business.services.customer.CustomerService;
import dev.vulcanium.business.services.user.GroupService;
import dev.vulcanium.business.services.user.PermissionService;
import dev.vulcanium.business.model.common.audit.AuditSection;
import dev.vulcanium.business.model.customer.Customer;
import dev.vulcanium.site.tech.store.security.AbstractCustomerServices;
import dev.vulcanium.site.tech.store.security.user.JWTUser;


@Service("jwtCustomerDetailsService")
public class JWTCustomerServicesImpl extends AbstractCustomerServices {


@Inject
public JWTCustomerServicesImpl(CustomerService customerService, PermissionService permissionService, GroupService groupService) {
	super(customerService, permissionService, groupService);
	this.customerService = customerService;
	this.permissionService = permissionService;
	this.groupService = groupService;
}

@Override
protected UserDetails userDetails(String userName, Customer customer, Collection<GrantedAuthority> authorities) {
	
	AuditSection section = null;
	section = customer.getAuditSection();
	Date lastModified = null;
	
	return new JWTUser(
			customer.getId(),
			userName,
			customer.getBilling().getFirstName(),
			customer.getBilling().getLastName(),
			customer.getEmailAddress(),
			customer.getPassword(),
			authorities,
			true,
			lastModified
	);
}

}
