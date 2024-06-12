package dev.vulcanium.site.tech.store.api.order;

import dev.vulcanium.business.model.common.Delivery;
import dev.vulcanium.business.model.customer.Customer;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.country.Country;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.business.model.shipping.ShippingOption;
import dev.vulcanium.business.model.shipping.ShippingQuote;
import dev.vulcanium.business.model.shipping.ShippingSummary;
import dev.vulcanium.business.model.shoppingcart.ShoppingCart;
import dev.vulcanium.business.services.catalog.pricing.PricingService;
import dev.vulcanium.business.services.customer.CustomerService;
import dev.vulcanium.business.services.reference.country.CountryService;
import dev.vulcanium.business.utils.LabelUtils;
import dev.vulcanium.site.tech.model.customer.address.AddressLocation;
import dev.vulcanium.site.tech.model.order.shipping.ReadableShippingSummary;
import dev.vulcanium.site.tech.populator.order.ReadableShippingSummaryPopulator;
import dev.vulcanium.site.tech.store.facade.order.OrderFacade;
import dev.vulcanium.site.tech.store.facade.shoppingcart.ShoppingCartFacade;
import io.swagger.annotations.*;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.List;
import java.util.Locale;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@Controller
@RequestMapping("/api")
@Api(tags = {"Shipping Quotes and Calculation resource (Shipping Api)"})
@SwaggerDefinition(tags = {
		@Tag(name = "Shipping Quotes and Calculation resource", description = "Get shipping quotes for public api and loged in customers")
})
public class OrderShippingApi {

private static final Logger LOGGER = LoggerFactory.getLogger(OrderShippingApi.class);

@Inject private CustomerService customerService;

@Inject private OrderFacade orderFacade;

@Inject private ShoppingCartFacade shoppingCartFacade;

@Inject private LabelUtils messages;

@Inject private PricingService pricingService;

@Inject private CountryService countryService;

/**
 * Get shipping quote for a given shopping cart
 *
 * @param id
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
@RequestMapping(
		value = {"/auth/cart/{code}/shipping"},
		method = RequestMethod.GET)
@ResponseBody
@ApiImplicitParams({
		@ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
		@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en")
})
public ReadableShippingSummary shipping(
		@PathVariable final String code,
		@ApiIgnore MerchantStore merchantStore,
		@ApiIgnore Language language,
		HttpServletRequest request,
		HttpServletResponse response) {
	
	try {
		Locale locale = request.getLocale();
		Principal principal = request.getUserPrincipal();
		String userName = principal.getName();
		Customer customer = customerService.getByNick(userName);
		
		if (customer == null) {
			response.sendError(503, "Error while getting user details to calculate shipping quote");
		}
		
		ShoppingCart cart = shoppingCartFacade.getShoppingCartModel(code, merchantStore);
		
		if (cart == null) {
			response.sendError(404, "Cart code " + code + " does not exist");
		}
		
		if (cart.getCustomerId() == null) {
			response.sendError(404, "Cart code " + code + " does not exist for exist for user " + userName);
		}
		
		if (cart.getCustomerId().longValue() != customer.getId().longValue()) {
			response.sendError(404, "Cart code " + code + " does not exist for exist for user " + userName);
		}
		
		ShippingQuote quote = orderFacade.getShippingQuote(customer, cart, merchantStore, language);
		
		ShippingSummary summary = orderFacade.getShippingSummary(quote, merchantStore, language);
		
		ReadableShippingSummary shippingSummary = new ReadableShippingSummary();
		ReadableShippingSummaryPopulator populator = new ReadableShippingSummaryPopulator();
		populator.setPricingService(pricingService);
		populator.populate(summary, shippingSummary, merchantStore, language);
		
		List<ShippingOption> options = quote.getShippingOptions();
		
		if (!CollectionUtils.isEmpty(options)) {
			
			for (ShippingOption shipOption : options) {
				
				StringBuilder moduleName = new StringBuilder();
				moduleName.append("module.shipping.").append(shipOption.getShippingModuleCode());
				
				String carrier =
						messages.getMessage(
								moduleName.toString(), new String[] {merchantStore.getStorename()}, locale);
				
				String note = messages.getMessage(moduleName.append(".note").toString(), locale, "");
				
				shipOption.setDescription(carrier);
				shipOption.setNote(note);
				
				if (!StringUtils.isBlank(shipOption.getOptionCode())) {
					StringBuilder optionCodeBuilder = new StringBuilder();
					try {
						
						optionCodeBuilder
								.append("module.shipping.")
								.append(shipOption.getShippingModuleCode());
						String optionName = messages.getMessage(optionCodeBuilder.toString(), locale);
						shipOption.setOptionName(optionName);
					} catch (Exception e) { // label not found
						LOGGER.warn("No shipping code found for " + optionCodeBuilder.toString());
					}
				}
			}
			
			shippingSummary.setShippingOptions(options);
		}
		
		return shippingSummary;
		
	} catch (Exception e) {
		LOGGER.error("Error while getting shipping quote", e);
		try {
			response.sendError(503, "Error while getting shipping quote" + e.getMessage());
		} catch (Exception ignore) {
		}
		return null;
	}
}

/**
 * Get shipping quote based on postal code
 * @param code
 * @param address
 * @param merchantStore
 * @param language
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
@RequestMapping(
		value = {"/cart/{code}/shipping"},
		method = RequestMethod.POST)
@ResponseBody
@ApiImplicitParams({
		@ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
		@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en")
})
public ReadableShippingSummary shipping(
		@PathVariable final String code,
		@RequestBody AddressLocation address,
		@ApiIgnore MerchantStore merchantStore,
		@ApiIgnore Language language,
		HttpServletRequest request,
		HttpServletResponse response)
		throws Exception {
	
	try {
		Locale locale = request.getLocale();
		
		ShoppingCart cart = shoppingCartFacade.getShoppingCartModel(code, merchantStore);
		
		if (cart == null) {
			response.sendError(404, "Cart id " + code + " does not exist");
		}
		
		
		Delivery addr = new Delivery();
		addr.setPostalCode(address.getPostalCode());
		
		Country c = countryService.getByCode(address.getCountryCode());
		
		if(c==null) {
			c = merchantStore.getCountry();
		}
		addr.setCountry(c);
		
		
		Customer temp = new Customer();
		temp.setAnonymous(true);
		temp.setDelivery(addr);
		
		ShippingQuote quote = orderFacade.getShippingQuote(temp, cart, merchantStore, language);
		
		ShippingSummary summary = orderFacade.getShippingSummary(quote, merchantStore, language);
		
		ReadableShippingSummary shippingSummary = new ReadableShippingSummary();
		ReadableShippingSummaryPopulator populator = new ReadableShippingSummaryPopulator();
		populator.setPricingService(pricingService);
		populator.populate(summary, shippingSummary, merchantStore, language);
		
		List<ShippingOption> options = quote.getShippingOptions();
		
		if (!CollectionUtils.isEmpty(options)) {
			
			for (ShippingOption shipOption : options) {
				
				StringBuilder moduleName = new StringBuilder();
				moduleName.append("module.shipping.").append(shipOption.getShippingModuleCode());
				
				String carrier =
						messages.getMessage(
								moduleName.toString(), new String[] {merchantStore.getStorename()}, locale);
				
				String note = messages.getMessage(moduleName.append(".note").toString(), locale, "");
				
				shipOption.setDescription(carrier);
				shipOption.setNote(note);
				
				if (!StringUtils.isBlank(shipOption.getOptionCode())) {
					StringBuilder optionCodeBuilder = new StringBuilder();
					try {
						
						optionCodeBuilder
								.append("module.shipping.")
								.append(shipOption.getShippingModuleCode());
						String optionName = messages.getMessage(optionCodeBuilder.toString(), new String[]{merchantStore.getStorename()},locale);
						shipOption.setOptionName(optionName);
					} catch (Exception e) {
						LOGGER.warn("No shipping code found for " + optionCodeBuilder.toString());
					}
				}
			}
			
			shippingSummary.setShippingOptions(options);
		}
		
		return shippingSummary;
		
	} catch (Exception e) {
		LOGGER.error("Error while getting shipping quote", e);
		try {
			response.sendError(503, "Error while getting shipping quote" + e.getMessage());
		} catch (Exception ignore) {
		}
		return null;
	}
}
}
