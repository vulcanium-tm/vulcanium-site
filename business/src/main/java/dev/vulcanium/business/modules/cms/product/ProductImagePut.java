package dev.vulcanium.business.modules.cms.product;

import dev.vulcanium.business.exception.ServiceException;
import dev.vulcanium.business.model.catalog.product.image.ProductImage;
import dev.vulcanium.business.model.content.ImageContentFile;


public interface ProductImagePut {

  void addProductImage(ProductImage productImage, ImageContentFile contentImage)
      throws ServiceException;

}
