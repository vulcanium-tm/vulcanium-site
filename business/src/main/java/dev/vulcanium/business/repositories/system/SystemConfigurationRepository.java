package dev.vulcanium.business.repositories.system;

import dev.vulcanium.business.model.system.SystemConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SystemConfigurationRepository extends JpaRepository<SystemConfiguration, Long> {


	SystemConfiguration findByKey(String key);

}
