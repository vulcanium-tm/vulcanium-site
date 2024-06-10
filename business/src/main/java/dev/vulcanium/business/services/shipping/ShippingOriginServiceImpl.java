package dev.vulcanium.business.services.shipping;

import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.shipping.ShippingOrigin;
import dev.vulcanium.business.repositories.shipping.ShippingOriginRepository;
import dev.vulcanium.business.services.common.generic.SalesManagerEntityServiceImpl;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;



@Service("shippingOriginService")
public class ShippingOriginServiceImpl extends SalesManagerEntityServiceImpl<Long, ShippingOrigin> implements ShippingOriginService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ShippingOriginServiceImpl.class);
	
	private ShippingOriginRepository shippingOriginRepository;

	

	@Inject
	public ShippingOriginServiceImpl(ShippingOriginRepository shippingOriginRepository) {
		super(shippingOriginRepository);
		this.shippingOriginRepository = shippingOriginRepository;
	}


	@Override
	public ShippingOrigin getByStore(MerchantStore store) {
		// TODO Auto-generated method stub
		return shippingOriginRepository.findByStore(store.getId());
	}
	

}
