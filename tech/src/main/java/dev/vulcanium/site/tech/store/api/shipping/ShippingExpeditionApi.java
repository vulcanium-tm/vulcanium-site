package dev.vulcanium.site.tech.store.api.shipping;

import dev.vulcanium.business.constants.Constants;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.business.utils.AuthorizationUtils;
import dev.vulcanium.site.tech.model.references.ReadableCountry;
import dev.vulcanium.site.tech.model.shipping.ExpeditionConfiguration;
import dev.vulcanium.site.tech.store.facade.shipping.ShippingFacade;
import io.swagger.annotations.Api;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/api")
@Api(tags = { "Shipping - Expedition management resource (Shipping Management Api) - ship to country" })
@SwaggerDefinition(tags = { @Tag(name = "Shipping - Expedition management resource", description = "Manage shipping expedition") })
public class ShippingExpeditionApi {

private static final Logger LOGGER = LoggerFactory.getLogger(ShippingExpeditionApi.class);

@Autowired
private AuthorizationUtils authorizationUtils;

@Autowired
private ShippingFacade shippingFacade;

@RequestMapping(value = { "/private/shipping/expedition" }, method = RequestMethod.GET)
@ResponseStatus(HttpStatus.OK)
@ResponseBody
public ExpeditionConfiguration expedition(
		@ApiIgnore MerchantStore merchantStore,
		@ApiIgnore Language language) {
	
	
	String user = authorizationUtils.authenticatedUser();
	authorizationUtils.authorizeUser(user, Stream.of(Constants.GROUP_SUPERADMIN, Constants.GROUP_ADMIN,
			Constants.GROUP_SHIPPING, Constants.GROUP_ADMIN_RETAIL).collect(Collectors.toList()), merchantStore);
	
	return shippingFacade.getExpeditionConfiguration(merchantStore, language);
	
}

@GetMapping("/shipping/country")
public List<ReadableCountry>
getCountry(
		@ApiIgnore MerchantStore merchantStore,
		@ApiIgnore Language language) {
	return shippingFacade.shipToCountry(merchantStore, language);
}


@RequestMapping(value = { "/private/shipping/expedition" }, method = RequestMethod.POST)
@ResponseStatus(HttpStatus.OK)
@ResponseBody
public void saveExpedition(
		@RequestBody ExpeditionConfiguration expedition,
		@ApiIgnore MerchantStore merchantStore,
		@ApiIgnore Language language) {
	
	
	String user = authorizationUtils.authenticatedUser();
	authorizationUtils.authorizeUser(user, Stream.of(Constants.GROUP_SUPERADMIN, Constants.GROUP_ADMIN,
			Constants.GROUP_SHIPPING, Constants.GROUP_ADMIN_RETAIL).collect(Collectors.toList()), merchantStore);
	
	shippingFacade.saveExpeditionConfiguration(expedition, merchantStore);
	
}

}
