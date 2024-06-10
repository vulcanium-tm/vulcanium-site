package dev.vulcanium.business.repositories.shipping;

import dev.vulcanium.business.model.shipping.Quote;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ShippingQuoteRepository extends JpaRepository<Quote, Long> {
	
	
	@Query("select q from Quote as q where q.orderId = ?1")
	List<Quote> findByOrder(Long order);

}
