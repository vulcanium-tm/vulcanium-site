package dev.vulcanium.site.tech.utils;

import static dev.vulcanium.business.constants.Constants.DEFAULT_STORE;

import java.util.Locale;
import java.util.Optional;

import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;
import dev.vulcanium.business.exception.ServiceException;
import dev.vulcanium.business.services.reference.language.LanguageService;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.business.constants.Constants;
import dev.vulcanium.site.tech.store.api.exception.ServiceRuntimeException;
import dev.vulcanium.site.tech.store.controller.store.facade.StoreFacade;

@Component
public class LanguageUtils {

protected final Log logger = LogFactory.getLog(getClass());
public static final String REQUEST_PARAMATER_STORE = "store";

private static final String ALL_LANGUALES = "_all";

@Inject
LanguageService languageService;

@Autowired
private StoreFacade storeFacade;

public Language getServiceLanguage(String lang) {
	Language l = null;
	if (!StringUtils.isBlank(lang)) {
		try {
			l = languageService.getByCode(lang);
		} catch (ServiceException e) {
			logger.error("Cannot retrieve language " + lang, e);
		}
	}
	
	if (l == null) {
		l = languageService.defaultLanguage();
	}
	
	return l;
}

/**
 * Determines request language based on store rules
 *
 * @param request
 * @return
 */
public Language getRequestLanguage(HttpServletRequest request, HttpServletResponse response) {
	
	Locale locale = null;
	
	Language language = (Language) request.getSession().getAttribute(Constants.LANGUAGE);
	MerchantStore store =
			(MerchantStore) request.getSession().getAttribute(Constants.MERCHANT_STORE);
	
	
	
	if (language == null) {
		try {
			
			locale = LocaleContextHolder.getLocale();
			
			
			
			if (store != null) {
				language = store.getDefaultLanguage();
				if (language != null) {
					locale = languageService.toLocale(language, store);
					if (locale != null) {
						LocaleContextHolder.setLocale(locale);
					}
					request.getSession().setAttribute(Constants.LANGUAGE, language);
				}
				
				if (language == null) {
					language = languageService.toLanguage(locale);
					request.getSession().setAttribute(Constants.LANGUAGE, language);
				}
				
			}
			
		} catch (Exception e) {
			if (language == null) {
				try {
					language = languageService.getByCode(Constants.DEFAULT_LANGUAGE);
				} catch (Exception ignore) {
				}
			}
		}
	} else {
		
		
		Locale localeFromContext = LocaleContextHolder.getLocale();
		if (!language.getCode().equals(localeFromContext.getLanguage())) {
			language = languageService.toLanguage(localeFromContext);
		}
		
	}
	
	if (language != null) {
		locale = languageService.toLocale(language, store);
	} else {
		language = languageService.toLanguage(locale);
	}
	
	LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
	if (localeResolver != null) {
		localeResolver.setLocale(request, response, locale);
	}
	response.setLocale(locale);
	request.getSession().setAttribute(Constants.LANGUAGE, language);
	
	return language;
}

/**
 * Should be used by rest web services
 *
 * @param request
 * @param store
 * @return
 * @throws Exception
 */
public Language getRESTLanguage(HttpServletRequest request, NativeWebRequest webRequest) {
	
	Validate.notNull(request, "HttpServletRequest must not be null");
	
	try {
		Language language = null;
		
		String lang = request.getParameter(Constants.LANG);
		
		if (StringUtils.isBlank(lang)) {
			if (language == null) {
				String storeValue = Optional.ofNullable(webRequest.getParameter(REQUEST_PARAMATER_STORE))
						                    .filter(StringUtils::isNotBlank).orElse(DEFAULT_STORE);
				if(!StringUtils.isBlank(storeValue)) {
					try {
						MerchantStore storeModel = storeFacade.get(storeValue);
						language = storeModel.getDefaultLanguage();
					} catch (Exception e) {
						logger.warn("Cannot get store with code [" + storeValue + "]");
					}
					
				} else {
					language = languageService.defaultLanguage();
				}
				
			}
		} else {
			if(!ALL_LANGUALES.equals(lang)) {
				language = languageService.getByCode(lang);
				if (language == null) {
					language = languageService.defaultLanguage();
				}
			}
		}
		return language;
		
	} catch (ServiceException e) {
		throw new ServiceRuntimeException(e);
	}
}

}
