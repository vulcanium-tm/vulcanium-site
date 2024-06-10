package dev.vulcanium.business.services.order.orderstatushistory;

import dev.vulcanium.business.model.order.Order;
import dev.vulcanium.business.model.order.orderstatus.OrderStatusHistory;
import dev.vulcanium.business.repositories.order.OrderStatusHistoryRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderStatusHistoryServiceImpl implements OrderStatusHistoryService{
    @Autowired
    private OrderStatusHistoryRepository orderStatusHistoryRepository;

    @Override
    public List<OrderStatusHistory> findByOrder(Order order) {
        return orderStatusHistoryRepository.findByOrderId(order.getId());
    }
}
