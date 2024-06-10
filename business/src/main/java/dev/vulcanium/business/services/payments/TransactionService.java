package dev.vulcanium.business.services.payments;

import dev.vulcanium.business.exception.ServiceException;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.order.Order;
import dev.vulcanium.business.model.payments.Transaction;
import dev.vulcanium.business.services.common.generic.SalesManagerEntityService;
import java.util.Date;
import java.util.List;




public interface TransactionService extends SalesManagerEntityService<Long, Transaction> {

	/**
	 * Obtain a previous transaction that has type authorize for a give order
	 * @param order
	 * @return
	 * @throws ServiceException
	 */
	Transaction getCapturableTransaction(Order order) throws ServiceException;

	Transaction getRefundableTransaction(Order order) throws ServiceException;

	List<Transaction> listTransactions(Order order) throws ServiceException;
	
	List<Transaction> listTransactions(Date startDate, Date endDate) throws ServiceException;
	
	Transaction lastTransaction(Order order, MerchantStore store) throws ServiceException;



}