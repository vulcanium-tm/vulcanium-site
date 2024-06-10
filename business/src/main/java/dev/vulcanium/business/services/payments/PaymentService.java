package dev.vulcanium.business.services.payments;

import dev.vulcanium.business.exception.ServiceException;
import dev.vulcanium.business.model.customer.Customer;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.order.Order;
import dev.vulcanium.business.model.payments.CreditCardType;
import dev.vulcanium.business.model.payments.Payment;
import dev.vulcanium.business.model.payments.PaymentMethod;
import dev.vulcanium.business.model.payments.Transaction;
import dev.vulcanium.business.model.shoppingcart.ShoppingCartItem;
import dev.vulcanium.business.model.system.IntegrationConfiguration;
import dev.vulcanium.business.model.system.IntegrationModule;
import dev.vulcanium.business.modules.integration.payment.model.PaymentModule;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface PaymentService {

	List<IntegrationModule> getPaymentMethods(MerchantStore store)
			throws ServiceException;

	Map<String, IntegrationConfiguration> getPaymentModulesConfigured(
			MerchantStore store) throws ServiceException;
	
	Transaction processPayment(Customer customer, MerchantStore store, Payment payment, List<ShoppingCartItem> items, Order order) throws ServiceException;
	Transaction processRefund(Order order, Customer customer, MerchantStore store, BigDecimal amount) throws ServiceException;

	/**
	 * Get a specific Payment module by payment type CREDITCART, MONEYORDER ...
	 * @param store
	 * @param type (payment type)
	 * @return IntegrationModule
	 * @throws ServiceException
	 */
	IntegrationModule getPaymentMethodByType(MerchantStore store, String type)
			throws ServiceException;
	
	/**
	 * Get a specific Payment module by payment code (defined in integrationmoduel.json) paypal, authorizenet ..
	 * @param store
	 * @param name
	 * @return IntegrationModule
	 * @throws ServiceException
	 */
	IntegrationModule getPaymentMethodByCode(MerchantStore store, String name)
			throws ServiceException;

	/**
	 * Saves a payment module configuration
	 * @param configuration
	 * @param store
	 * @throws ServiceException
	 */
	void savePaymentModuleConfiguration(IntegrationConfiguration configuration,
			MerchantStore store) throws ServiceException;

	/**
	 * Validates if the credit card input information are correct
	 * @param number
	 * @param type
	 * @param month
	 * @param date
	 * @throws ServiceException
	 */
	void validateCreditCard(String number, CreditCardType creditCard, String month, String date)
			throws ServiceException;

	/**
	 * Get the integration configuration
	 * for a specific payment module
	 * @param moduleCode
	 * @param store
	 * @return IntegrationConfiguration
	 * @throws ServiceException
	 */
	IntegrationConfiguration getPaymentConfiguration(String moduleCode,
			MerchantStore store) throws ServiceException;

	void removePaymentModuleConfiguration(String moduleCode, MerchantStore store)
			throws ServiceException;

	Transaction processCapturePayment(Order order, Customer customer,
			MerchantStore store)
			throws ServiceException;
	
	/**
	 * Initializes a transaction
	 * @param order
	 * @param customer
	 * @param payment
	 * @param store
	 * @return Transaction
	 */
	Transaction initTransaction(Order order, Customer customer, Payment payment, MerchantStore store) throws ServiceException;
	
	/**
	 * Initializes a transaction without an order
	 * @param order
	 * @param customer
	 * @param payment
	 * @param store
	 * @return Transaction
	 */
	Transaction initTransaction(Customer customer, Payment payment, MerchantStore store) throws ServiceException;

	List<PaymentMethod> getAcceptedPaymentMethods(MerchantStore store)
			throws ServiceException;

	/**
	 * Returns a PaymentModule based on the payment code
	 * @param paymentModuleCode
	 * @return PaymentModule
	 * @throws ServiceException
	 */
	PaymentModule getPaymentModule(String paymentModuleCode)
			throws ServiceException;

}