package dev.vulcanium.business.repositories.order;

import dev.vulcanium.business.model.order.OrderTotal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderTotalRepository extends JpaRepository<OrderTotal, Long> {


}
