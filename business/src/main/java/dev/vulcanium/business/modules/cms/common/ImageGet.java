package dev.vulcanium.business.modules.cms.common;

import dev.vulcanium.business.exception.ServiceException;
import dev.vulcanium.business.model.content.FileContentType;
import dev.vulcanium.business.model.content.OutputContentFile;
import java.util.List;

public interface ImageGet {

  List<OutputContentFile> getImages(final String merchantStoreCode,
      FileContentType imageContentType) throws ServiceException;

}
