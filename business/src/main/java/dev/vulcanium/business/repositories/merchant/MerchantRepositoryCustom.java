package dev.vulcanium.business.repositories.merchant;

import dev.vulcanium.business.exception.ServiceException;
import dev.vulcanium.business.model.common.GenericEntityList;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.merchant.MerchantStoreCriteria;

public interface MerchantRepositoryCustom {

  GenericEntityList<MerchantStore> listByCriteria(MerchantStoreCriteria criteria)
      throws ServiceException;


}
