package dev.vulcanium.business.modules.cms.product;

import dev.vulcanium.business.exception.ServiceException;
import dev.vulcanium.business.model.catalog.product.Product;
import dev.vulcanium.business.model.catalog.product.file.ProductImageSize;
import dev.vulcanium.business.model.catalog.product.image.ProductImage;
import dev.vulcanium.business.model.content.OutputContentFile;
import dev.vulcanium.business.modules.cms.common.ImageGet;
import java.util.List;

public interface ProductImageGet extends ImageGet {

  /**
   * Used for accessing the path directly
   * 
   * @param merchantStoreCode
   * @param product
   * @param imageName
   * @return
   * @throws ServiceException
   */
  OutputContentFile getProductImage(final String merchantStoreCode, final String productCode,
      final String imageName) throws ServiceException;

  OutputContentFile getProductImage(final String merchantStoreCode, final String productCode,
      final String imageName, final ProductImageSize size) throws ServiceException;

  OutputContentFile getProductImage(ProductImage productImage) throws ServiceException;

  List<OutputContentFile> getImages(Product product) throws ServiceException;


}
