package dev.vulcanium.business.utils;

import dev.vulcanium.business.model.order.orderproduct.OrderProduct;
import dev.vulcanium.business.model.order.orderproduct.OrderProductAttribute;
import java.util.Set;

public class ProductUtils {
	
	public static String buildOrderProductDisplayName(OrderProduct orderProduct) {
		
		String pName = orderProduct.getProductName();
		Set<OrderProductAttribute> oAttributes = orderProduct.getOrderAttributes();
		StringBuilder attributeName = null;
		for(OrderProductAttribute oProductAttribute : oAttributes) {
			if(attributeName == null) {
				attributeName = new StringBuilder();
				attributeName.append("[");
			} else {
				attributeName.append(", ");
			}
			attributeName.append(oProductAttribute.getProductAttributeName())
			.append(": ")
			.append(oProductAttribute.getProductAttributeValueName());
			
		}
		
		
		StringBuilder productName = new StringBuilder();
		productName.append(pName);
		
		if(attributeName!=null) {
			attributeName.append("]");
			productName.append(" ").append(attributeName.toString());
		}
		
		return productName.toString();
		
		
	}

}
