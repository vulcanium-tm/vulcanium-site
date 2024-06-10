package dev.vulcanium.business.services.reference.language;

import dev.vulcanium.business.exception.ServiceException;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.business.services.common.generic.SalesManagerEntityService;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public interface LanguageService extends SalesManagerEntityService<Integer, Language> {

	Language getByCode(String code) throws ServiceException;

	Map<String, Language> getLanguagesMap() throws ServiceException;

	List<Language> getLanguages() throws ServiceException;

	Locale toLocale(Language language, MerchantStore store);

	Language toLanguage(Locale locale);
	
	Language defaultLanguage();
}
