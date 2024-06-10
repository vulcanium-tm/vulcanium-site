package dev.vulcanium.business.repositories.order;

import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.order.OrderCriteria;
import dev.vulcanium.business.model.order.OrderList;




public interface OrderRepositoryCustom {

	OrderList listByStore(MerchantStore store, OrderCriteria criteria);
	OrderList listOrders(MerchantStore store, OrderCriteria criteria);
}
