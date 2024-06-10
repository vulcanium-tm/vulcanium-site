package dev.vulcanium.site.tech.store.api.order;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import dev.vulcanium.business.services.catalog.pricing.PricingService;
import dev.vulcanium.business.services.customer.CustomerService;
import dev.vulcanium.business.services.order.OrderService;
import dev.vulcanium.business.services.shipping.ShippingQuoteService;
import dev.vulcanium.business.model.customer.Customer;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.order.OrderSummary;
import dev.vulcanium.business.model.order.OrderTotalSummary;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.business.model.shipping.ShippingSummary;
import dev.vulcanium.business.model.shoppingcart.ShoppingCart;
import dev.vulcanium.business.model.shoppingcart.ShoppingCartItem;
import dev.vulcanium.site.tech.model.order.ReadableOrderTotalSummary;
import dev.vulcanium.site.tech.populator.order.ReadableOrderSummaryPopulator;
import dev.vulcanium.site.tech.store.facade.shoppingcart.ShoppingCartFacade;
import dev.vulcanium.site.tech.utils.LabelUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import springfox.documentation.annotations.ApiIgnore;

@Controller
@RequestMapping("/api")
@Api(tags = {"Order Total calculation for a given shopping cart (Order Total Api)"})
@SwaggerDefinition(tags = {
		@Tag(name = "Order Total resource", description = "Calculates order total for a giben shopping cart")
})
public class OrderTotalApi {

@Inject private ShoppingCartFacade shoppingCartFacade;

@Inject private LabelUtils messages;

@Inject private PricingService pricingService;

@Inject private CustomerService customerService;

@Inject private ShippingQuoteService shippingQuoteService;

@Inject private OrderService orderService;

private static final Logger LOGGER = LoggerFactory.getLogger(OrderTotalApi.class);

/**
 * This service calculates order total for a given shopping cart This method takes in
 * consideration any applicable sales tax An optional request parameter accepts a quote id that
 * was received using shipping api
 *
 * @param quote
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
@RequestMapping(
		value = {"/auth/cart/{id}/total"},
		method = RequestMethod.GET)
@ResponseBody
@ApiImplicitParams({
		@ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
		@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en")
})
public ReadableOrderTotalSummary payment(
		@PathVariable final Long id,
		@RequestParam(value = "quote", required = false) Long quote,
		@ApiIgnore MerchantStore merchantStore,
		@ApiIgnore Language language,
		HttpServletRequest request,
		HttpServletResponse response) {
	
	try {
		Principal principal = request.getUserPrincipal();
		String userName = principal.getName();
		
		Customer customer = customerService.getByNick(userName);
		
		if (customer == null) {
			response.sendError(503, "Error while getting user details to calculate shipping quote");
		}
		
		ShoppingCart shoppingCart = shoppingCartFacade.getShoppingCartModel(id, merchantStore);
		
		if (shoppingCart == null) {
			response.sendError(404, "Cart id " + id + " does not exist");
			return null;
		}
		
		if (shoppingCart.getCustomerId() == null) {
			response.sendError(
					404, "Cart id " + id + " does not exist for exist for user " + userName);
			return null;
		}
		
		if (shoppingCart.getCustomerId().longValue() != customer.getId().longValue()) {
			response.sendError(
					404, "Cart id " + id + " does not exist for exist for user " + userName);
			return null;
		}
		
		ShippingSummary shippingSummary = null;
		
		if (quote != null) {
			shippingSummary = shippingQuoteService.getShippingSummary(quote, merchantStore);
		}
		
		OrderTotalSummary orderTotalSummary = null;
		
		OrderSummary orderSummary = new OrderSummary();
		orderSummary.setShippingSummary(shippingSummary);
		List<ShoppingCartItem> itemsSet =
				new ArrayList<ShoppingCartItem>(shoppingCart.getLineItems());
		orderSummary.setProducts(itemsSet);
		
		orderTotalSummary =
				orderService.caculateOrderTotal(orderSummary, customer, merchantStore, language);
		
		ReadableOrderTotalSummary returnSummary = new ReadableOrderTotalSummary();
		ReadableOrderSummaryPopulator populator = new ReadableOrderSummaryPopulator();
		populator.setMessages(messages);
		populator.setPricingService(pricingService);
		populator.populate(orderTotalSummary, returnSummary, merchantStore, language);
		
		return returnSummary;
		
	} catch (Exception e) {
		LOGGER.error("Error while calculating order summary", e);
		try {
			response.sendError(503, "Error while calculating order summary " + e.getMessage());
		} catch (Exception ignore) {
		}
		return null;
	}
}

/**
 * Public api
 * @param id
 * @param quote
 * @param merchantStore
 * @param language
 * @param response
 * @return
 */
@RequestMapping(
		value = {"/cart/{code}/total"},
		method = RequestMethod.GET)
@ResponseBody
@ApiImplicitParams({
		@ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
		@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en")
})
public ReadableOrderTotalSummary calculateTotal(
		@PathVariable final String code,
		@RequestParam(value = "quote", required = false) Long quote,
		@ApiIgnore MerchantStore merchantStore,
		@ApiIgnore Language language,
		HttpServletResponse response) {
	
	try {
		ShoppingCart shoppingCart = shoppingCartFacade.getShoppingCartModel(code, merchantStore);
		
		if (shoppingCart == null) {
			
			response.sendError(404, "Cart code " + code + " does not exist");
			
			return null;
		}
		
		ShippingSummary shippingSummary = null;
		
		if (quote != null) {
			shippingSummary = shippingQuoteService.getShippingSummary(quote, merchantStore);
		}
		
		OrderTotalSummary orderTotalSummary = null;
		
		OrderSummary orderSummary = new OrderSummary();
		orderSummary.setShippingSummary(shippingSummary);
		List<ShoppingCartItem> itemsSet =
				new ArrayList<ShoppingCartItem>(shoppingCart.getLineItems());
		orderSummary.setProducts(itemsSet);
		
		orderTotalSummary = orderService.caculateOrderTotal(orderSummary, merchantStore, language);
		
		ReadableOrderTotalSummary returnSummary = new ReadableOrderTotalSummary();
		ReadableOrderSummaryPopulator populator = new ReadableOrderSummaryPopulator();
		populator.setMessages(messages);
		populator.setPricingService(pricingService);
		populator.populate(orderTotalSummary, returnSummary, merchantStore, language);
		
		return returnSummary;
		
	} catch (Exception e) {
		LOGGER.error("Error while calculating order summary", e);
		try {
			response.sendError(503, "Error while calculating order summary " + e.getMessage());
		} catch (Exception ignore) {
		}
		return null;
	}
}
}
