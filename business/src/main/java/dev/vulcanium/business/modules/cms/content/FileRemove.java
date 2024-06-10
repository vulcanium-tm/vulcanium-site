/**
 * 
 */
package dev.vulcanium.business.modules.cms.content;

import dev.vulcanium.business.exception.ServiceException;
import dev.vulcanium.business.model.content.FileContentType;
import java.util.Optional;


/**
 * @author Umesh Awasthi
 *
 */
public interface FileRemove {
  void removeFile(String merchantStoreCode, FileContentType staticContentType,
      String fileName, Optional<String> path) throws ServiceException;

  void removeFiles(String merchantStoreCode, Optional<String> path) throws ServiceException;

}
