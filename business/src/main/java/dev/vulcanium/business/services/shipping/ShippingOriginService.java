package dev.vulcanium.business.services.shipping;

import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.shipping.ShippingOrigin;
import dev.vulcanium.business.services.common.generic.SalesManagerEntityService;

/**
 * ShippingOrigin object if different from MerchantStore address.
 * Can be managed through this service.
 * @author carlsamson
 *
 */
public interface ShippingOriginService  extends SalesManagerEntityService<Long, ShippingOrigin> {

	ShippingOrigin getByStore(MerchantStore store);



}
