/**
 * 
 */
package dev.vulcanium.business.modules.cms.content;

import dev.vulcanium.business.exception.ServiceException;
import java.util.Optional;


public interface FolderRemove {
  void removeFolder(final String merchantStoreCode, String folderName, Optional<String> folderPath)
      throws ServiceException;

}
