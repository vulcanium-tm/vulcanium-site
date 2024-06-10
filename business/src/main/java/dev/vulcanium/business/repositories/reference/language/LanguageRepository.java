package dev.vulcanium.business.repositories.reference.language;

import dev.vulcanium.business.exception.ServiceException;
import dev.vulcanium.business.model.reference.language.Language;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LanguageRepository extends JpaRepository <Language, Integer> {
	
	Language findByCode(String code) throws ServiceException;
	


}
