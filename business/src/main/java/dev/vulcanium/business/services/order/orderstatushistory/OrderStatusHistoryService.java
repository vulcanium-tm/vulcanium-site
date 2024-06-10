package dev.vulcanium.business.services.order.orderstatushistory;

import dev.vulcanium.business.model.order.Order;
import dev.vulcanium.business.model.order.orderstatus.OrderStatusHistory;
import java.util.List;

public interface OrderStatusHistoryService {
    List<OrderStatusHistory> findByOrder(Order order);
}
