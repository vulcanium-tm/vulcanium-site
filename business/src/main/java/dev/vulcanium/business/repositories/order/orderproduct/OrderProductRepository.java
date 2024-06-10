package dev.vulcanium.business.repositories.order.orderproduct;

import dev.vulcanium.business.model.order.orderproduct.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {


}
