package dev.vulcanium.business.repositories.system;

import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.system.MerchantLog;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MerchantLogRepository extends JpaRepository<MerchantLog, Long> {

	List<MerchantLog> findByStore(MerchantStore store);
}
