package dev.vulcanium.site.tech.store.api.order;

import dev.vulcanium.business.constants.Constants;
import dev.vulcanium.business.model.customer.Customer;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.payments.Payment;
import dev.vulcanium.business.model.payments.Transaction;
import dev.vulcanium.business.model.payments.TransactionType;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.business.model.shoppingcart.ShoppingCart;
import dev.vulcanium.business.services.catalog.pricing.PricingService;
import dev.vulcanium.business.services.customer.CustomerService;
import dev.vulcanium.business.services.order.OrderService;
import dev.vulcanium.business.services.payments.PaymentService;
import dev.vulcanium.business.services.shoppingcart.ShoppingCartService;
import dev.vulcanium.business.utils.AuthorizationUtils;
import dev.vulcanium.site.tech.model.order.ReadableOrderList;
import dev.vulcanium.site.tech.model.order.transaction.PersistablePayment;
import dev.vulcanium.site.tech.model.order.transaction.ReadableTransaction;
import dev.vulcanium.site.tech.populator.order.transaction.PersistablePaymentPopulator;
import dev.vulcanium.site.tech.populator.order.transaction.ReadableTransactionPopulator;
import dev.vulcanium.site.tech.store.api.exception.ResourceNotFoundException;
import dev.vulcanium.site.tech.store.facade.order.OrderFacade;
import io.swagger.annotations.*;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.security.Principal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@Controller
@RequestMapping("/api")
@Api(tags = { "Order payment resource (Order payment Api)" })
@SwaggerDefinition(tags = { @Tag(name = "Order payment resource", description = "Manage order payments") })
public class OrderPaymentApi {

private static final Logger LOGGER = LoggerFactory.getLogger(OrderPaymentApi.class);

@Inject
private CustomerService customerService;

@Inject
private OrderService orderService;

@Inject
private ShoppingCartService shoppingCartService;

@Inject
private PricingService pricingService;

@Inject
private PaymentService paymentService;

@Inject
private OrderFacade orderFacade;

@Inject
private AuthorizationUtils authorizationUtils;

@RequestMapping(value = { "/cart/{code}/payment/init" }, method = RequestMethod.POST)
@ResponseBody
@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
		@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en") })
public ReadableTransaction init(@Valid @RequestBody PersistablePayment payment, @PathVariable String code,
                                @ApiIgnore MerchantStore merchantStore, @ApiIgnore Language language) throws Exception {
	
	ShoppingCart cart = shoppingCartService.getByCode(code, merchantStore);
	if (cart == null) {
		throw new ResourceNotFoundException("Cart code " + code + " does not exist");
	}
	
	PersistablePaymentPopulator populator = new PersistablePaymentPopulator();
	populator.setPricingService(pricingService);
	
	Payment paymentModel = new Payment();
	
	populator.populate(payment, paymentModel, merchantStore, language);
	
	Transaction transactionModel = paymentService.initTransaction(null, paymentModel, merchantStore);
	
	ReadableTransaction transaction = new ReadableTransaction();
	ReadableTransactionPopulator trxPopulator = new ReadableTransactionPopulator();
	trxPopulator.setOrderService(orderService);
	trxPopulator.setPricingService(pricingService);
	
	trxPopulator.populate(transactionModel, transaction, merchantStore, language);
	
	return transaction;
	
}

@RequestMapping(value = { "/auth/cart/{code}/payment/init" }, method = RequestMethod.POST)
@ResponseBody
@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
		@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en") })
public ReadableTransaction init(@Valid @RequestBody PersistablePayment payment, @PathVariable String code,
                                @ApiIgnore MerchantStore merchantStore, @ApiIgnore Language language, HttpServletRequest request,
                                HttpServletResponse response) throws Exception {
	
	try {
		Principal principal = request.getUserPrincipal();
		String userName = principal.getName();
		
		Customer customer = customerService.getByNick(userName);
		
		if (customer == null) {
			response.sendError(401, "Error while initializing the payment customer not authorized");
			return null;
		}
		
		ShoppingCart cart = shoppingCartService.getByCode(code, merchantStore);
		if (cart == null) {
			
			throw new ResourceNotFoundException("Cart code " + code + " does not exist");
		}
		
		if (cart.getCustomerId() == null) {
			response.sendError(404, "Cart code " + code + " does not exist for exist for user " + userName);
			return null;
		}
		
		if (cart.getCustomerId().longValue() != customer.getId().longValue()) {
			response.sendError(404, "Cart code " + code + " does not exist for exist for user " + userName);
			return null;
		}
		
		PersistablePaymentPopulator populator = new PersistablePaymentPopulator();
		populator.setPricingService(pricingService);
		
		Payment paymentModel = new Payment();
		
		populator.populate(payment, paymentModel, merchantStore, language);
		
		Transaction transactionModel = paymentService.initTransaction(customer, paymentModel, merchantStore);
		
		ReadableTransaction transaction = new ReadableTransaction();
		ReadableTransactionPopulator trxPopulator = new ReadableTransactionPopulator();
		trxPopulator.setOrderService(orderService);
		trxPopulator.setPricingService(pricingService);
		
		trxPopulator.populate(transactionModel, transaction, merchantStore, language);
		
		return transaction;
		
	} catch (Exception e) {
		LOGGER.error("Error while initializing the payment", e);
		try {
			response.sendError(503, "Error while initializing the payment " + e.getMessage());
		} catch (Exception ignore) {
		}
		return null;
	}
}

@RequestMapping(value = { "/private/orders/{id}/payment/nextTransaction" }, method = RequestMethod.GET)
@ResponseStatus(HttpStatus.OK)

@ResponseBody
@ApiImplicitParams({
		@ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
		@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en") })
public String nextTransaction(
		@PathVariable final Long id,
		@ApiIgnore MerchantStore merchantStore,
		@ApiIgnore Language language) {
	
	String user = authorizationUtils.authenticatedUser();
	authorizationUtils.authorizeUser(user, Stream.of(Constants.GROUP_SUPERADMIN, Constants.GROUP_ADMIN,
			Constants.GROUP_ADMIN_ORDER, Constants.GROUP_ADMIN_RETAIL).collect(Collectors.toList()), merchantStore);
	
	TransactionType transactionType = orderFacade.nextTransaction(id, merchantStore);
	return "{\"transactionType\":\"" + transactionType.name() + "\"}";
	
}

@RequestMapping(value = { "/private/orders/{id}/payment/transactions" }, method = RequestMethod.GET)
@ResponseStatus(HttpStatus.OK)

@ResponseBody
@ApiImplicitParams({
		@ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
		@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en") })
public List<ReadableTransaction> listTransactions(
		@PathVariable final Long id,
		@ApiIgnore MerchantStore merchantStore,
		@ApiIgnore Language language) {
	
	String user = authorizationUtils.authenticatedUser();
	authorizationUtils.authorizeUser(user, Stream.of(Constants.GROUP_SUPERADMIN, Constants.GROUP_ADMIN,
			Constants.GROUP_ADMIN_ORDER, Constants.GROUP_ADMIN_RETAIL).collect(Collectors.toList()), merchantStore);
	
	
	return orderFacade.listTransactions(id, merchantStore);
	
}

/**
 * An order can be pre-authorized but un captured. This metho returns all
 * order subject to be capturable For a given time frame
 *
 * @param startDate
 * @param endDate
 * @param request
 * @param response
 * @return ReadableOrderList
 * @throws Exception
 */
@RequestMapping(value = { "/private/orders/payment/capturable" }, method = RequestMethod.GET)
@ResponseStatus(HttpStatus.ACCEPTED)
@ResponseBody
@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
		@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en") })
public ReadableOrderList listCapturableOrders(
		@RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
		@RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
		@ApiIgnore MerchantStore merchantStore, @ApiIgnore Language language, HttpServletRequest request,
		HttpServletResponse response) {
	
	try {
		Calendar cal = Calendar.getInstance();
		Date sDate = null;
		
		if (startDate != null) {
			sDate = Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
		} else {
			cal.add(Calendar.DATE, -1);
			sDate = cal.getTime();
		}
		
		Date eDate = null;
		
		if (endDate != null) {
			eDate = Date.from(endDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
		} else {
			eDate = new Date();
		}
		
		ReadableOrderList returnList = orderFacade.getCapturableOrderList(merchantStore, sDate, eDate, language);
		
		return returnList;
		
	} catch (Exception e) {
		LOGGER.error("Error while getting capturable payments", e);
		try {
			response.sendError(503, "Error while getting capturable payments " + e.getMessage());
		} catch (Exception ignore) {
		}
		return null;
	}
}

/**
 * Capture payment transaction for a given order id
 *
 * @param id
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
@RequestMapping(value = { "/private/orders/{id}/capture" }, method = RequestMethod.POST)
@ResponseStatus(HttpStatus.OK)
@ResponseBody
@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
		@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en") })
public ReadableTransaction capturePayment(@PathVariable Long id, @ApiIgnore MerchantStore merchantStore,
                                          @ApiIgnore Language language) {
	
	return null;
}

/**
 * Refund payment
 *
 * @param id
 * @param merchantStore
 * @param language
 * @return
 */
@RequestMapping(value = { "/private/orders/{id}/refund" }, method = RequestMethod.POST)
@ResponseStatus(HttpStatus.OK)
@ResponseBody
@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
		@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en") })
public ReadableTransaction refundPayment(@PathVariable Long id, @ApiIgnore MerchantStore merchantStore,
                                         @ApiIgnore Language language) {
	return null;
}

/**
 * Capture payment
 *
 * @param id
 * @param merchantStore
 * @param language
 * @return
 */
@RequestMapping(value = { "/private/orders/{id}/authorize" }, method = RequestMethod.POST)
@ResponseStatus(HttpStatus.OK)
@ResponseBody
@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
		@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en") })
public ReadableTransaction authorizePayment(@PathVariable Long id, @ApiIgnore MerchantStore merchantStore,
                                            @ApiIgnore Language language) {
	return null;
}
}