package dev.vulcanium.site.tech.store.api.references;

import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.currency.Currency;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.site.tech.model.references.*;
import dev.vulcanium.site.tech.store.controller.country.facade.CountryFacade;
import dev.vulcanium.site.tech.store.controller.currency.facade.CurrencyFacade;
import dev.vulcanium.site.tech.store.controller.language.facade.LanguageFacade;
import dev.vulcanium.site.tech.store.controller.store.facade.StoreFacade;
import dev.vulcanium.site.tech.store.facade.zone.ZoneFacade;
import dev.vulcanium.site.tech.utils.LanguageUtils;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Get system Language, Country and Currency objects
 */
@RestController
@RequestMapping("/api")
public class ReferencesApi {

private static final Logger LOGGER = LoggerFactory.getLogger(ReferencesApi.class);


@Inject private StoreFacade storeFacade;

@Inject private LanguageUtils languageUtils;

@Inject private LanguageFacade languageFacade;

@Inject private CountryFacade countryFacade;

@Inject private ZoneFacade zoneFacade;

@Inject private CurrencyFacade currencyFacade;

/**
 * Search languages by language code private/languages returns everything
 *
 * @return
 */
@GetMapping("/languages")
public List<Language> getLanguages() {
	return languageFacade.getLanguages();
}

/**
 * Returns a country with zones (provinces, states) supports language set in parameter
 * ?lang=en|fr|ru...
 *
 * @param request
 * @return
 */
@GetMapping("/country")
public List<ReadableCountry> getCountry(@ApiIgnore Language language, HttpServletRequest request) {
	MerchantStore merchantStore = storeFacade.getByCode(request);
	return countryFacade.getListCountryZones(language, merchantStore);
}

@GetMapping("/zones")
public List<ReadableZone> getZones(
		@RequestParam("code") String code, @ApiIgnore Language language, HttpServletRequest request) {
	MerchantStore merchantStore = storeFacade.getByCode(request);
	return zoneFacade.getZones(code, language, merchantStore);
}

/**
 * Currency
 *
 * @return
 */
@GetMapping("/currency")
public List<Currency> getCurrency() {
	return currencyFacade.getList();
}

@GetMapping("/measures")
public SizeReferences measures() {
	SizeReferences sizeReferences = new SizeReferences();
	sizeReferences.setMeasures(Arrays.asList(MeasureUnit.values()));
	sizeReferences.setWeights(Arrays.asList(WeightUnit.values()));
	return sizeReferences;
}
}
