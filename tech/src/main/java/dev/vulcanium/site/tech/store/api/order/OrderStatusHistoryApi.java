package dev.vulcanium.site.tech.store.api.order;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import jakarta.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import dev.vulcanium.business.constants.Constants;

import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.site.tech.model.order.history.PersistableOrderStatusHistory;
import dev.vulcanium.site.tech.model.order.history.ReadableOrderStatusHistory;
import dev.vulcanium.site.tech.store.facade.order.OrderFacade;
import dev.vulcanium.site.tech.utils.AuthorizationUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/api")
@Api(tags = { "Order status history api" })
@SwaggerDefinition(tags = {
		@Tag(name = "Order status history resource", description = "Related to OrderManagement api") })
public class OrderStatusHistoryApi {

@Inject
private OrderFacade orderFacade;

@Inject
private AuthorizationUtils authorizationUtils;

@RequestMapping(value = { "private/orders/{id}/history" }, method = RequestMethod.GET)
@ResponseStatus(HttpStatus.OK)
@ResponseBody
public List<ReadableOrderStatusHistory> list(@PathVariable final Long id, @ApiIgnore MerchantStore merchantStore,
                                             @ApiIgnore Language language) {
	
	String user = authorizationUtils.authenticatedUser();
	authorizationUtils.authorizeUser(user, Stream.of(Constants.GROUP_SUPERADMIN, Constants.GROUP_ADMIN,
			Constants.GROUP_ADMIN_ORDER, Constants.GROUP_ADMIN_RETAIL).collect(Collectors.toList()), merchantStore);
	
	return orderFacade.getReadableOrderHistory(id, merchantStore, language);
	
}

@RequestMapping(value = { "private/orders/{id}/history" }, method = RequestMethod.POST)
@ResponseStatus(HttpStatus.CREATED)
@ApiOperation(httpMethod = "POST", value = "Add order history", notes = "Adds a new status to an order", produces = "application/json", response = Void.class)
@ResponseBody
public void create(@PathVariable final Long id, @RequestBody PersistableOrderStatusHistory history,
                   @ApiIgnore MerchantStore merchantStore, @ApiIgnore Language language) {
	
	String user = authorizationUtils.authenticatedUser();
	authorizationUtils.authorizeUser(user, Stream.of(Constants.GROUP_SUPERADMIN, Constants.GROUP_ADMIN,
			Constants.GROUP_ADMIN_ORDER, Constants.GROUP_ADMIN_RETAIL).collect(Collectors.toList()), merchantStore);
	orderFacade.createOrderStatus(history, id, merchantStore);
	
}

}
