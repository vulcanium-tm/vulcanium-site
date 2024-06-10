package dev.vulcanium.site.tech.store.facade.customer;

import java.security.Principal;

import dev.vulcanium.business.model.customer.Customer;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import java.util.List;
import java.util.List;
import dev.vulcanium.business.services.customer.CustomerService;
import dev.vulcanium.business.model.customer.Customer;
import dev.vulcanium.business.model.customer.CustomerCriteria;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.business.model.shoppingcart.ShoppingCart;
import dev.vulcanium.site.tech.model.customer.CustomerEntity;
import dev.vulcanium.site.tech.model.customer.PersistableCustomer;
import dev.vulcanium.site.tech.model.customer.PersistableCustomerReview;
import dev.vulcanium.site.tech.model.customer.ReadableCustomer;
import dev.vulcanium.site.tech.model.customer.ReadableCustomerReview;
import dev.vulcanium.site.tech.model.customer.address.Address;
import dev.vulcanium.site.tech.model.customer.optin.PersistableCustomerOptin;
import dev.vulcanium.site.tech.populator.customer.ReadableCustomerList;

/**
 * <p>Customer facade working as a bridge between {@link CustomerService} and Controller
 * It will take care about interacting with Service API and doing any pre and post processing
 * </p>
 */
public interface CustomerFacade {

/** validates username with principal **/
void authorize(Customer customer,  Principal principal);

/**
 *
 * Forgot password functionality
 * @param customerName
 * @param store
 * @param language
 */
void requestPasswordReset(String customerName, String customerContextPath, MerchantStore store, Language language);

/**
 * Validates if a password request is valid
 * @param token
 * @param store
 */
void verifyPasswordRequestToken(String token, String store);


/**
 * Reset password
 * @param password
 * @param token
 * @param store
 */
void resetPassword(String password, String token, String store);

/**
 * Check if customer exist
 * @param userName
 * @param store
 * @return
 */
boolean customerExists(String userName, MerchantStore store);

/**
 * Method used to fetch customer based on the username and storecode.
 * Customer username is unique to each store.
 *
 * @param userName
 * @param store
 * @param store
 * @param language
 * @throws Exception
 *
 */
public CustomerEntity getCustomerDataByUserName(final String userName,final MerchantStore store, final Language language) throws Exception;

/**
 * Creates a ReadableCustomer
 * @param id
 * @param merchantStore
 * @param language
 * @return
 */
ReadableCustomer getCustomerById(final Long id, final MerchantStore merchantStore, final Language language);

/**
 * Get Customer using unique username
 * @param userName
 * @param merchantStore
 * @param language
 * @return
 * @throws Exception
 */
ReadableCustomer getByUserName(String userName, MerchantStore merchantStore, Language language);
/**
 * <p>Method responsible for merging cart during authentication,
 *     Method will perform following operations
 * <li> Merge Customer Shopping Cart with Session Cart if any.</li>
 * <li> Convert Customer to {@link CustomerEntity} </li>
 * </p>
 *
 * @param customer username of Customer
 * @param sessionShoppingCartId session shopping cart, if user already have few items in Cart.
 * @throws Exception
 */
public ShoppingCart mergeCart(final Customer customer,final String sessionShoppingCartId,final MerchantStore store,final Language language) throws Exception;

public Customer getCustomerByUserName(final String userName, final MerchantStore store);

public boolean checkIfUserExists(final String userName,final MerchantStore store) throws Exception;

public PersistableCustomer  registerCustomer( final PersistableCustomer customer,final MerchantStore merchantStore, final Language language) throws Exception;

public Address getAddress(final Long userId, final MerchantStore merchantStore,boolean isBillingAddress) throws Exception;

public void updateAddress( Long userId, MerchantStore merchantStore, Address address, final Language language )
		throws Exception;

public void setCustomerModelDefaultProperties(Customer customer, MerchantStore store) throws Exception;


public void authenticate(Customer customer, String userName, String password) throws Exception;

Customer getCustomerModel(PersistableCustomer customer,
                          MerchantStore merchantStore, Language language) throws Exception;

Customer populateCustomerModel(Customer customerModel, PersistableCustomer customer,
                               MerchantStore merchantStore, Language language) throws Exception;

/*
 * Creates a Customer from a PersistableCustomer received from REST API
 */
ReadableCustomer create(PersistableCustomer customer, MerchantStore store, Language language);

/**
 * Reset customer password
 * @param customer
 * @param store
 * @param language
 * @throws Exception
 */
void resetPassword(Customer customer, MerchantStore store, Language language);

/**
 * Updates a Customer
 * @param customer
 * @param store
 * @throws Exception
 */
PersistableCustomer update(PersistableCustomer customer, MerchantStore store);

PersistableCustomer update(String userName, PersistableCustomer customer, MerchantStore store);

/**
 * Updates only shipping and billing addresses
 * @param customer
 * @param store
 * @return
 */
void updateAddress(PersistableCustomer customer, MerchantStore store);

void updateAddress(String userName, PersistableCustomer customer, MerchantStore store);

/**
 * Save or update a CustomerReview
 * @param review
 * @param store
 * @param language
 * @throws Exception
 */
PersistableCustomerReview saveOrUpdateCustomerReview(PersistableCustomerReview review, MerchantStore store, Language language);

/**
 * List all customer reviews by reviewed
 * @param customer
 * @param store
 * @param language
 * @return
 */
List<ReadableCustomerReview> getAllCustomerReviewsByReviewed(Long customerId, MerchantStore store, Language language);

/**
 * Deletes a customer review
 * @param review
 * @param store
 * @param language
 */
void deleteCustomerReview(Long customerId, Long reviewId, MerchantStore store, Language language);

/**
 * Optin a customer to newsletter
 * @param optin
 * @param store
 * @throws Exception
 */
void optinCustomer(PersistableCustomerOptin optin, MerchantStore store);

ReadableCustomer getCustomerByNick(String userName, MerchantStore merchantStore, Language language);

void deleteByNick(String nick);

void deleteById(Long id);

void delete(Customer entity);

ReadableCustomerList getListByStore(MerchantStore store, CustomerCriteria criteria, Language language);

PersistableCustomerReview createCustomerReview(
		Long customerId,
		PersistableCustomerReview review,
		MerchantStore merchantStore,
		Language language);

PersistableCustomerReview updateCustomerReview(Long id, Long reviewId, PersistableCustomerReview review, MerchantStore store, Language language);

boolean passwordMatch(String rawPassword, Customer customer);

void changePassword(Customer customer, String newPassword);
}

