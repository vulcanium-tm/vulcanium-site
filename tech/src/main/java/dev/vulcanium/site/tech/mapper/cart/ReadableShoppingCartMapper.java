package dev.vulcanium.site.tech.mapper.cart;

import dev.vulcanium.business.constants.Constants;
import dev.vulcanium.business.model.catalog.product.attribute.*;
import dev.vulcanium.business.model.catalog.product.variant.ProductVariant;
import dev.vulcanium.business.model.catalog.product.variant.ProductVariantImage;
import dev.vulcanium.business.model.content.FileContentType;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.order.OrderSummary;
import dev.vulcanium.business.model.order.OrderTotalSummary;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.business.model.shoppingcart.ShoppingCart;
import dev.vulcanium.business.services.catalog.pricing.PricingService;
import dev.vulcanium.business.services.catalog.product.attribute.ProductAttributeService;
import dev.vulcanium.business.services.catalog.product.variant.ProductVariantService;
import dev.vulcanium.business.services.shoppingcart.ShoppingCartCalculationService;
import dev.vulcanium.business.store.api.exception.ConversionRuntimeException;
import dev.vulcanium.business.utils.ImageFilePath;
import dev.vulcanium.site.tech.mapper.Mapper;
import dev.vulcanium.site.tech.mapper.catalog.ReadableMinimalProductMapper;
import dev.vulcanium.site.tech.mapper.catalog.ReadableProductVariationMapper;
import dev.vulcanium.site.tech.model.catalog.product.ReadableImage;
import dev.vulcanium.site.tech.model.order.total.ReadableOrderTotal;
import dev.vulcanium.site.tech.model.shoppingcart.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class ReadableShoppingCartMapper implements Mapper<ShoppingCart, ReadableShoppingCart> {

private static final Logger LOG = LoggerFactory.getLogger(ReadableShoppingCartMapper.class);

@Autowired
private ShoppingCartCalculationService shoppingCartCalculationService;

@Autowired
private PricingService pricingService;

@Autowired
private ProductAttributeService productAttributeService;

@Autowired
private ProductVariantService productVariantService;

@Autowired
private ReadableMinimalProductMapper readableMinimalProductMapper;

@Autowired
private ReadableProductVariationMapper readableProductVariationMapper;

@Autowired
@Qualifier("img")
private ImageFilePath imageUtils;

@Override
public ReadableShoppingCart convert(ShoppingCart source, MerchantStore store, Language language) {
	ReadableShoppingCart destination = new ReadableShoppingCart();
	return this.merge(source, destination, store, language);
}

private ReadableImage image(ProductVariantImage instanceImage, MerchantStore store, Language language) {
	ReadableImage img = new ReadableImage();
	img.setDefaultImage(instanceImage.isDefaultImage());
	img.setId(instanceImage.getId());
	img.setImageName(instanceImage.getProductImage());
	img.setImageUrl(imageUtils.buildCustomTypeImageUtils(store, img.getImageName(), FileContentType.VARIANT));
	return img;
}

@Override
public ReadableShoppingCart merge(ShoppingCart source, ReadableShoppingCart destination, MerchantStore store,
                                  Language language) {
	Validate.notNull(source, "ShoppingCart cannot be null");
	Validate.notNull(destination, "ReadableShoppingCart cannot be null");
	Validate.notNull(store, "MerchantStore cannot be null");
	Validate.notNull(language, "Language cannot be null");
	
	destination.setCode(source.getShoppingCartCode());
	int cartQuantity = 0;
	
	destination.setCustomer(source.getCustomerId());
	
	try {
		
		if (!StringUtils.isBlank(source.getPromoCode())) {
			Date promoDateAdded = source.getPromoAdded();// promo valid 1 day
			if (promoDateAdded == null) {
				promoDateAdded = new Date();
			}
			Instant instant = promoDateAdded.toInstant();
			ZonedDateTime zdt = instant.atZone(ZoneId.systemDefault());
			LocalDate date = zdt.toLocalDate();
			// date added < date + 1 day
			LocalDate tomorrow = LocalDate.now().plusDays(1);
			if (date.isBefore(tomorrow)) {
				destination.setPromoCode(source.getPromoCode());
			}
		}
		
		Set<dev.vulcanium.business.model.shoppingcart.ShoppingCartItem> items = source.getLineItems();
		
		if (items != null) {
			
			for (dev.vulcanium.business.model.shoppingcart.ShoppingCartItem item : items) {
				ReadableShoppingCartItem shoppingCartItem = new ReadableShoppingCartItem();
				readableMinimalProductMapper.merge(item.getProduct(), shoppingCartItem, store, language);
				
				//variation
				if(item.getVariant() != null) {
					Optional<ProductVariant> productVariant = productVariantService.getById(item.getVariant(), store);
					if(productVariant.isEmpty()) {
						throw new ConversionRuntimeException("An error occured during shopping cart [" + source.getShoppingCartCode() + "] conversion, productVariant [" + item.getVariant() + "] not found");
					}
					shoppingCartItem.setVariant(readableProductVariationMapper.convert(productVariant.get().getVariation(), store, language));
					if(productVariant.get().getVariationValue() != null) {
						shoppingCartItem.setVariantValue(readableProductVariationMapper.convert(productVariant.get().getVariationValue(), store, language));
					}
					
					if(productVariant.get().getProductVariantGroup() != null) {
						Set<String> nameSet = new HashSet<>();
						List<ReadableImage> instanceImages = productVariant.get().getProductVariantGroup().getImages()
								                                     .stream().map(i -> this.image(i, store, language))
								                                     .filter(e -> nameSet.add(e.getImageUrl()))
								                                     .collect(Collectors.toList());
						shoppingCartItem.setImages(instanceImages);
					}
				}
				
				
				
				
				shoppingCartItem.setPrice(item.getItemPrice());
				shoppingCartItem.setFinalPrice(pricingService.getDisplayAmount(item.getItemPrice(), store));
				
				shoppingCartItem.setQuantity(item.getQuantity());
				
				cartQuantity = cartQuantity + item.getQuantity();
				
				BigDecimal subTotal = pricingService.calculatePriceQuantity(item.getItemPrice(),
						item.getQuantity());
				
				// calculate sub total (price * quantity)
				shoppingCartItem.setSubTotal(subTotal);
				
				shoppingCartItem.setDisplaySubTotal(pricingService.getDisplayAmount(subTotal, store));
				
				Set<dev.vulcanium.business.model.shoppingcart.ShoppingCartAttributeItem> attributes = item
						                                                                                     .getAttributes();
				if (attributes != null) {
					for (dev.vulcanium.business.model.shoppingcart.ShoppingCartAttributeItem attribute : attributes) {
						
						ProductAttribute productAttribute = productAttributeService
								                                    .getById(attribute.getProductAttributeId());
						
						if (productAttribute == null) {
							LOG.warn("Product attribute with ID " + attribute.getId()
									         + " not found, skipping cart attribute " + attribute.getId());
							continue;
						}
						
						ReadableShoppingCartAttribute cartAttribute = new ReadableShoppingCartAttribute();
						
						cartAttribute.setId(attribute.getId());
						
						ProductOption option = productAttribute.getProductOption();
						ProductOptionValue optionValue = productAttribute.getProductOptionValue();
						
						List<ProductOptionDescription> optionDescriptions = option.getDescriptionsSettoList();
						List<ProductOptionValueDescription> optionValueDescriptions = optionValue
								                                                              .getDescriptionsSettoList();
						
						String optName = null;
						String optValue = null;
						if (!CollectionUtils.isEmpty(optionDescriptions)
								    && !CollectionUtils.isEmpty(optionValueDescriptions)) {
							
							optName = optionDescriptions.get(0).getName();
							optValue = optionValueDescriptions.get(0).getName();
							
							for (ProductOptionDescription optionDescription : optionDescriptions) {
								if (optionDescription.getLanguage() != null && optionDescription.getLanguage()
										                                               .getId().intValue() == language.getId().intValue()) {
									optName = optionDescription.getName();
									break;
								}
							}
							
							for (ProductOptionValueDescription optionValueDescription : optionValueDescriptions) {
								if (optionValueDescription.getLanguage() != null && optionValueDescription
										                                                    .getLanguage().getId().intValue() == language.getId().intValue()) {
									optValue = optionValueDescription.getName();
									break;
								}
							}
							
						}
						
						if (optName != null) {
							ReadableShoppingCartAttributeOption attributeOption = new ReadableShoppingCartAttributeOption();
							attributeOption.setCode(option.getCode());
							attributeOption.setId(option.getId());
							attributeOption.setName(optName);
							cartAttribute.setOption(attributeOption);
						}
						
						if (optValue != null) {
							ReadableShoppingCartAttributeOptionValue attributeOptionValue = new ReadableShoppingCartAttributeOptionValue();
							attributeOptionValue.setCode(optionValue.getCode());
							attributeOptionValue.setId(optionValue.getId());
							attributeOptionValue.setName(optValue);
							cartAttribute.setOptionValue(attributeOptionValue);
						}
						shoppingCartItem.getCartItemattributes().add(cartAttribute);
					}
					
				}
				destination.getProducts().add(shoppingCartItem);
			}
		}
		
		// Calculate totals using shoppingCartService
		// OrderSummary contains ShoppingCart items
		
		OrderSummary summary = new OrderSummary();
		List<dev.vulcanium.business.model.shoppingcart.ShoppingCartItem> productsList = new ArrayList<dev.vulcanium.business.model.shoppingcart.ShoppingCartItem>();
		productsList.addAll(source.getLineItems());
		summary.setProducts(productsList);
		
		// OrdetTotalSummary contains all calculations
		
		OrderTotalSummary orderSummary = shoppingCartCalculationService.calculate(source, store, language);
		
		if (CollectionUtils.isNotEmpty(orderSummary.getTotals())) {
			
			if (orderSummary.getTotals().stream()
					    .filter(t -> Constants.OT_DISCOUNT_TITLE.equals(t.getOrderTotalCode())).count() == 0) {
				// no promo coupon applied
				destination.setPromoCode(null);
				
			}
			
			List<ReadableOrderTotal> totals = new ArrayList<ReadableOrderTotal>();
			for (dev.vulcanium.business.model.order.OrderTotal t : orderSummary.getTotals()) {
				ReadableOrderTotal total = new ReadableOrderTotal();
				total.setCode(t.getOrderTotalCode());
				total.setValue(t.getValue());
				total.setText(t.getText());
				totals.add(total);
			}
			destination.setTotals(totals);
		}
		
		destination.setSubtotal(orderSummary.getSubTotal());
		destination.setDisplaySubTotal(pricingService.getDisplayAmount(orderSummary.getSubTotal(), store));
		
		destination.setTotal(orderSummary.getTotal());
		destination.setDisplayTotal(pricingService.getDisplayAmount(orderSummary.getTotal(), store));
		
		destination.setQuantity(cartQuantity);
		destination.setId(source.getId());
		
		if (source.getOrderId() != null) {
			destination.setOrder(source.getOrderId());
		}
		
	} catch (Exception e) {
		throw new ConversionRuntimeException("An error occured while converting ReadableShoppingCart", e);
	}
	
	return destination;
}



}
