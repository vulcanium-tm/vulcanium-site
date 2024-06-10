package dev.vulcanium.business.modules.integration.shipping.model;

import java.util.List;

import dev.vulcanium.business.exception.ServiceException;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.shipping.PackageDetails;
import dev.vulcanium.business.model.shipping.ShippingProduct;

public interface Packaging {

public List<PackageDetails> getBoxPackagesDetails(
		List<ShippingProduct> products, MerchantStore store) throws ServiceException;

public List<PackageDetails> getItemPackagesDetails(
		List<ShippingProduct> products, MerchantStore store) throws ServiceException;
	
}
