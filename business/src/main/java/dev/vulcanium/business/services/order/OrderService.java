package dev.vulcanium.business.services.order;

import dev.vulcanium.business.exception.ServiceException;
import dev.vulcanium.business.model.customer.Customer;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.order.*;
import dev.vulcanium.business.model.order.orderstatus.OrderStatusHistory;
import dev.vulcanium.business.model.payments.Payment;
import dev.vulcanium.business.model.payments.Transaction;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.business.model.shoppingcart.ShoppingCart;
import dev.vulcanium.business.model.shoppingcart.ShoppingCartItem;
import dev.vulcanium.business.services.common.generic.SalesManagerEntityService;
import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.List;



public interface OrderService extends SalesManagerEntityService<Long, Order> {

    void addOrderStatusHistory(Order order, OrderStatusHistory history)
                    throws ServiceException;

    /**
     * Can be used to calculates the final prices of all items contained in checkout page
     * @param orderSummary
     * @param customer
     * @param store
     * @param language
     * @return
     * @throws ServiceException
     */
    OrderTotalSummary caculateOrderTotal(OrderSummary orderSummary,
                                         Customer customer, MerchantStore store, Language language)
                                                         throws ServiceException;

    /**
     * Can be used to calculates the final prices of all items contained in a ShoppingCart
     * @param orderSummary
     * @param store
     * @param language
     * @return
     * @throws ServiceException
     */
    OrderTotalSummary caculateOrderTotal(OrderSummary orderSummary,
                                         MerchantStore store, Language language) throws ServiceException;


    /**
     * Can be used to calculates the final prices of all items contained in checkout page
     * @param shoppingCart
     * @param customer
     * @param store
     * @param language
     * @return  @return {@link OrderTotalSummary}
     * @throws ServiceException
     */
    OrderTotalSummary calculateShoppingCartTotal(final ShoppingCart shoppingCart,final Customer customer, final MerchantStore store, final Language language) throws ServiceException;

    /**
     * Can be used to calculates the final prices of all items contained in a ShoppingCart
     * @param shoppingCart
     * @param store
     * @param language
     * @return {@link OrderTotalSummary}
     * @throws ServiceException
     */
    OrderTotalSummary calculateShoppingCartTotal(final ShoppingCart shoppingCart,final MerchantStore store, final Language language) throws ServiceException;

    ByteArrayOutputStream generateInvoice(MerchantStore store, Order order,
                                          Language language) throws ServiceException;

    Order getOrder(Long id, MerchantStore store);

    
    /**
     * For finding orders. Mainly used in the administration tool
     * @param store
     * @param criteria
     * @return
     */
    OrderList listByStore(MerchantStore store, OrderCriteria criteria);


    /**
	 * get all orders. Mainly used in the administration tool
	 * @param criteria
	 * @return
	 */
	OrderList getOrders(OrderCriteria criteria, MerchantStore store);

    void saveOrUpdate(Order order) throws ServiceException;

	Order processOrder(Order order, Customer customer,
			List<ShoppingCartItem> items, OrderTotalSummary summary,
			Payment payment, MerchantStore store) throws ServiceException;

	Order processOrder(Order order, Customer customer,
			List<ShoppingCartItem> items, OrderTotalSummary summary,
			Payment payment, Transaction transaction, MerchantStore store)
			throws ServiceException;



	
	/**
	 * Determines if an Order has download files
	 * @param order
	 * @return
	 * @throws ServiceException
	 */
	boolean hasDownloadFiles(Order order) throws ServiceException;
	
	/**
	 * List all orders that have been pre-authorized but not captured
	 * @param store
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws ServiceException
	 */
	List<Order> getCapturableOrders(MerchantStore store, Date startDate, Date endDate) throws ServiceException;

}
