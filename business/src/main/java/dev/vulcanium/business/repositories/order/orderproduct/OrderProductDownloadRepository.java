package dev.vulcanium.business.repositories.order.orderproduct;

import dev.vulcanium.business.model.order.orderproduct.OrderProductDownload;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderProductDownloadRepository extends JpaRepository<OrderProductDownload, Long> {

	@Query("select o from OrderProductDownload o left join fetch o.orderProduct op join fetch op.order opo join fetch opo.merchant opon where o.id = ?1")
	OrderProductDownload findOne(Long id);
	
	@Query("select o from OrderProductDownload o left join fetch o.orderProduct op join fetch op.order opo join fetch opo.merchant opon where opo.id = ?1")
	List<OrderProductDownload> findByOrderId(Long id);

}
