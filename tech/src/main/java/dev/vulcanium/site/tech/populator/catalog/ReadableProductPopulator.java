package dev.vulcanium.site.tech.populator.catalog;

import dev.vulcanium.business.exception.ConversionException;
import dev.vulcanium.business.model.catalog.category.Category;
import dev.vulcanium.business.model.catalog.product.Product;
import dev.vulcanium.business.model.catalog.product.attribute.ProductAttribute;
import dev.vulcanium.business.model.catalog.product.attribute.ProductOptionDescription;
import dev.vulcanium.business.model.catalog.product.attribute.ProductOptionValue;
import dev.vulcanium.business.model.catalog.product.attribute.ProductOptionValueDescription;
import dev.vulcanium.business.model.catalog.product.availability.ProductAvailability;
import dev.vulcanium.business.model.catalog.product.description.ProductDescription;
import dev.vulcanium.business.model.catalog.product.image.ProductImage;
import dev.vulcanium.business.model.catalog.product.manufacturer.ManufacturerDescription;
import dev.vulcanium.business.model.catalog.product.price.FinalPrice;
import dev.vulcanium.business.model.catalog.product.price.ProductPrice;
import dev.vulcanium.business.model.catalog.product.price.ProductPriceDescription;
import dev.vulcanium.business.model.catalog.product.type.ProductType;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.business.services.catalog.pricing.PricingService;
import dev.vulcanium.business.utils.AbstractDataPopulator;
import dev.vulcanium.business.utils.DateUtil;
import dev.vulcanium.business.utils.ImageFilePath;
import dev.vulcanium.site.tech.model.catalog.category.ReadableCategory;
import dev.vulcanium.site.tech.model.catalog.manufacturer.ReadableManufacturer;
import dev.vulcanium.site.tech.model.catalog.product.*;
import dev.vulcanium.site.tech.model.catalog.product.attribute.*;
import dev.vulcanium.site.tech.model.catalog.product.attribute.api.ReadableProductOptionValue;
import dev.vulcanium.site.tech.model.catalog.product.type.ProductTypeDescription;
import dev.vulcanium.site.tech.model.catalog.product.type.ReadableProductType;
import java.util.*;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

@Setter
@Getter
public class ReadableProductPopulator extends
		AbstractDataPopulator<Product, ReadableProduct> {

private PricingService pricingService;

private ImageFilePath imageUtils;

@Override
public ReadableProduct populate(Product source,
                                ReadableProduct target, MerchantStore store, Language language)
		throws ConversionException {
	Validate.notNull(pricingService, "Requires to set PricingService");
	Validate.notNull(imageUtils, "Requires to set imageUtils");
	
	
	try {
		
		List<dev.vulcanium.site.tech.model.catalog.product.ProductDescription> fulldescriptions = new ArrayList<>();
		if(language == null) {
			target = new ReadableProductFull();
		}
		
		if(target==null) {
			target = new ReadableProduct();
		}
		
		ProductDescription description = source.getProductDescription();
		
		if (source.getDescriptions()!=null && !source.getDescriptions().isEmpty()){
			for(ProductDescription desc : source.getDescriptions()) {
				if(language != null && desc.getLanguage()!=null && desc.getLanguage().getId().intValue() == language.getId().intValue()) {
					description = desc;
					break;
				} else {
					fulldescriptions.add(populateDescription(desc));
				}
			}
		}
		
		if(target instanceof ReadableProductFull) {
			((ReadableProductFull)target).setDescriptions(fulldescriptions);
		}
		
		if(language == null) {
			language = store.getDefaultLanguage();
		}
		
		final Language lang = language;
		
		target.setId(source.getId());
		target.setAvailable(source.isAvailable());
		target.setProductShipeable(source.isProductShipeable());
		
		ProductSpecification specifications = new ProductSpecification();
		specifications.setHeight(source.getProductHeight());
		specifications.setLength(source.getProductLength());
		specifications.setWeight(source.getProductWeight());
		specifications.setWidth(source.getProductWidth());
		target.setProductSpecifications(specifications);
		
		target.setPreOrder(source.isPreOrder());
		target.setRefSku(source.getRefSku());
		target.setSortOrder(source.getSortOrder());
		
		if(source.getType() != null) {
			target.setType(this.type(source.getType(), language));
		}
		
		if(source.getOwner() != null) {
			RentalOwner owner = new RentalOwner();
			owner.setId(source.getOwner().getId());
			owner.setEmailAddress(source.getOwner().getEmailAddress());
			owner.setFirstName(source.getOwner().getBilling().getFirstName());
			owner.setLastName(source.getOwner().getBilling().getLastName());
			dev.vulcanium.site.tech.model.customer.address.Address address = new dev.vulcanium.site.tech.model.customer.address.Address();
			address.setAddress(source.getOwner().getBilling().getAddress());
			address.setBillingAddress(true);
			address.setCity(source.getOwner().getBilling().getCity());
			address.setCompany(source.getOwner().getBilling().getCompany());
			address.setCountry(source.getOwner().getBilling().getCountry().getIsoCode());
			address.setZone(source.getOwner().getBilling().getZone().getCode());
			address.setLatitude(source.getOwner().getBilling().getLatitude());
			address.setLongitude(source.getOwner().getBilling().getLongitude());
			address.setPhone(source.getOwner().getBilling().getTelephone());
			address.setPostalCode(source.getOwner().getBilling().getPostalCode());
			owner.setAddress(address);
			target.setOwner(owner);
		}
		
		
		if(source.getDateAvailable() != null) {
			target.setDateAvailable(DateUtil.formatDate(source.getDateAvailable()));
		}
		
		if(source.getAuditSection()!=null) {
			target.setCreationDate(DateUtil.formatDate(source.getAuditSection().getDateCreated()));
		}
		target.setProductVirtual(source.getProductVirtual());
		if(description!=null) {
			dev.vulcanium.site.tech.model.catalog.product.ProductDescription tragetDescription = populateDescription(description);
			target.setDescription(tragetDescription);
			
		}
		
		if(source.getManufacturer()!=null) {
			ManufacturerDescription manufacturer = source.getManufacturer().getDescriptions().iterator().next();
			ReadableManufacturer manufacturerEntity = new ReadableManufacturer();
			dev.vulcanium.site.tech.model.catalog.manufacturer.ManufacturerDescription d = new dev.vulcanium.site.tech.model.catalog.manufacturer.ManufacturerDescription();
			d.setName(manufacturer.getName());
			manufacturerEntity.setDescription(d);
			manufacturerEntity.setId(source.getManufacturer().getId());
			manufacturerEntity.setOrder(source.getManufacturer().getOrder());
			manufacturerEntity.setCode(source.getManufacturer().getCode());
			target.setManufacturer(manufacturerEntity);
		}
		
		Set<ProductImage> images = source.getImages();
		if (images!=null && !images.isEmpty()){
			List<ReadableImage> imageList = new ArrayList<>();
			
			String contextPath = imageUtils.getContextPath();
			
			for(ProductImage img : images) {
				ReadableImage prdImage = new ReadableImage();
				prdImage.setImageName(img.getProductImage());
				prdImage.setDefaultImage(img.isDefaultImage());
				prdImage.setOrder(img.getSortOrder()!=null ? img.getSortOrder() : 0);
				
				if (img.getImageType() == 1 && img.getProductImageUrl()!=null) {
					prdImage.setImageUrl(img.getProductImageUrl());
				} else {
					
					prdImage.setImageUrl(contextPath + imageUtils.buildProductImageUtils(store, source.getSku(),
							img.getProductImage()));
				}
				prdImage.setId(img.getId());
				prdImage.setImageType(img.getImageType());
				if(img.getProductImageUrl()!=null){
					prdImage.setExternalUrl(img.getProductImageUrl());
				}
				if(img.getImageType()==1 && img.getProductImageUrl()!=null) {//video
					prdImage.setVideoUrl(img.getProductImageUrl());
				}
				
				if(prdImage.isDefaultImage()) {
					target.setImage(prdImage);
				}
				
				imageList.add(prdImage);
			}
			imageList = imageList.stream()
					            .sorted(Comparator.comparingInt(ReadableImage::getOrder))
					            .collect(Collectors.toList());
			
			target
					.setImages(imageList);
		}
		
		if(!CollectionUtils.isEmpty(source.getCategories())) {
			
			ReadableCategoryPopulator categoryPopulator = new ReadableCategoryPopulator();
			List<ReadableCategory> categoryList = new ArrayList<>();
			
			for(Category category : source.getCategories()) {
				
				ReadableCategory readableCategory = new ReadableCategory();
				categoryPopulator.populate(category, readableCategory, store, language);
				categoryList.add(readableCategory);
				
			}
			
			target.setCategories(categoryList);
			
		}
		
		if(!CollectionUtils.isEmpty(source.getAttributes())) {
			
			Set<ProductAttribute> attributes = source.getAttributes();
			Map<Long,ReadableProductProperty> properties = null;
			Map<Long,ReadableProductOption> selectableOptions = null;
			
			if(!CollectionUtils.isEmpty(attributes)) {
				
				for(ProductAttribute attribute : attributes) {
					ReadableProductOption opt;
					ReadableProductAttribute attr = null;
					ReadableProductProperty property;
					ReadableProductPropertyValue propertyValue = null;
					ReadableProductOptionValue optValue = new ReadableProductOptionValue();
					ReadableProductAttributeValue attrValue = new ReadableProductAttributeValue();
					
					ProductOptionValue optionValue = attribute.getProductOptionValue();
					
					if(attribute.getAttributeDisplayOnly()) {
						property = createProperty(attribute, language);
						
						ReadableProductOption readableOption = new ReadableProductOption(); //that is the property
						ReadableProductPropertyValue readableOptionValue = new ReadableProductPropertyValue();
						
						readableOption.setCode(attribute.getProductOption().getCode());
						readableOption.setId(attribute.getProductOption().getId());
						
						Set<ProductOptionDescription> podescriptions = attribute.getProductOption().getDescriptions();
						if (podescriptions!=null && !podescriptions.isEmpty()){
							for(ProductOptionDescription optionDescription : podescriptions) {
								if(optionDescription.getLanguage().getCode().equals(language.getCode())) {
									readableOption.setName(optionDescription.getName());
								}
							}
						}
						
						property.setProperty(readableOption);
						
						Set<ProductOptionValueDescription> povdescriptions = attribute.getProductOptionValue().getDescriptions();
						readableOptionValue.setId(attribute.getProductOptionValue().getId());
						if (povdescriptions!=null && !povdescriptions.isEmpty()){
							for(ProductOptionValueDescription optionValueDescription : povdescriptions) {
								if(optionValueDescription.getLanguage().getCode().equals(language.getCode())) {
									readableOptionValue.setName(optionValueDescription.getName());
								}
							}
						}
						
						property.setPropertyValue(readableOptionValue);
						target.getProperties().add(property);
						
					} else {
						
						if(selectableOptions==null) {
							selectableOptions = new TreeMap<>();
						}
						opt = selectableOptions.get(attribute.getProductOption().getId());
						if(opt==null) {
							opt = createOption(attribute, language);
						}
						if(opt!=null) {
							selectableOptions.put(attribute.getProductOption().getId(), opt);
						}
						
						optValue.setDefaultValue(attribute.getAttributeDefault());
						optValue.setId(attribute.getId());
						optValue.setCode(attribute.getProductOptionValue().getCode());
						dev.vulcanium.site.tech.model.catalog.product.attribute.ProductOptionValueDescription valueDescription = new dev.vulcanium.site.tech.model.catalog.product.attribute.ProductOptionValueDescription();
						valueDescription.setLanguage(language.getCode());
						if(attribute.getProductAttributePrice()!=null && attribute.getProductAttributePrice().doubleValue()>0) {
							String formatedPrice = pricingService.getDisplayAmount(attribute.getProductAttributePrice(), store);
							optValue.setPrice(formatedPrice);
						}
						
						if(!StringUtils.isBlank(attribute.getProductOptionValue().getProductOptionValueImage())) {
							optValue.setImage(imageUtils.buildProductPropertyImageUtils(store, attribute.getProductOptionValue().getProductOptionValueImage()));
						}
						optValue.setSortOrder(0);
						if(attribute.getProductOptionSortOrder()!=null) {
							optValue.setSortOrder(attribute.getProductOptionSortOrder());
						}
						
						List<ProductOptionValueDescription> podescriptions = optionValue.getDescriptionsSettoList();
						ProductOptionValueDescription podescription = null;
						if (podescriptions!=null && !podescriptions.isEmpty()){
							podescription = podescriptions.getFirst();
							if(podescriptions.size()>1) {
								for(ProductOptionValueDescription optionValueDescription : podescriptions) {
									if(optionValueDescription.getLanguage().getId().intValue()==language.getId().intValue()) {
										podescription = optionValueDescription;
										break;
									}
								}
							}
						}
						valueDescription.setName(podescription.getName());
						valueDescription.setDescription(podescription.getDescription());
						optValue.setDescription(valueDescription);
						
						if(opt!=null) {
							opt.getOptionValues().add(optValue);
						}
					}
					
				}
				
			}
			
			if(selectableOptions != null) {
				List<ReadableProductOption> options = new ArrayList<>(selectableOptions.values());
				target.setOptions(options);
			}
			
			
		}
		
		ProductAvailability availability = null;
		for(ProductAvailability a : source.getAvailabilities()) {
			availability = a;
			target.setQuantity(availability.getProductQuantity() == null ? 1:availability.getProductQuantity());
			target.setQuantityOrderMaximum(availability.getProductQuantityOrderMax() == null ? 1:availability.getProductQuantityOrderMax());
			target.setQuantityOrderMinimum(availability.getProductQuantityOrderMin()==null ? 1:availability.getProductQuantityOrderMin());
			if (availability.getProductQuantity()>0 && target.isAvailable()){
				target.setCanBePurchased(true);
			}
		}
		
		
		target.setSku(source.getSku());
		
		FinalPrice price = pricingService.calculateProductPrice(source);
		
		if(price != null) {
			
			target.setFinalPrice(pricingService.getDisplayAmount(price.getFinalPrice(), store));
			target.setPrice(price.getFinalPrice());
			target.setOriginalPrice(pricingService.getDisplayAmount(price.getOriginalPrice(), store));
			
			if(price.isDiscounted()) {
				target.setDiscounted(true);
			}
			
			if(availability != null) {
				Set<ProductPrice> prices = availability.getPrices();
				if(!CollectionUtils.isEmpty(prices)) {
					ReadableProductPrice readableProductPrice = new ReadableProductPrice();
					readableProductPrice.setDiscounted(target.isDiscounted());
					readableProductPrice.setFinalPrice(target.getFinalPrice());
					readableProductPrice.setOriginalPrice(target.getOriginalPrice());
					
					Optional<ProductPrice> pr = prices.stream().filter(p -> p.getCode().equals(ProductPrice.DEFAULT_PRICE_CODE))
							                            .findFirst();
					
					target.setProductPrice(readableProductPrice);
					
					if(pr.isPresent()) {
						readableProductPrice.setId(pr.get().getId());
						Optional<ProductPriceDescription> d = pr.get().getDescriptions().stream().filter(desc -> desc.getLanguage().getCode().equals(lang.getCode())).findFirst();
						if(d.isPresent()) {
							dev.vulcanium.site.tech.model.catalog.product.ProductPriceDescription priceDescription = new dev.vulcanium.site.tech.model.catalog.product.ProductPriceDescription();
							priceDescription.setLanguage(language.getCode());
							priceDescription.setId(d.get().getId());
							priceDescription.setPriceAppender(d.get().getPriceAppender());
							readableProductPrice.setDescription(priceDescription);
						}
					}
					
				}
			}
			
		}
		
		
		
		
		if(target instanceof ReadableProductFull) {
			((ReadableProductFull)target).setDescriptions(fulldescriptions);
		}
		
		
		return target;
		
	} catch (Exception e) {
		throw new ConversionException(e);
	}
}



private ReadableProductOption createOption(ProductAttribute productAttribute, Language language) {
	
	
	ReadableProductOption option = new ReadableProductOption();
	option.setId(productAttribute.getProductOption().getId());//attribute of the option
	option.setType(productAttribute.getProductOption().getProductOptionType());
	option.setCode(productAttribute.getProductOption().getCode());
	List<ProductOptionDescription> descriptions = productAttribute.getProductOption().getDescriptionsSettoList();
	ProductOptionDescription description = null;
	if (descriptions!=null && !descriptions.isEmpty()){
		description = descriptions.getFirst();
		if(descriptions.size()>1) {
			for(ProductOptionDescription optionDescription : descriptions) {
				if(optionDescription.getLanguage().getCode().equals(language.getCode())) {
					description = optionDescription;
					break;
				}
			}
		}
	}
	
	if(description==null) {
		return null;
	}
	
	option.setLang(language.getCode());
	option.setName(description.getName());
	option.setCode(productAttribute.getProductOption().getCode());
	
	
	return option;
	
}

private ReadableProductType type (ProductType type, Language language) {
	ReadableProductType readableType = new ReadableProductType();
	readableType.setCode(type.getCode());
	readableType.setId(type.getId());
	
	if(!CollectionUtils.isEmpty(type.getDescriptions())) {
		Optional<ProductTypeDescription> desc = type.getDescriptions().stream().filter(t -> t.getLanguage().getCode().equals(language.getCode()))
				                                        .map(d -> typeDescription(d)).findFirst();
		desc.ifPresent(readableType::setDescription);
	}
	
	return readableType;
}

private ProductTypeDescription typeDescription(dev.vulcanium.site.tech.model.catalog.product.type.ProductTypeDescription description) {
	ProductTypeDescription desc = new ProductTypeDescription();
	desc.setId(description.getId());
	desc.setName(description.getName());
	desc.setDescription(description.getDescription());
	desc.setLanguage(description.getLanguage().getCode());
	return desc;
}

private ReadableProductAttribute createAttribute(ProductAttribute productAttribute, Language language) {
	
	
	ReadableProductAttribute attr = new ReadableProductAttribute();
	attr.setId(productAttribute.getProductOption().getId());//attribute of the option
	attr.setType(productAttribute.getProductOption().getProductOptionType());
	List<ProductOptionDescription> descriptions = productAttribute.getProductOption().getDescriptionsSettoList();
	ProductOptionDescription description = null;
	if (descriptions!=null && !descriptions.isEmpty()){
		description = descriptions.getFirst();
		if(descriptions.size()>1) {
			for(ProductOptionDescription optionDescription : descriptions) {
				if(optionDescription.getLanguage().getId().intValue()==language.getId().intValue()) {
					description = optionDescription;
					break;
				}
			}
		}
	}
	
	if(description==null) {
		return null;
	}
	
	attr.setLang(language.getCode());
	attr.setName(description.getName());
	attr.setCode(productAttribute.getProductOption().getCode());
	
	
	return attr;
	
}

private ReadableProductProperty createProperty(ProductAttribute productAttribute, Language language) {
	
	
	ReadableProductProperty attr = new ReadableProductProperty();
	attr.setId(productAttribute.getProductOption().getId());
	attr.setType(productAttribute.getProductOption().getProductOptionType());
	
	
	
	
	List<ProductOptionDescription> descriptions = productAttribute.getProductOption().getDescriptionsSettoList();
	
	ReadableProductPropertyValue propertyValue = new ReadableProductPropertyValue();
	
	if (descriptions!=null && !descriptions.isEmpty()){
		for(ProductOptionDescription optionDescription : descriptions) {
			dev.vulcanium.site.tech.model.catalog.product.attribute.ProductOptionValueDescription productOptionValueDescription = new dev.vulcanium.site.tech.model.catalog.product.attribute.ProductOptionValueDescription();
			productOptionValueDescription.setId(optionDescription.getId());
			productOptionValueDescription.setLanguage(optionDescription.getLanguage().getCode());
			productOptionValueDescription.setName(optionDescription.getName());
			propertyValue.getValues().add(productOptionValueDescription);
			
		}
	}
	
	attr.setCode(productAttribute.getProductOption().getCode());
	return attr;
	
}




@Override
protected ReadableProduct createTarget() {
	return null;
}

dev.vulcanium.site.tech.model.catalog.product.ProductDescription populateDescription(ProductDescription description) {
	if(description == null) {
		return null;
	}
	
	dev.vulcanium.site.tech.model.catalog.product.ProductDescription tragetDescription = new dev.vulcanium.site.tech.model.catalog.product.ProductDescription();
	tragetDescription.setFriendlyUrl(description.getSeUrl());
	tragetDescription.setName(description.getName());
	tragetDescription.setId(description.getId());
	if(!StringUtils.isBlank(description.getMetatagTitle())) {
		tragetDescription.setTitle(description.getMetatagTitle());
	} else {
		tragetDescription.setTitle(description.getName());
	}
	tragetDescription.setMetaDescription(description.getMetatagDescription());
	tragetDescription.setDescription(description.getDescription());
	tragetDescription.setHighlights(description.getProductHighlight());
	tragetDescription.setLanguage(description.getLanguage().getCode());
	tragetDescription.setKeyWords(description.getMetatagKeywords());
	
	if(description.getLanguage() != null) {
		tragetDescription.setLanguage(description.getLanguage().getCode());
	}
	return tragetDescription;
}

}
