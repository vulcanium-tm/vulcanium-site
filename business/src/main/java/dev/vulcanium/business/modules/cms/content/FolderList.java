package dev.vulcanium.business.modules.cms.content;

import dev.vulcanium.business.exception.ServiceException;
import java.util.List;
import java.util.Optional;

public interface FolderList {
	
	  List<String> listFolders(final String merchantStoreCode, Optional<String> path)
		      throws ServiceException;

}
