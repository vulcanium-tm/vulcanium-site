package dev.vulcanium.site.tech.store.facade.customer;

import dev.vulcanium.business.model.customer.CustomerCriteria;
import dev.vulcanium.business.model.shoppingcart.ShoppingCart;
import dev.vulcanium.site.tech.model.customer.*;
import dev.vulcanium.site.tech.model.customer.address.Address;
import dev.vulcanium.site.tech.model.customer.optin.PersistableCustomerOptin;
import dev.vulcanium.site.tech.populator.customer.ReadableCustomerList;
import java.security.Principal;
import java.util.*;

import jakarta.inject.Inject;

import org.jsoup.helper.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import dev.vulcanium.business.exception.ServiceException;
import dev.vulcanium.business.modules.email.Email;
import dev.vulcanium.business.services.customer.CustomerService;
import dev.vulcanium.business.services.reference.language.LanguageService;
import dev.vulcanium.business.services.system.EmailService;
import dev.vulcanium.business.model.common.CredentialsReset;
import dev.vulcanium.business.model.customer.Customer;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.site.tech.constants.EmailConstants;
import dev.vulcanium.site.tech.store.api.exception.GenericRuntimeException;
import dev.vulcanium.site.tech.store.api.exception.ResourceNotFoundException;
import dev.vulcanium.site.tech.store.api.exception.ServiceRuntimeException;
import dev.vulcanium.site.tech.store.api.exception.UnauthorizedException;
import dev.vulcanium.site.tech.utils.DateUtil;
import dev.vulcanium.site.tech.utils.EmailUtils;
import dev.vulcanium.site.tech.utils.FilePathUtils;
import dev.vulcanium.site.tech.utils.ImageFilePath;
import dev.vulcanium.site.tech.utils.LabelUtils;

@Service("customerFacadev1")
public class CustomerFacadeImpl implements CustomerFacade {

@Autowired
private dev.vulcanium.site.tech.store.facade.customer.CustomerFacade customerFacade;

@Autowired
private CustomerService customerService;

@Autowired
private FilePathUtils filePathUtils;

@Autowired
private LanguageService lamguageService;

@Autowired
private EmailUtils emailUtils;

@Autowired
private EmailService emailService;

@Autowired
@Qualifier("img")
private ImageFilePath imageUtils;

@Inject
private LabelUtils messages;

@Inject
private PasswordEncoder passwordEncoder;

private static final String resetCustomerLink = "customer/%s/reset/%s"; // front

private static final String ACCOUNT_PASSWORD_RESET_TPL = "email_template_password_reset_request_customer.ftl";

private static final String RESET_PASSWORD_LINK = "RESET_PASSWORD_LINK";

private static final String RESET_PASSWORD_TEXT = "RESET_PASSWORD_TEXT";

@Override
public void authorize(Customer customer, Principal principal) {
	
	Validate.notNull(customer, "Customer cannot be null");
	Validate.notNull(principal, "Principal cannot be null");
	
	if (!principal.getName().equals(customer.getNick())) {
		throw new UnauthorizedException(
				"User [" + principal.getName() + "] unauthorized for customer [" + customer.getId() + "]");
	}
	
}

@Override
public void requestPasswordReset(String customerName, String customerContextPath, MerchantStore store,
                                 Language language) {
	
	try {
		Customer customer = customerService.getByNick(customerName, store.getId());
		
		if (customer == null) {
			throw new ResourceNotFoundException(
					"Customer [" + customerName + "] not found for store [" + store.getCode() + "]");
		}
		
		String token = UUID.randomUUID().toString();
		Date expiry = DateUtil.addDaysToCurrentDate(2);
		CredentialsReset credsRequest = new CredentialsReset();
		credsRequest.setCredentialsRequest(token);
		credsRequest.setCredentialsRequestExpiry(expiry);
		customer.setCredentialsResetRequest(credsRequest);
		customerService.saveOrUpdate(customer);
		String baseUrl = filePathUtils.buildBaseUrl(customerContextPath, store);
		String customerResetLink = new StringBuilder().append(baseUrl)
				                           .append(String.format(resetCustomerLink, store.getCode(), token)).toString();
		
		resetPasswordRequest(customer, customerResetLink, store, lamguageService.toLocale(language, store));
		
	} catch (Exception e) {
		throw new ServiceRuntimeException("Error while executing resetPassword request", e);
	}
	
	/**
	 * User sends username (unique in the system)
	 *
	 * UserNameEntity will be the following { userName: "test@test.com" }
	 *
	 * The system retrieves user using userName (username is unique) if user
	 * exists, system sends an email with reset password link
	 *
	 * How to retrieve a User from userName
	 *
	 * userFacade.findByUserName
	 *
	 * How to send an email
	 *
	 *
	 * How to generate a token
	 *
	 * Generate random token
	 *
	 * Calculate token expiration date
	 *
	 * Now + 48 hours
	 *
	 * Update User in the database with token
	 *
	 * Send reset token email
	 */
	
}

@Async
private void resetPasswordRequest(Customer customer, String resetLink, MerchantStore store, Locale locale)
		throws Exception {
	try {
		
		// creation of a user, send an email
		String[] storeEmail = { store.getStoreEmailAddress() };
		
		Map<String, String> templateTokens = emailUtils.createEmailObjectsMap(imageUtils.getContextPath(), store,
				messages, locale);
		templateTokens.put(EmailConstants.LABEL_HI, messages.getMessage("label.generic.hi", locale));
		templateTokens.put(EmailConstants.EMAIL_CUSTOMER_FIRSTNAME, customer.getBilling().getFirstName());
		templateTokens.put(RESET_PASSWORD_LINK, resetLink);
		templateTokens.put(RESET_PASSWORD_TEXT,
				messages.getMessage("email.reset.password.text", new String[] { store.getStorename() }, locale));
		templateTokens.put(EmailConstants.LABEL_LINK_TITLE,
				messages.getMessage("email.link.reset.password.title", locale));
		templateTokens.put(EmailConstants.LABEL_LINK, messages.getMessage("email.link", locale));
		templateTokens.put(EmailConstants.EMAIL_CONTACT_OWNER,
				messages.getMessage("email.contactowner", storeEmail, locale));
		
		Email email = new Email();
		email.setFrom(store.getStorename());
		email.setFromEmail(store.getStoreEmailAddress());
		email.setSubject(messages.getMessage("email.link.reset.password.title", locale));
		email.setTo(customer.getEmailAddress());
		email.setTemplateName(ACCOUNT_PASSWORD_RESET_TPL);
		email.setTemplateTokens(templateTokens);
		
		emailService.sendHtmlEmail(store, email);
		
	} catch (Exception e) {
		throw new Exception("Cannot send email to customer", e);
	}
}

@Override
public void verifyPasswordRequestToken(String token, String store) {
	Validate.notNull(token, "ResetPassword token cannot be null");
	Validate.notNull(store, "Store code cannot be null");
	
	verifyCustomerLink(token, store);
	return;
}

@Override
public void resetPassword(String password, String token, String store) {
	Validate.notNull(token, "ResetPassword token cannot be null");
	Validate.notNull(store, "Store code cannot be null");
	Validate.notNull(password, "New password cannot be null");
	
	Customer customer = verifyCustomerLink(token, store);// reverify
	customer.setPassword(passwordEncoder.encode(password));
	try {
		customerService.save(customer);
	} catch (ServiceException e) {
		throw new ServiceRuntimeException("Error while saving customer",e);
	}
	
}

private Customer verifyCustomerLink(String token, String store) {
	
	Customer customer = null;
	try {
		customer = customerService.getByPasswordResetToken(store, token);
		if (customer == null) {
			throw new ResourceNotFoundException(
					"Customer not fount for store [" + store + "] and token [" + token + "]");
		}
		
	} catch (Exception e) {
		throw new ServiceRuntimeException("Cannot verify customer token", e);
	}
	
	Date tokenExpiry = customer.getCredentialsResetRequest().getCredentialsRequestExpiry();
	
	if (tokenExpiry == null) {
		throw new GenericRuntimeException("No expiry date configured for token [" + token + "]");
	}
	
	if (!DateUtil.dateBeforeEqualsDate(new Date(), tokenExpiry)) {
		throw new GenericRuntimeException("Ttoken [" + token + "] has expired");
	}
	
	return customer;
	
}

@Override
public boolean customerExists(String userName, MerchantStore store) {
	return Optional.ofNullable(customerService.getByNick(userName, store.getId()))
			       .isPresent();
}

public CustomerEntity getCustomerDataByUserName(String userName, MerchantStore store, Language language) throws Exception{
	return null;
}

public ReadableCustomer getCustomerById(Long id, MerchantStore merchantStore, Language language){
	return null;
}

public ReadableCustomer getByUserName(String userName, MerchantStore merchantStore, Language language){
	return null;
}

public ShoppingCart mergeCart(Customer customer, String sessionShoppingCartId, MerchantStore store, Language language) throws Exception{
	return null;
}

public Customer getCustomerByUserName(String userName, MerchantStore store){
	return null;
}

public boolean checkIfUserExists(String userName, MerchantStore store) throws Exception{
	return false;
}

public PersistableCustomer registerCustomer(PersistableCustomer customer, MerchantStore merchantStore, Language language) throws Exception{
	return null;
}

public Address getAddress(Long userId, MerchantStore merchantStore, boolean isBillingAddress) throws Exception{
	return null;
}

public void updateAddress(Long userId, MerchantStore merchantStore, Address address, Language language) throws Exception{

}

public void setCustomerModelDefaultProperties(Customer customer, MerchantStore store) throws Exception{

}

public void authenticate(Customer customer, String userName, String password) throws Exception{

}

public Customer getCustomerModel(PersistableCustomer customer, MerchantStore merchantStore, Language language) throws Exception{
	return null;
}

public Customer populateCustomerModel(Customer customerModel, PersistableCustomer customer, MerchantStore merchantStore, Language language) throws Exception{
	return null;
}

public ReadableCustomer create(PersistableCustomer customer, MerchantStore store, Language language){
	return null;
}

public void resetPassword(Customer customer, MerchantStore store, Language language){

}

public PersistableCustomer update(PersistableCustomer customer, MerchantStore store){
	return null;
}

public PersistableCustomer update(String userName, PersistableCustomer customer, MerchantStore store){
	return null;
}

public void updateAddress(PersistableCustomer customer, MerchantStore store){

}

public void updateAddress(String userName, PersistableCustomer customer, MerchantStore store){

}

public PersistableCustomerReview saveOrUpdateCustomerReview(PersistableCustomerReview review, MerchantStore store, Language language){
	return null;
}

public List<ReadableCustomerReview> getAllCustomerReviewsByReviewed(Long customerId, MerchantStore store, Language language){
	return List.of();
}

public void deleteCustomerReview(Long customerId, Long reviewId, MerchantStore store, Language language){

}

public void optinCustomer(PersistableCustomerOptin optin, MerchantStore store){

}

public ReadableCustomer getCustomerByNick(String userName, MerchantStore merchantStore, Language language){
	return null;
}

public void deleteByNick(String nick){

}

public void deleteById(Long id){

}

public void delete(Customer entity){

}

public ReadableCustomerList getListByStore(MerchantStore store, CustomerCriteria criteria, Language language){
	return null;
}

public PersistableCustomerReview createCustomerReview(Long customerId, PersistableCustomerReview review, MerchantStore merchantStore, Language language){
	return null;
}

public PersistableCustomerReview updateCustomerReview(Long id, Long reviewId, PersistableCustomerReview review, MerchantStore store, Language language){
	return null;
}

public boolean passwordMatch(String rawPassword, Customer customer){
	return false;
}

public void changePassword(Customer customer, String newPassword){

}

}