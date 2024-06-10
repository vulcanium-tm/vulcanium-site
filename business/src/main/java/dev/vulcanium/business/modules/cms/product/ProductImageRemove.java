package dev.vulcanium.business.modules.cms.product;

import dev.vulcanium.business.exception.ServiceException;
import dev.vulcanium.business.model.catalog.product.Product;
import dev.vulcanium.business.model.catalog.product.image.ProductImage;
import dev.vulcanium.business.modules.cms.common.ImageRemove;


public interface ProductImageRemove extends ImageRemove {

  void removeProductImage(ProductImage productImage) throws ServiceException;

  void removeProductImages(Product product) throws ServiceException;

}
