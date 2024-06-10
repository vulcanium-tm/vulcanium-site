package dev.vulcanium.business.modules.cms.content;

import dev.vulcanium.business.exception.ServiceException;
import dev.vulcanium.business.model.content.FileContentType;
import dev.vulcanium.business.model.content.OutputContentFile;
import java.util.List;
import java.util.Optional;


/**
 * Methods to retrieve the static content from the CMS
 * 
 * @author Carl Samson
 *
 */
public interface FileGet {

  OutputContentFile getFile(final String merchantStoreCode, Optional<String> path, FileContentType fileContentType,
      String contentName) throws ServiceException;

  List<String> getFileNames(final String merchantStoreCode, Optional<String> path, FileContentType fileContentType)
      throws ServiceException;

  List<OutputContentFile> getFiles(final String merchantStoreCode,
		  Optional<String> path, FileContentType fileContentType) throws ServiceException;
}
