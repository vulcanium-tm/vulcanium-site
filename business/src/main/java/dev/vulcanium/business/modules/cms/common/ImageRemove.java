package dev.vulcanium.business.modules.cms.common;

import dev.vulcanium.business.exception.ServiceException;


public interface ImageRemove {

  void removeImages(final String merchantStoreCode) throws ServiceException;

}
