package dev.vulcanium.business.services.shipping;

import dev.vulcanium.business.exception.ServiceException;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.order.Order;
import dev.vulcanium.business.model.shipping.Quote;
import dev.vulcanium.business.model.shipping.ShippingSummary;
import dev.vulcanium.business.repositories.shipping.ShippingQuoteRepository;
import dev.vulcanium.business.services.common.generic.SalesManagerEntityServiceImpl;
import java.util.List;
import jakarta.inject.Inject;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("shippingQuoteService")
public class ShippingQuoteServiceImpl extends SalesManagerEntityServiceImpl<Long, Quote> implements ShippingQuoteService {

	
	private static final Logger LOGGER = LoggerFactory.getLogger(ShippingQuoteServiceImpl.class);
	
	private ShippingQuoteRepository shippingQuoteRepository;
	
	@Inject
	private ShippingService shippingService;
	
	@Inject
	public ShippingQuoteServiceImpl(ShippingQuoteRepository repository) {
		super(repository);
		this.shippingQuoteRepository = repository;
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<Quote> findByOrder(Order order) throws ServiceException {
		Validate.notNull(order,"Order cannot be null");
		return this.shippingQuoteRepository.findByOrder(order.getId());
	}

	@Override
	public ShippingSummary getShippingSummary(Long quoteId, MerchantStore store) throws ServiceException {
		
		Validate.notNull(quoteId,"quoteId must not be null");
		
		Quote q = shippingQuoteRepository.getOne(quoteId);

		
		ShippingSummary quote = null;
		
		if(q != null) {
			
			quote = new ShippingSummary();
			quote.setDeliveryAddress(q.getDelivery());
			quote.setShipping(q.getPrice());
			quote.setShippingModule(q.getModule());
			quote.setShippingOption(q.getOptionName());
			quote.setShippingOptionCode(q.getOptionCode());
			quote.setHandling(q.getHandling());
			
			if(shippingService.hasTaxOnShipping(store)) {
				quote.setTaxOnShipping(true);
			}
			
			
			
		}
		
		
		return quote;
		
	}


}
