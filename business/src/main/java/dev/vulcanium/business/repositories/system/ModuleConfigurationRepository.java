package dev.vulcanium.business.repositories.system;

import dev.vulcanium.business.model.system.IntegrationModule;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModuleConfigurationRepository extends JpaRepository<IntegrationModule, Long> {

	List<IntegrationModule> findByModule(String moduleName);
	
	IntegrationModule findByCode(String code);
	

}
