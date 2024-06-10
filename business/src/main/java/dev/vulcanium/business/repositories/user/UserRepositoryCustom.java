package dev.vulcanium.business.repositories.user;

import dev.vulcanium.business.exception.ServiceException;
import dev.vulcanium.business.model.common.Criteria;
import dev.vulcanium.business.model.common.GenericEntityList;
import dev.vulcanium.business.model.user.User;

public interface UserRepositoryCustom {
  
  GenericEntityList<User> listByCriteria(Criteria criteria) throws ServiceException;

}
