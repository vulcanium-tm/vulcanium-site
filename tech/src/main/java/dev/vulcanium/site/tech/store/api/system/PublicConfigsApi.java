package dev.vulcanium.site.tech.store.api.system;

import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.site.tech.model.system.Configs;
import dev.vulcanium.site.tech.store.controller.store.facade.StoreFacade;
import dev.vulcanium.site.tech.store.controller.system.facade.MerchantConfigurationFacade;
import dev.vulcanium.site.tech.utils.LanguageUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/api")
public class PublicConfigsApi {

private static final Logger LOGGER = LoggerFactory.getLogger(PublicConfigsApi.class);

@Inject private StoreFacade storeFacade;

@Inject private LanguageUtils languageUtils;

@Inject private MerchantConfigurationFacade configurationFacade;

/**
 * Get public set of merchant configuration --- allow online purchase --- social links
 *
 * @return
 */
@GetMapping("/config")
@ApiOperation(
		httpMethod = "GET",
		value = "Get public configuration for a given merchant store",
		notes = "",
		produces = "application/json",
		response = Configs.class)
@ApiImplicitParams({
		@ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
		@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en")
})
public Configs getConfig(@ApiIgnore MerchantStore merchantStore, @ApiIgnore Language language) {
	return configurationFacade.getMerchantConfig(merchantStore, language);
}
}
