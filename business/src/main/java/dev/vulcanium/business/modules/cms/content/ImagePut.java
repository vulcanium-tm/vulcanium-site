package dev.vulcanium.business.modules.cms.content;

import dev.vulcanium.business.exception.ServiceException;
import dev.vulcanium.business.model.content.InputContentFile;
import java.util.List;

public interface ImagePut {


  void addImage(final String merchantStoreCode, InputContentFile image)
      throws ServiceException;

  void addImages(final String merchantStoreCode, List<InputContentFile> imagesList)
      throws ServiceException;

}
