package dev.vulcanium.business.services.order.orderproduct;

import dev.vulcanium.business.model.order.orderproduct.OrderProductDownload;
import dev.vulcanium.business.services.common.generic.SalesManagerEntityService;
import java.util.List;


public interface OrderProductDownloadService extends SalesManagerEntityService<Long, OrderProductDownload> {

	/**
	 * List {@link OrderProductDownload} by order id
	 * @param orderId
	 * @return
	 */
	List<OrderProductDownload> getByOrderId(Long orderId);

}
