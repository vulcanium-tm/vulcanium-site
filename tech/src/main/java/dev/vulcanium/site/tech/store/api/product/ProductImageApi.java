package dev.vulcanium.site.tech.store.api.product;

import dev.vulcanium.business.exception.ServiceException;
import dev.vulcanium.business.model.catalog.product.Product;
import dev.vulcanium.business.model.catalog.product.image.ProductImage;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.business.services.catalog.product.ProductService;
import dev.vulcanium.business.services.catalog.product.image.ProductImageService;
import dev.vulcanium.business.store.api.exception.ResourceNotFoundException;
import dev.vulcanium.business.store.api.exception.ServiceRuntimeException;
import dev.vulcanium.business.store.api.exception.UnauthorizedException;
import dev.vulcanium.site.tech.mapper.catalog.ReadableProductImageMapper;
import dev.vulcanium.site.tech.model.catalog.product.ReadableImage;
import dev.vulcanium.site.tech.model.entity.NameEntity;
import io.swagger.annotations.*;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

@Controller
@RequestMapping("/api")
@Api(tags = { "Product images management. Add, remove and set the order of product images." })
@SwaggerDefinition(tags = {
		@Tag(name = "Product images management", description = "Add and remove products images. Change images sort order.") })
public class ProductImageApi {

@Inject
private ProductImageService productImageService;

@Inject
private ProductService productService;

@Autowired
private ReadableProductImageMapper readableProductImageMapper;

private static final Logger LOGGER = LoggerFactory.getLogger(ProductImageApi.class);

/**
 * To be used with MultipartFile
 *
 * @param id
 * @param uploadfiles
 * @param request
 * @param response
 * @throws Exception
 */
@ResponseStatus(HttpStatus.CREATED)
@RequestMapping(value = { "/private/product/{id}/image", "/auth/product/{id}/image" }, consumes = {
		MediaType.MULTIPART_FORM_DATA_VALUE }, method = RequestMethod.POST)
@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
		@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en") })
public void uploadImage(
		@PathVariable Long id,
		@RequestParam(value = "file", required = true) MultipartFile[] files,
		@RequestParam(value = "order", required = false, defaultValue = "0") Integer position,
		@RequestParam(value = "defaultImage", required = false, defaultValue = "false") boolean defaultImage,
		@ApiIgnore MerchantStore merchantStore, @ApiIgnore Language language) throws IOException {
	
	try {
		Product product = productService.getById(id);
		if (product == null) {
			throw new ResourceNotFoundException("Product not found");
		}
		
		if (product.getMerchantStore().getId().intValue() != merchantStore.getId().intValue()) {
			throw new UnauthorizedException("Resource not authorized for this merchant");
		}
		
		boolean hasDefaultImage = false;
		Set<ProductImage> images = product.getImages();
		
		if (!defaultImage && !CollectionUtils.isEmpty(images)) {
			for (ProductImage image : images) {
				if (image.isDefaultImage()) {
					hasDefaultImage = true;
					break;
				}
			}
		}
		
		List<ProductImage> contentImagesList = new ArrayList<ProductImage>();
		int sortOrder = position;
		for (MultipartFile multipartFile : files) {
			if (!multipartFile.isEmpty()) {
				ProductImage productImage = new ProductImage();
				productImage.setImage(multipartFile.getInputStream());
				productImage.setProductImage(multipartFile.getOriginalFilename());
				productImage.setProduct(product);
				
				if (!hasDefaultImage) {
					productImage.setDefaultImage(true);
					hasDefaultImage = true;
				}
				productImage.setSortOrder(sortOrder);
				position++;
				contentImagesList.add(productImage);
			}
		}
		
		if (CollectionUtils.isNotEmpty(contentImagesList)) {
			productImageService.addProductImages(product, contentImagesList);
		}
		
	} catch (Exception e) {
		LOGGER.error("Error while creating ProductImage", e);
		throw new ServiceRuntimeException("Error while creating image");
	}
}

@ResponseStatus(HttpStatus.OK)
@RequestMapping(value = { "/private/product/image/{id}",
		"/auth/product/images/{id}" }, method = RequestMethod.DELETE)
public void deleteImage(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response)
		throws Exception {
	
	try {
		ProductImage productImage = productImageService.getById(id);
		
		if (productImage != null) {
			productImageService.delete(productImage);
		} else {
			response.sendError(404, "No ProductImage found for ID : " + id);
		}
		
	} catch (Exception e) {
		LOGGER.error("Error while deleting ProductImage", e);
		try {
			response.sendError(503, "Error while deleting ProductImage " + e.getMessage());
		} catch (Exception ignore) {
		}
	}
}

@ResponseStatus(HttpStatus.OK)
@RequestMapping(value = { "/private/product/{id}/image/{imageId}" }, method = RequestMethod.DELETE)
public void deleteImage(@PathVariable Long id, @PathVariable Long imageId, @Valid NameEntity imageName,
                        @ApiIgnore MerchantStore merchantStore, @ApiIgnore Language language) {
	
	
	Optional<ProductImage> productImage = productImageService.getProductImage(imageId, id, merchantStore);
	
	if (productImage.isPresent()) {
		try {
			productImageService.delete(productImage.get());
		} catch (ServiceException e) {
			LOGGER.error("Error while deleting ProductImage", e);
			throw new ServiceRuntimeException("ProductImage [" + imageId + "] cannot be deleted",e);
			
		}
	} else {
		throw new ResourceNotFoundException("Product image [" + imageId
				                                    + "] not found for product id [" + id + "] and merchant [" + merchantStore.getCode() + "]");
	}
	
}


/**
 * Get product images
 * @param id
 * @param imageId
 * @param merchantStore
 * @param language
 * @return
 */

@ResponseStatus(HttpStatus.OK)
@RequestMapping(value = { "/product/{productId}/images" }, method = RequestMethod.GET)
@ApiOperation(httpMethod = "GET", value = "Get images for a given product")
@ApiResponses(value = {
		@ApiResponse(code = 200, message = "List of ProductImage found", response = List.class) })
@ResponseBody
@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
		@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en") })
public List<ReadableImage> images(
		@PathVariable Long productId,
		@ApiIgnore MerchantStore merchantStore,
		@ApiIgnore Language language) {
	
	
	Product p = productService.getById(productId);
	
	if(p==null) {
		throw new ResourceNotFoundException("Product images not found for product id [" + productId
				                                    + "] and merchant [" + merchantStore.getCode() + "]");
	}
	
	if(p.getMerchantStore().getId() != merchantStore.getId()) {
		throw new ResourceNotFoundException("Product images not found for product id [" + productId
				                                    + "] and merchant [" + merchantStore.getCode() + "]");
	}
	
	List<ReadableImage> target = new ArrayList<ReadableImage>();
	
	Set<ProductImage> images = p.getImages();
	if(images!=null && images.size()>0) {
		
		
		target = images.stream().map(i -> image(i, merchantStore, language))
				         .sorted(Comparator.comparingInt(ReadableImage::getOrder))
				         .collect(Collectors.toList());
		
		
	}
	
	return target;
	
}

private ReadableImage image(ProductImage image, MerchantStore store, Language language) {
	return readableProductImageMapper.convert(image, store, language);
}


/**
 *
 * Patch image (change position)
 *
 * @param id
 * @param files
 * @param position
 * @param merchantStore
 * @param language
 * @throws IOException
 */

@ResponseStatus(HttpStatus.OK)
@RequestMapping(value = { "/private/product/{id}/image/{imageId}",
		"/auth/product/{id}/image/{id}" }, method = RequestMethod.PATCH)
@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
		@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en") })
public void imageDetails(@PathVariable Long id, @PathVariable Long imageId,
                         @RequestParam(value = "order", required = false, defaultValue = "0") Integer position,
                         @ApiIgnore MerchantStore merchantStore, @ApiIgnore Language language) throws IOException {
	
	try {
		
		Product p = productService.getById(id);
		
		if(p==null) {
			throw new ResourceNotFoundException("Product image [" + imageId + "] not found for product id [" + id
					                                    + "] and merchant [" + merchantStore.getCode() + "]");
		}
		
		if(p.getMerchantStore().getId() != merchantStore.getId()) {
			throw new ResourceNotFoundException("Product image [" + imageId + "] not found for product id [" + id
					                                    + "] and merchant [" + merchantStore.getCode() + "]");
		}
		
		Optional<ProductImage> productImage = productImageService.getProductImage(imageId, id, merchantStore);
		
		if (productImage.isPresent()) {
			productImage.get().setSortOrder(position);
			productImageService.updateProductImage(p, productImage.get());
		} else {
			throw new ResourceNotFoundException("Product image [" + imageId + "] not found for product id [" + id
					                                    + "] and merchant [" + merchantStore.getCode() + "]");
		}
		
		
		
	} catch (Exception e) {
		LOGGER.error("Error while deleting ProductImage", e);
		throw new ServiceRuntimeException("ProductImage [" + imageId + "] cannot be edited");
	}
}

}
