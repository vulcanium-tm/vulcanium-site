package dev.vulcanium.business.services.order.ordertotal;

import dev.vulcanium.business.model.catalog.product.Product;
import dev.vulcanium.business.model.customer.Customer;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.order.OrderSummary;
import dev.vulcanium.business.model.order.OrderTotal;
import dev.vulcanium.business.model.order.OrderTotalVariation;
import dev.vulcanium.business.model.order.RebatesOrderTotalVariation;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.business.model.shoppingcart.ShoppingCartItem;
import dev.vulcanium.business.modules.order.total.OrderTotalPostProcessorModule;
import dev.vulcanium.business.services.catalog.product.ProductService;
import java.util.ArrayList;
import java.util.List;
import jakarta.annotation.Resource;
import jakarta.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("OrderTotalService")
public class OrderTotalServiceImpl implements OrderTotalService {
	
	@Autowired
	@Resource(name="orderTotalsPostProcessors")
	List<OrderTotalPostProcessorModule> orderTotalPostProcessors;
	
	@Inject
	private ProductService productService;


	@Override
	public OrderTotalVariation findOrderTotalVariation(OrderSummary summary, Customer customer, MerchantStore store, Language language)
			throws Exception {
	
		RebatesOrderTotalVariation variation = new RebatesOrderTotalVariation();
		
		List<OrderTotal> totals = null;
		
		if(orderTotalPostProcessors != null) {
			for(OrderTotalPostProcessorModule module : orderTotalPostProcessors) {
				//TODO check if the module is enabled from the Admin
				
				List<ShoppingCartItem> items = summary.getProducts();
				for(ShoppingCartItem item : items) {

					Product product = productService.getBySku(item.getSku(), store, language);
					//Product product = productService.getProductForLocale(productId, language, languageService.toLocale(language, store));
					
					OrderTotal orderTotal = module.caculateProductPiceVariation(summary, item, product, customer, store);
					if(orderTotal==null) {
						continue;
					}
					if(totals==null) {
						totals = new ArrayList<OrderTotal>();
						variation.setVariations(totals);
					}
					
					//if product is null it will be catched when invoking the module
					orderTotal.setText(StringUtils.isNoneBlank(orderTotal.getText())?orderTotal.getText():product.getProductDescription().getName());
					variation.getVariations().add(orderTotal);	
				}
			}
		}
		
		
		return variation;
	}

}
