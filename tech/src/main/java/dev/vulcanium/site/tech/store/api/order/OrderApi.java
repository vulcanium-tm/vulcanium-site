package dev.vulcanium.site.tech.store.api.order;

import dev.vulcanium.business.constants.Constants;
import dev.vulcanium.business.model.customer.Customer;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.order.Order;
import dev.vulcanium.business.model.order.OrderCriteria;
import dev.vulcanium.business.model.order.orderstatus.OrderStatus;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.business.model.shoppingcart.ShoppingCart;
import dev.vulcanium.business.services.customer.CustomerService;
import dev.vulcanium.business.services.order.OrderService;
import dev.vulcanium.business.services.shoppingcart.ShoppingCartService;
import dev.vulcanium.business.utils.AuthorizationUtils;
import dev.vulcanium.business.utils.LocaleUtils;
import dev.vulcanium.site.tech.model.customer.PersistableCustomer;
import dev.vulcanium.site.tech.model.customer.ReadableCustomer;
import dev.vulcanium.site.tech.model.order.*;
import dev.vulcanium.site.tech.populator.customer.ReadableCustomerPopulator;
import dev.vulcanium.site.tech.store.api.exception.GenericRuntimeException;
import dev.vulcanium.site.tech.store.api.exception.ResourceNotFoundException;
import dev.vulcanium.site.tech.store.api.exception.ServiceRuntimeException;
import dev.vulcanium.site.tech.store.facade.customer.CustomerFacade;
import dev.vulcanium.site.tech.store.facade.order.OrderFacade;
import dev.vulcanium.site.tech.store.security.services.CredentialsException;
import dev.vulcanium.site.tech.store.security.services.CredentialsService;
import io.swagger.annotations.*;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.helper.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/api")
@Api(tags = { "Ordering api (Order Flow Api)" })
@SwaggerDefinition(tags = { @Tag(name = "Order flow resource", description = "Manage orders (create, list, get)") })
public class OrderApi {

private static final Logger LOGGER = LoggerFactory.getLogger(OrderApi.class);

@Inject
private CustomerService customerService;

@Inject
private OrderFacade orderFacade;

@Inject
private OrderService orderService;

@Inject
private dev.vulcanium.site.tech.store.facade.order.OrderFacade orderFacadeV1;

@Inject
private ShoppingCartService shoppingCartService;

@Autowired
private CustomerFacade customerFacade;

@Autowired
private CustomerFacade customerFacadev1; //v1 version

@Inject
private AuthorizationUtils authorizationUtils;

@Inject
private CredentialsService credentialsService;

private static final String DEFAULT_ORDER_LIST_COUNT = "25";

/**
 * Get a list of orders for a given customer accept request parameter
 * 'start' start index for count accept request parameter 'max' maximum
 * number count, otherwise returns all Used for administrators
 *
 * @param response
 * @return
 * @throws Exception
 */
@RequestMapping(value = { "/private/orders/customers/{id}" }, method = RequestMethod.GET)
@ResponseStatus(HttpStatus.OK)
@ResponseBody
@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT"),
		@ApiImplicitParam(name = "lang", dataType = "string", defaultValue = "en") })
public ReadableOrderList list(@PathVariable final Long id,
                              @RequestParam(value = "start", required = false) Integer start,
                              @RequestParam(value = "count", required = false) Integer count, @ApiIgnore MerchantStore merchantStore,
                              @ApiIgnore Language language, HttpServletResponse response) throws Exception {
	
	Customer customer = customerService.getById(id);
	
	if (customer == null) {
		LOGGER.error("Customer is null for id " + id);
		response.sendError(404, "Customer is null for id " + id);
		return null;
	}
	
	if (start == null) {
		start = new Integer(0);
	}
	if (count == null) {
		count = new Integer(100);
	}
	
	ReadableCustomer readableCustomer = new ReadableCustomer();
	ReadableCustomerPopulator customerPopulator = new ReadableCustomerPopulator();
	customerPopulator.populate(customer, readableCustomer, merchantStore, language);
	
	ReadableOrderList returnList = orderFacade.getReadableOrderList(merchantStore, customer, start, count,
			language);
	
	List<ReadableOrder> orders = returnList.getOrders();
	
	if (!CollectionUtils.isEmpty(orders)) {
		for (ReadableOrder order : orders) {
			order.setCustomer(readableCustomer);
		}
	}
	
	return returnList;
}

/**
 * List orders for authenticated customers
 *
 * @param start
 * @param count
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
@RequestMapping(value = { "/auth/orders" }, method = RequestMethod.GET)
@ResponseStatus(HttpStatus.OK)
@ResponseBody
@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT"),
		@ApiImplicitParam(name = "lang", dataType = "string", defaultValue = "en") })
public ReadableOrderList list(@RequestParam(value = "page", required = false) Integer page,
                              @RequestParam(value = "count", required = false) Integer count, @ApiIgnore MerchantStore merchantStore,
                              @ApiIgnore Language language, HttpServletRequest request, HttpServletResponse response) throws Exception {
	
	Principal principal = request.getUserPrincipal();
	String userName = principal.getName();
	
	Customer customer = customerService.getByNick(userName);
	
	if (customer == null) {
		response.sendError(401, "Error while listing orders, customer not authorized");
		return null;
	}
	
	if (page == null) {
		page = new Integer(0);
	}
	if (count == null) {
		count = new Integer(100);
	}
	
	ReadableCustomer readableCustomer = new ReadableCustomer();
	ReadableCustomerPopulator customerPopulator = new ReadableCustomerPopulator();
	customerPopulator.populate(customer, readableCustomer, merchantStore, language);
	
	ReadableOrderList returnList = orderFacade.getReadableOrderList(merchantStore, customer, page, count, language);
	
	if (returnList == null) {
		returnList = new ReadableOrderList();
	}
	
	List<ReadableOrder> orders = returnList.getOrders();
	if (!CollectionUtils.isEmpty(orders)) {
		for (ReadableOrder order : orders) {
			order.setCustomer(readableCustomer);
		}
	}
	return returnList;
}

/**
 * This method returns list of all the orders for a store.This is not
 * bound to any specific stores and will get list of all the orders
 * available for this instance
 *
 * @param start
 * @param count
 * @return List of orders
 * @throws Exception
 */
@RequestMapping(value = { "/private/orders" }, method = RequestMethod.GET)
@ResponseStatus(HttpStatus.OK)
@ResponseBody
public ReadableOrderList list(
		@RequestParam(value = "count", required = false, defaultValue = DEFAULT_ORDER_LIST_COUNT) Integer count,
		@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
		@RequestParam(value = "name", required = false) String name,
		@RequestParam(value = "id", required = false) Long id,
		@RequestParam(value = "status", required = false) String status,
		@RequestParam(value = "phone", required = false) String phone,
		@RequestParam(value = "email", required = false) String email,
		@ApiIgnore MerchantStore merchantStore,
		@ApiIgnore Language language) {
	
	OrderCriteria orderCriteria = new OrderCriteria();
	orderCriteria.setPageSize(count);
	orderCriteria.setStartPage(page);
	
	orderCriteria.setCustomerName(name);
	orderCriteria.setCustomerPhone(phone);
	orderCriteria.setStatus(status);
	orderCriteria.setEmail(email);
	orderCriteria.setId(id);
	
	
	String user = authorizationUtils.authenticatedUser();
	authorizationUtils.authorizeUser(user, Stream.of(Constants.GROUP_SUPERADMIN, Constants.GROUP_ADMIN,
			Constants.GROUP_ADMIN_ORDER, Constants.GROUP_ADMIN_RETAIL).collect(Collectors.toList()), merchantStore);
	
	ReadableOrderList orders = orderFacade.getReadableOrderList(orderCriteria, merchantStore);
	
	
	return orders;
	
}

/**
 * Order details
 * @param id
 * @param merchantStore
 * @param language
 * @return
 */
@RequestMapping(value = { "/private/orders/{id}" }, method = RequestMethod.GET)
@ResponseStatus(HttpStatus.OK)
@ResponseBody
@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT"),
		@ApiImplicitParam(name = "lang", dataType = "string", defaultValue = "en") })
public ReadableOrder get(
		@PathVariable final Long id,
		@ApiIgnore MerchantStore merchantStore,
		@ApiIgnore Language language) {
	
	String user = authorizationUtils.authenticatedUser();
	authorizationUtils.authorizeUser(user, Stream.of(Constants.GROUP_SUPERADMIN, Constants.GROUP_ADMIN,
			Constants.GROUP_ADMIN_ORDER, Constants.GROUP_ADMIN_RETAIL).collect(Collectors.toList()), merchantStore);
	
	
	ReadableOrder order = orderFacade.getReadableOrder(id, merchantStore, language);
	
	return order;
}

/**
 * Get a given order by id
 *
 * @param id
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
@RequestMapping(value = { "/auth/orders/{id}" }, method = RequestMethod.GET)
@ResponseStatus(HttpStatus.OK)
@ResponseBody
@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT"),
		@ApiImplicitParam(name = "lang", dataType = "string", defaultValue = "en") })
public ReadableOrder getOrder(@PathVariable final Long id, @ApiIgnore MerchantStore merchantStore,
                              @ApiIgnore Language language, HttpServletRequest request, HttpServletResponse response) throws Exception {
	Principal principal = request.getUserPrincipal();
	String userName = principal.getName();
	
	Customer customer = customerService.getByNick(userName);
	
	if (customer == null) {
		response.sendError(401, "Error while performing checkout customer not authorized");
		return null;
	}
	
	ReadableOrder order = orderFacade.getReadableOrder(id, merchantStore, language);
	
	if (order == null) {
		LOGGER.error("Order is null for id " + id);
		response.sendError(404, "Order is null for id " + id);
		return null;
	}
	
	if (order.getCustomer() == null) {
		LOGGER.error("Order is null for customer " + principal);
		response.sendError(404, "Order is null for customer " + principal);
		return null;
	}
	
	if (order.getCustomer().getId() != null
			    && order.getCustomer().getId().longValue() != customer.getId().longValue()) {
		LOGGER.error("Order is null for customer " + principal);
		response.sendError(404, "Order is null for customer " + principal);
		return null;
	}
	
	return order;
}

/**
 * Action for performing a checkout on a given shopping cart
 *
 * @param id
 * @param order
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
@RequestMapping(value = { "/auth/cart/{code}/checkout" }, method = RequestMethod.POST)
@ResponseStatus(HttpStatus.OK)
@ResponseBody
@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT"),
		@ApiImplicitParam(name = "lang", dataType = "string", defaultValue = "en") })
public ReadableOrderConfirmation checkout(
		@PathVariable final String code, //shopping cart
		@Valid @RequestBody PersistableOrder order, // order
		@ApiIgnore MerchantStore merchantStore,
		@ApiIgnore Language language,
		HttpServletRequest request,
		HttpServletResponse response, Locale locale) throws Exception {
	
	try {
		Principal principal = request.getUserPrincipal();
		String userName = principal.getName();
		
		Customer customer = customerService.getByNick(userName);
		
		if (customer == null) {
			response.sendError(401, "Error while performing checkout customer not authorized");
			return null;
		}
		
		ShoppingCart cart = shoppingCartService.getByCode(code, merchantStore);
		if (cart == null) {
			throw new ResourceNotFoundException("Cart code " + code + " does not exist");
		}
		
		order.setShoppingCartId(cart.getId());
		order.setCustomerId(customer.getId());//That is an existing customer purchasing
		
		Order modelOrder = orderFacade.processOrder(order, customer, merchantStore, language, locale);
		Long orderId = modelOrder.getId();
		modelOrder.setId(orderId);
		
		
		return orderFacadeV1.orderConfirmation(modelOrder, customer, merchantStore, language);
		
		
		
	} catch (Exception e) {
		LOGGER.error("Error while processing checkout", e);
		try {
			response.sendError(503, "Error while processing checkout " + e.getMessage());
		} catch (Exception ignore) {
		}
		return null;
	}
}

/**
 * Main checkout resource that will complete the order flow
 * @param code
 * @param order
 * @param merchantStore
 * @param language
 * @return
 */
@RequestMapping(value = { "/cart/{code}/checkout" }, method = RequestMethod.POST)
@ResponseStatus(HttpStatus.OK)
@ResponseBody
@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT"),
		@ApiImplicitParam(name = "lang", dataType = "string", defaultValue = "en") })
public ReadableOrderConfirmation checkout(
		@PathVariable final String code,//shopping cart
		@Valid @RequestBody PersistableAnonymousOrder order,//order
		@ApiIgnore MerchantStore merchantStore,
		@ApiIgnore Language language) {
	
	Validate.notNull(order.getCustomer(), "Customer must not be null");
	
	
	ShoppingCart cart;
	try {
		cart = shoppingCartService.getByCode(code, merchantStore);
		
		if (cart == null) {
			throw new ResourceNotFoundException("Cart code " + code + " does not exist");
		}
		
		PersistableCustomer presistableCustomer = order.getCustomer();
		if(!StringUtils.isBlank(presistableCustomer.getPassword())) { //validate customer password
			credentialsService.validateCredentials(presistableCustomer.getPassword(), presistableCustomer.getRepeatPassword(), merchantStore, language);
		}
		
		Customer customer = new Customer();
		customer = customerFacade.populateCustomerModel(customer, order.getCustomer(), merchantStore, language);
		
		if(!StringUtils.isBlank(presistableCustomer.getPassword())) {
			customer.setAnonymous(false);
			customer.setNick(customer.getEmailAddress()); //username
			if(customerFacadev1.checkIfUserExists(customer.getNick(),  merchantStore)) {
				throw new GenericRuntimeException("409", "Customer with email [" + customer.getEmailAddress() + "] is already registered");
			}
		}
		
		
		order.setShoppingCartId(cart.getId());
		
		Order modelOrder = orderFacade.processOrder(order, customer, merchantStore, language,
				LocaleUtils.getLocale(language));
		Long orderId = modelOrder.getId();
		order.setId(orderId);
		order.getCustomer().setId(modelOrder.getCustomerId());
		
		return orderFacadeV1.orderConfirmation(modelOrder, customer, merchantStore, language);
		
		
	} catch (Exception e) {
		if(e instanceof CredentialsException) {
			throw new GenericRuntimeException("412","Credentials creation Failed [" + e.getMessage() + "]");
		}
		String message = e.getMessage();
		if(StringUtils.isBlank(message)) {//exception type
			message = "APP-BACKEND";
			if(e.getCause() instanceof dev.vulcanium.business.modules.integration.IntegrationException) {
				message = "Integration problen occured to complete order";
			}
		}
		throw new ServiceRuntimeException("Error during checkout [" + message + "]", e);
	}
	
}

@RequestMapping(value = { "/private/orders/{id}/customer" }, method = RequestMethod.PATCH)
@ResponseStatus(HttpStatus.OK)
@ResponseBody
@ApiImplicitParams({
		@ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT"),
		@ApiImplicitParam(name = "lang", dataType = "string", defaultValue = "en") })
public void updateOrderCustomer(
		@PathVariable final Long id,
		@Valid @RequestBody PersistableCustomer orderCustomer,
		@ApiIgnore MerchantStore merchantStore,
		@ApiIgnore Language language) {
	
	String user = authorizationUtils.authenticatedUser();
	authorizationUtils.authorizeUser(user, Stream.of(Constants.GROUP_SUPERADMIN, Constants.GROUP_ADMIN,
			Constants.GROUP_ADMIN_ORDER, Constants.GROUP_ADMIN_RETAIL).collect(Collectors.toList()), merchantStore);
	
	
	orderFacade.updateOrderCustomre(id, orderCustomer, merchantStore);
	return;
}

@RequestMapping(value = { "/private/orders/{id}/status" }, method = RequestMethod.PUT)
@ResponseStatus(HttpStatus.OK)
@ResponseBody
@ApiImplicitParams({
		@ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT"),
		@ApiImplicitParam(name = "lang", dataType = "string", defaultValue = "en") })
public void updateOrderStatus(
		@PathVariable final Long id,
		@Valid @RequestBody String status,
		@ApiIgnore MerchantStore merchantStore,
		@ApiIgnore Language language) {
	
	String user = authorizationUtils.authenticatedUser();
	authorizationUtils.authorizeUser(user, Stream.of(Constants.GROUP_SUPERADMIN, Constants.GROUP_ADMIN,
			Constants.GROUP_ADMIN_ORDER, Constants.GROUP_ADMIN_RETAIL).collect(Collectors.toList()), merchantStore);
	
	Order order = orderService.getOrder(id, merchantStore);
	if (order == null) {
		throw new GenericRuntimeException("412", "Order not found [" + id + "]");
	}
	
	OrderStatus statusEnum = OrderStatus.valueOf(status);
	
	orderFacade.updateOrderStatus(order, statusEnum, merchantStore);
	return;
}
}
