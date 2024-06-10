package dev.vulcanium.site.tech.store.api;

import java.util.Locale;
import java.util.Map;

import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import dev.vulcanium.business.services.catalog.category.CategoryService;
import dev.vulcanium.business.services.catalog.product.ProductService;
import dev.vulcanium.business.services.merchant.MerchantStoreService;
import dev.vulcanium.business.services.reference.language.LanguageService;
import dev.vulcanium.business.utils.ajax.AjaxResponse;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.business.constants.Constants;
import dev.vulcanium.site.tech.model.shop.ContactForm;
import dev.vulcanium.site.tech.utils.EmailTemplatesUtils;
import dev.vulcanium.site.tech.utils.LocaleUtils;


/**
 * Rest services for sending contact
 */
@Controller
@RequestMapping("/services")
public class StoreContactRESTController {

@Inject
private LanguageService languageService;

@Inject
private MerchantStoreService merchantStoreService;

@Inject
private EmailTemplatesUtils emailTemplatesUtils;



private static final Logger LOGGER = LoggerFactory.getLogger(StoreContactRESTController.class);



@RequestMapping( value="/public/{store}", method=RequestMethod.GET)
@ResponseStatus(HttpStatus.ACCEPTED)
@ResponseBody
public AjaxResponse store(@PathVariable final String store, HttpServletRequest request, HttpServletResponse response) {
	
	AjaxResponse ajaxResponse = new AjaxResponse();
	try {
		
		/** default routine **/
		
		MerchantStore merchantStore = (MerchantStore)request.getAttribute(Constants.MERCHANT_STORE);
		if(merchantStore!=null) {
			if(!merchantStore.getCode().equals(store)) {
				merchantStore = null;
			}
		}
		
		if(merchantStore== null) {
			merchantStore = merchantStoreService.getByCode(store);
		}
		
		if(merchantStore==null) {
			LOGGER.error("Merchant store is null for code " + store);
			response.sendError(503, "Merchant store is null for code " + store);
			return null;
		}
		
		Language language = merchantStore.getDefaultLanguage();
		
		Map<String,Language> langs = languageService.getLanguagesMap();
		
		
		return null;
		
		
	} catch (Exception e) {
		LOGGER.error("Error while saving category",e);
		try {
			response.sendError(503, "Error while saving category " + e.getMessage());
		} catch (Exception ignore) {
		}
		return null;
	}
}


@RequestMapping( value="/public/{store}/contact", method=RequestMethod.POST)
@ResponseStatus(HttpStatus.ACCEPTED)
@ResponseBody
public AjaxResponse contact(@PathVariable final String store, @Valid @RequestBody ContactForm contact, HttpServletRequest request, HttpServletResponse response) {
	
	AjaxResponse ajaxResponse = new AjaxResponse();
	try {
		
		/** default routine **/
		
		MerchantStore merchantStore = (MerchantStore)request.getAttribute(Constants.MERCHANT_STORE);
		if(merchantStore!=null) {
			if(!merchantStore.getCode().equals(store)) {
				merchantStore = null;
			}
		}
		
		if(merchantStore== null) {
			merchantStore = merchantStoreService.getByCode(store);
		}
		
		if(merchantStore==null) {
			LOGGER.error("Merchant store is null for code " + store);
			response.sendError(503, "Merchant store is null for code " + store);
			return null;
		}
		
		Language language = merchantStore.getDefaultLanguage();
		
		Map<String,Language> langs = languageService.getLanguagesMap();
		
		
		if(!StringUtils.isBlank(request.getParameter(Constants.LANG))) {
			String lang = request.getParameter(Constants.LANG);
			if(lang!=null) {
				language = langs.get(language);
			}
		}
		
		if(language==null) {
			language = merchantStore.getDefaultLanguage();
		}
		
		Locale l = LocaleUtils.getLocale(language);
		
		
		/** end default routine **/
		
		
		emailTemplatesUtils.sendContactEmail(contact, merchantStore, l, request.getContextPath());
		
		ajaxResponse.setStatus(AjaxResponse.RESPONSE_STATUS_SUCCESS);
		
		return ajaxResponse;
		
		
	} catch (Exception e) {
		LOGGER.error("Error while saving category",e);
		try {
			response.sendError(503, "Error while saving category " + e.getMessage());
		} catch (Exception ignore) {
		}
		return null;
	}
}

}
