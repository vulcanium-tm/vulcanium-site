package dev.vulcanium.business.modules.cms.content;

import dev.vulcanium.business.exception.ServiceException;
import dev.vulcanium.business.model.content.FileContentType;
import dev.vulcanium.business.model.content.OutputContentFile;
import dev.vulcanium.business.modules.cms.common.ImageGet;
import java.util.List;

public interface ContentImageGet extends ImageGet{

  OutputContentFile getImage(final String merchantStoreCode, String imageName,
      FileContentType imageContentType) throws ServiceException;

  List<String> getImageNames(final String merchantStoreCode,
      FileContentType imageContentType) throws ServiceException;

}
