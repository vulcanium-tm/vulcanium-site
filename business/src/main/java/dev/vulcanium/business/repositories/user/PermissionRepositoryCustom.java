package dev.vulcanium.business.repositories.user;

import dev.vulcanium.business.model.user.PermissionCriteria;
import dev.vulcanium.business.model.user.PermissionList;

public interface PermissionRepositoryCustom {

	PermissionList listByCriteria(PermissionCriteria criteria);


}
