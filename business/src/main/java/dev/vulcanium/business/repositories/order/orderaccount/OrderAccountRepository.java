package dev.vulcanium.business.repositories.order.orderaccount;

import dev.vulcanium.business.model.order.orderaccount.OrderAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderAccountRepository extends JpaRepository<OrderAccount, Long> {


}
