package dev.vulcanium.business.services.user;

import dev.vulcanium.business.exception.ServiceException;
import dev.vulcanium.business.model.user.Group;
import dev.vulcanium.business.model.user.Permission;
import dev.vulcanium.business.model.user.PermissionCriteria;
import dev.vulcanium.business.model.user.PermissionList;
import dev.vulcanium.business.services.common.generic.SalesManagerEntityService;
import java.util.List;



public interface PermissionService extends SalesManagerEntityService<Integer, Permission> {

  List<Permission> getByName();

  List<Permission> listPermission() throws ServiceException;

  Permission getById(Integer permissionId);

  List<Permission> getPermissions(List<Integer> groupIds) throws ServiceException;

  void deletePermission(Permission permission) throws ServiceException;

  PermissionList listByCriteria(PermissionCriteria criteria) throws ServiceException;

  void removePermission(Permission permission, Group group) throws ServiceException;

}
