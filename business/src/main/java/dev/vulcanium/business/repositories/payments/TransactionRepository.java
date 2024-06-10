package dev.vulcanium.business.repositories.payments;

import dev.vulcanium.business.model.payments.Transaction;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Temporal;
import org.springframework.data.repository.query.Param;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

	@Query("select t from Transaction t join fetch t.order to where to.id = ?1")
	List<Transaction> findByOrder(Long orderId);
	
	@Query("select t from Transaction t join fetch t.order to left join fetch to.orderAttributes toa left join fetch to.orderProducts too left join fetch to.orderTotal toot left join fetch to.orderHistory tood where to is not null and t.transactionDate BETWEEN :from AND :to")
	List<Transaction> findByDates(
			@Param("from") @Temporal(jakarta.persistence.TemporalType.TIMESTAMP) Date startDate, 
			@Param("to") @Temporal(jakarta.persistence.TemporalType.TIMESTAMP) Date endDate);
}
