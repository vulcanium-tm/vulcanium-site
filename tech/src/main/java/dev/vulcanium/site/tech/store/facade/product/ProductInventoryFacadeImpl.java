package dev.vulcanium.site.tech.store.facade.product;

import static dev.vulcanium.site.tech.utils.ReadableEntityUtil.createReadableList;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import dev.vulcanium.business.exception.ServiceException;
import dev.vulcanium.business.services.catalog.product.ProductService;
import dev.vulcanium.business.services.catalog.product.availability.ProductAvailabilityService;
import dev.vulcanium.business.services.catalog.product.variant.ProductVariantService;
import dev.vulcanium.business.model.catalog.product.Product;
import dev.vulcanium.business.model.catalog.product.availability.ProductAvailability;
import dev.vulcanium.business.model.catalog.product.variant.ProductVariant;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.site.tech.mapper.inventory.PersistableInventoryMapper;
import dev.vulcanium.site.tech.mapper.inventory.ReadableInventoryMapper;
import dev.vulcanium.site.tech.model.catalog.product.inventory.PersistableInventory;
import dev.vulcanium.site.tech.model.catalog.product.inventory.ReadableInventory;
import dev.vulcanium.site.tech.model.entity.ReadableEntityList;
import dev.vulcanium.site.tech.store.api.exception.ResourceNotFoundException;
import dev.vulcanium.site.tech.store.api.exception.ServiceRuntimeException;
import dev.vulcanium.site.tech.store.facade.product.ProductInventoryFacade;

@Service("productInventoryFacade")
@Profile({ "default", "cloud", "gcp", "aws", "mysql", "local" })
public class ProductInventoryFacadeImpl implements ProductInventoryFacade {

@Autowired
private ProductAvailabilityService productAvailabilityService;

@Autowired
private ProductService productService;

@Autowired
private ProductVariantService productVariantService;

@Autowired
private ReadableInventoryMapper readableInventoryMapper;

@Autowired
private PersistableInventoryMapper productInventoryMapper;



private void validateProductHasSameStore(MerchantStore store, Product product) {
	if (!product.getMerchantStore().getId().equals(store.getId())) {
		throw new ResourceNotFoundException("Product with id [" + product.getId() + "] not found for store id ["
				                                    + store.getInvoiceTemplate() + "]");
	}
}

@Override
public void delete(Long productId, Long inventoryId, MerchantStore store) {
	Optional<ProductAvailability> availability = productAvailabilityService.getById(inventoryId, store);
	try {
		if (availability.isPresent()) {
			if(availability.get().getProduct().getId() == productId) {
				productAvailabilityService.delete(availability.get());
			} else {
				throw new ResourceNotFoundException("Product with id [" + productId + "] and inventory id [" +  inventoryId + "] not found for store id [" + store.getId() + "]");
			}
		} else {
			throw new ResourceNotFoundException("Product with id [" + productId + "] and inventory id [" +  inventoryId + "] not found for store id [" + store.getId() + "]");
		}
	} catch (ServiceException e) {
		throw new ServiceRuntimeException("Error while deleting inventory", e);
	}
}

private Product getProductById(Long productId, MerchantStore store) {
	return productService
			       .retrieveById(productId, store)
			       .orElseThrow(() -> new ResourceNotFoundException("Product with id [" + productId + "] not found"));
}

private ProductVariant getProductByInstance(Long instanceId, MerchantStore store) {
	return productVariantService.getById(instanceId, store).orElseThrow(() -> new ResourceNotFoundException("Product with instance [" + instanceId + "] not found"));
	
}

@Override
public ReadableInventory add(PersistableInventory inventory, MerchantStore store,
                             Language language) {
	Validate.notNull(store, "MerchantStore cannot be null");
	ProductAvailability availability = getProductAvailabilityToSave(inventory, store);
	
	saveOrUpdate(availability);
	return readableInventoryMapper.convert(availability, store, language);
}

private void saveOrUpdate(ProductAvailability availability) {
	try {
		productAvailabilityService.saveOrUpdate(availability);
	} catch (ServiceException e) {
		throw new ServiceRuntimeException("Cannot create Inventory", e);
	}
}

private ProductAvailability getProductAvailabilityToSave(PersistableInventory inventory, MerchantStore store) {
	
	return productInventoryMapper.convert(inventory, store, store.getDefaultLanguage());
	
}

@Override
public ReadableInventory get(Long inventoryId, MerchantStore store, Language language) {
	
	
	ProductAvailability availability = productAvailabilityService.getById(inventoryId, store)
			                                   .orElseThrow(() -> new ResourceNotFoundException("Inventory with id [" + inventoryId + "] not found"));
	return readableInventoryMapper.convert(availability, store, language);
}

@Override
public void update(PersistableInventory inventory, MerchantStore store, Language language) {
	
	Validate.notNull(inventory, "Inventory cannot be null");
	Validate.notNull(store, "MerchantStore cannot be null");
	
	Set<ProductAvailability> originAvailability = null;
	Product product = null;
	
	if(inventory.getProductId()!=null && inventory.getProductId().longValue()>0) {
		product = this.getProductById(inventory.getProductId(), store);
		originAvailability = product.getAvailabilities();
	} else {
		if(inventory.getVariant() != null && inventory.getId().longValue() > 0) {
			ProductVariant instance = this.getProductByInstance(inventory.getVariant(), store);
			originAvailability = instance.getAvailabilities();
			product = instance.getProduct();
		}
	}
	
	ProductAvailability avail = originAvailability.stream()
			                            .filter(a -> a.getId().longValue() == inventory.getId())
			                            .findAny()
			                            .orElse(null);
	
	if (avail == null) {
		throw new ResourceNotFoundException(
				"Inventory with id [" + inventory.getId() + "] not found");
	}
	
	
	inventory.setProductId(product.getId());
	
	avail = productInventoryMapper.merge(inventory, avail, store, language);
	avail.setProduct(product);
	avail.setMerchantStore(store);
	saveOrUpdate(avail);
}



@Override
public ReadableEntityList<ReadableInventory> get(String sku, MerchantStore store, Language language, int page, int count) {
	Validate.notNull(sku, "Product sku cannot be null");
	Validate.notNull(store, "MerchantStore code cannot be null");
	Validate.notNull(language, "Language cannot be null");
	
	Page<ProductAvailability> availabilities = productAvailabilityService.getBySku(sku, page, count);
	
	/**
	 * br -> if availabilities is null, it may be bcz a variant is a decorated product with no specific inventory.
	 * in this case it should display the parent product inventory
	 *
	 */
	
	if(availabilities.isEmpty()) {
		//get parent product
		try {
			Product singleProduct = productService.getBySku(sku, store);
			if(singleProduct!=null) {
				availabilities = new PageImpl<ProductAvailability>(new ArrayList<ProductAvailability>(singleProduct.getAvailabilities()));
			}
		} catch (ServiceException e) {
			throw new ServiceRuntimeException("An error occured while getting product with sku " + sku,e);
		}
	}
	
	List<ReadableInventory> returnList = availabilities.getContent().stream().map(i -> this.readableInventoryMapper.convert(i, store, language))
			                                     .collect(Collectors.toList());
	
	return createReadableList(availabilities, returnList);
}

@Override
public ReadableEntityList<ReadableInventory> get(Long productId, MerchantStore store, Language language, int page, int count) {
	
	Validate.notNull(productId, "Product id cannot be null");
	Validate.notNull(store, "MerchantStore code cannot be null");
	
	Page<ProductAvailability> availabilities = productAvailabilityService.listByProduct(productId, store, page, count);
	
	List<ReadableInventory> returnList = availabilities.getContent().stream().map(i -> this.readableInventoryMapper.convert(i, store, language))
			                                     .collect(Collectors.toList());
	
	return createReadableList(availabilities, returnList);
	
}



}
