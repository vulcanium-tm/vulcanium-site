package dev.vulcanium.site.tech.store.controller.language.facade;

import dev.vulcanium.business.exception.ServiceException;
import dev.vulcanium.business.services.reference.language.LanguageService;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.business.store.api.exception.ResourceNotFoundException;
import dev.vulcanium.business.store.api.exception.ServiceRuntimeException;
import java.util.List;
import jakarta.inject.Inject;
import org.springframework.stereotype.Service;

@Service("languageFacade")
public class LanguageFacadeImpl implements LanguageFacade {

@Inject
private LanguageService languageService;

@Override
public List<Language> getLanguages() {
	try{
		List<Language> languages = languageService.getLanguages();
		if (languages.isEmpty()) {
			throw new ResourceNotFoundException("No languages found");
		}
		return languages;
	} catch (ServiceException e){
		throw new ServiceRuntimeException(e);
	}
	
}
}
