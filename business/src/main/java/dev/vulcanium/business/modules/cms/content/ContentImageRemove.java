package dev.vulcanium.business.modules.cms.content;

import dev.vulcanium.business.exception.ServiceException;
import dev.vulcanium.business.model.content.FileContentType;
import dev.vulcanium.business.modules.cms.common.ImageRemove;

public interface ContentImageRemove extends ImageRemove {



  void removeImage(final String merchantStoreCode, final FileContentType imageContentType,
      final String imageName) throws ServiceException;

}
