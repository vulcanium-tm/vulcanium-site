package dev.vulcanium.business.modules.integration.payment.model;

import java.math.BigDecimal;
import java.util.List;

import dev.vulcanium.business.model.customer.Customer;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.order.Order;
import dev.vulcanium.business.model.payments.Payment;
import dev.vulcanium.business.model.payments.Transaction;
import dev.vulcanium.business.model.shoppingcart.ShoppingCartItem;
import dev.vulcanium.business.model.system.IntegrationConfiguration;
import dev.vulcanium.business.model.system.IntegrationModule;
import dev.vulcanium.business.modules.integration.IntegrationException;

public interface PaymentModule {

public void validateModuleConfiguration(IntegrationConfiguration integrationConfiguration, MerchantStore store) throws IntegrationException;


/**
 * Returns token-value related to the initialization of the transaction This
 * method is invoked for paypal express checkout
 * @param store MerchantStore
 * @param customer Customer
 * @param amount BigDecimal
 * @param payment Payment
 * @param configuration IntegrationConfiguration
 * @param module IntegrationModule
 * @return Transaction a Transaction
 * @throws IntegrationException IntegrationException
 */
Transaction initTransaction(
		MerchantStore store, Customer customer, BigDecimal amount, Payment payment, IntegrationConfiguration configuration, IntegrationModule module)
		throws IntegrationException;

 Transaction authorize(
		MerchantStore store, Customer customer, List<ShoppingCartItem> items, BigDecimal amount, Payment payment, IntegrationConfiguration configuration, IntegrationModule module)
		throws IntegrationException;


 Transaction capture(
		MerchantStore store, Customer customer, Order order, Transaction capturableTransaction, IntegrationConfiguration configuration, IntegrationModule module)
		throws IntegrationException;

 Transaction authorizeAndCapture(
		MerchantStore store, Customer customer, List<ShoppingCartItem> items, BigDecimal amount, Payment payment, IntegrationConfiguration configuration, IntegrationModule module)
		throws IntegrationException;

 Transaction refund(
		boolean partial, MerchantStore store, Transaction transaction, Order order, BigDecimal amount, IntegrationConfiguration configuration, IntegrationModule module)
		throws IntegrationException;
	
}
